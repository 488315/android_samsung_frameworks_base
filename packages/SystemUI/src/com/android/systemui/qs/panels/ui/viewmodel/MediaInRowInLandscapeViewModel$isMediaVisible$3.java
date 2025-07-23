package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.media.controls.ui.view.MediaHostState;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaInRowInLandscapeViewModel$isMediaVisible$3 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaInRowInLandscapeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaInRowInLandscapeViewModel$isMediaVisible$3(MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaInRowInLandscapeViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaInRowInLandscapeViewModel$isMediaVisible$3 mediaInRowInLandscapeViewModel$isMediaVisible$3 = new MediaInRowInLandscapeViewModel$isMediaVisible$3(this.this$0, continuation);
        mediaInRowInLandscapeViewModel$isMediaVisible$3.L$0 = obj;
        return mediaInRowInLandscapeViewModel$isMediaVisible$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaInRowInLandscapeViewModel$isMediaVisible$3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel = this.this$0;
            MediaHostState mediaHostState = (MediaHostState) ((LinkedHashMap) mediaInRowInLandscapeViewModel.mediaHostStatesManager.mediaHostStates).get(new Integer(mediaInRowInLandscapeViewModel.inLocation));
            Boolean valueOf = Boolean.valueOf(mediaHostState != null ? mediaHostState.getVisible() : false);
            this.label = 1;
            if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
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
