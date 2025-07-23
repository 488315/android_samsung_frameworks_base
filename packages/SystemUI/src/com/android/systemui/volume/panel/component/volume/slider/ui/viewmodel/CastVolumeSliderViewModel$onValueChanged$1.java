package com.android.systemui.volume.panel.component.volume.slider.ui.viewmodel;

import android.media.session.MediaSession;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class CastVolumeSliderViewModel$onValueChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ float $newValue;
    int label;
    final /* synthetic */ CastVolumeSliderViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastVolumeSliderViewModel$onValueChanged$1(float f, CastVolumeSliderViewModel castVolumeSliderViewModel, Continuation continuation) {
        super(2, continuation);
        this.$newValue = f;
        this.this$0 = castVolumeSliderViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CastVolumeSliderViewModel$onValueChanged$1(this.$newValue, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CastVolumeSliderViewModel$onValueChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            int roundToInt = MathKt__MathJVMKt.roundToInt(this.$newValue);
            CastVolumeSliderViewModel castVolumeSliderViewModel = this.this$0;
            VolumePanelLogger volumePanelLogger = castVolumeSliderViewModel.volumePanelLogger;
            MediaSession.Token token = castVolumeSliderViewModel.session.sessionToken;
            volumePanelLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(8);
            LogBuffer logBuffer = volumePanelLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = token.toString();
            logMessageImpl.int1 = roundToInt;
            logBuffer.commit(obtain);
            CastVolumeSliderViewModel castVolumeSliderViewModel2 = this.this$0;
            MediaDeviceSessionInteractor mediaDeviceSessionInteractor = castVolumeSliderViewModel2.mediaDeviceSessionInteractor;
            this.label = 1;
            if (mediaDeviceSessionInteractor.setSessionVolume(castVolumeSliderViewModel2.session, roundToInt, this) == coroutineSingletons) {
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
