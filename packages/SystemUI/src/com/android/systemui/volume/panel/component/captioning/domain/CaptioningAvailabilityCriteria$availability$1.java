package com.android.systemui.volume.panel.component.captioning.domain;

import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class CaptioningAvailabilityCriteria$availability$1 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ CaptioningAvailabilityCriteria this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CaptioningAvailabilityCriteria$availability$1(CaptioningAvailabilityCriteria captioningAvailabilityCriteria, Continuation continuation) {
        super(2, continuation);
        this.this$0 = captioningAvailabilityCriteria;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CaptioningAvailabilityCriteria$availability$1 captioningAvailabilityCriteria$availability$1 = new CaptioningAvailabilityCriteria$availability$1(this.this$0, continuation);
        captioningAvailabilityCriteria$availability$1.Z$0 = ((Boolean) obj).booleanValue();
        return captioningAvailabilityCriteria$availability$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((CaptioningAvailabilityCriteria$availability$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.uiEventLogger.log(this.Z$0 ? VolumePanelUiEvent.VOLUME_PANEL_LIVE_CAPTION_TOGGLE_SHOWN : VolumePanelUiEvent.VOLUME_PANEL_LIVE_CAPTION_TOGGLE_GONE);
        return Unit.INSTANCE;
    }
}
