package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioSharingStreamSliderViewModel;
import com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.SliderState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.ranges.ClosedFloatRange;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class AudioSharingStreamSliderViewModel$slider$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AudioSharingStreamSliderViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel.AudioSharingStreamSliderViewModel$slider$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $deviceName;
        final /* synthetic */ Integer $volume;
        int label;
        final /* synthetic */ AudioSharingStreamSliderViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Integer num, AudioSharingStreamSliderViewModel audioSharingStreamSliderViewModel, String str, Continuation continuation) {
            super(2, continuation);
            this.$volume = num;
            this.this$0 = audioSharingStreamSliderViewModel;
            this.$deviceName = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$volume, this.this$0, this.$deviceName, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            float intValue = this.$volume.intValue();
            this.this$0.audioSharingInteractor.getClass();
            return new AudioSharingStreamSliderViewModel.State(intValue, new ClosedFloatRange(0, this.this$0.audioSharingInteractor.getVolumeMax()), this.this$0.audioSharingIcon, this.$deviceName);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioSharingStreamSliderViewModel$slider$2(AudioSharingStreamSliderViewModel audioSharingStreamSliderViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = audioSharingStreamSliderViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AudioSharingStreamSliderViewModel$slider$2 audioSharingStreamSliderViewModel$slider$2 = new AudioSharingStreamSliderViewModel$slider$2(this.this$0, (Continuation) obj3);
        audioSharingStreamSliderViewModel$slider$2.L$0 = (Integer) obj;
        audioSharingStreamSliderViewModel$slider$2.L$1 = (CachedBluetoothDevice) obj2;
        return audioSharingStreamSliderViewModel$slider$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String name;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Integer num = (Integer) this.L$0;
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) this.L$1;
            if (cachedBluetoothDevice == null || (name = cachedBluetoothDevice.getName()) == null) {
                return SliderState.Empty.INSTANCE;
            }
            if (num == null) {
                return SliderState.Empty.INSTANCE;
            }
            AudioSharingStreamSliderViewModel audioSharingStreamSliderViewModel = this.this$0;
            CoroutineContext coroutineContext = audioSharingStreamSliderViewModel.uiBackgroundContext;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(num, audioSharingStreamSliderViewModel, name, null);
            this.L$0 = null;
            this.label = 1;
            obj = BuildersKt.withContext(coroutineContext, anonymousClass1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return (SliderState) obj;
    }
}
