package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class AudioSharingStreamSliderViewModel$slider$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioSharingStreamSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioSharingStreamSliderViewModel$slider$1(AudioSharingStreamSliderViewModel audioSharingStreamSliderViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioSharingStreamSliderViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioSharingStreamSliderViewModel$slider$1 audioSharingStreamSliderViewModel$slider$1 = new AudioSharingStreamSliderViewModel$slider$1(this.this$0, continuation);
        audioSharingStreamSliderViewModel$slider$1.L$0 = obj;
        return audioSharingStreamSliderViewModel$slider$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioSharingStreamSliderViewModel$slider$1) create((Integer) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Integer num = (Integer) this.L$0;
        if (num != null) {
            VolumePanelLogger volumePanelLogger = this.this$0.volumePanelLogger;
            int iIntValue = num.intValue();
            volumePanelLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer = volumePanelLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = iIntValue;
            logBuffer.commit(logMessageObtain);
        }
        return Unit.INSTANCE;
    }
}
