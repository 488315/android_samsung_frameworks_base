package com.android.systemui.qs.panels.ui.viewmodel;

import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.view.MediaHostState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class MediaInRowInLandscapeViewModel$isMediaVisible$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaInRowInLandscapeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaInRowInLandscapeViewModel$isMediaVisible$2(MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaInRowInLandscapeViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaInRowInLandscapeViewModel$isMediaVisible$2 mediaInRowInLandscapeViewModel$isMediaVisible$2 = new MediaInRowInLandscapeViewModel$isMediaVisible$2(this.this$0, continuation);
        mediaInRowInLandscapeViewModel$isMediaVisible$2.L$0 = obj;
        return mediaInRowInLandscapeViewModel$isMediaVisible$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaInRowInLandscapeViewModel$isMediaVisible$2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$isMediaVisible$2$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel = this.this$0;
            final ?? r1 = new MediaHostStatesManager.Callback() { // from class: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$isMediaVisible$2$callback$1
                @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
                public final void onHostStateChanged(int i2, MediaHostState mediaHostState) {
                    if (i2 == mediaInRowInLandscapeViewModel.inLocation) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(mediaHostState.getVisible()));
                    }
                }
            };
            this.this$0.mediaHostStatesManager.callbacks.add(r1);
            final MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel$isMediaVisible$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    mediaInRowInLandscapeViewModel2.mediaHostStatesManager.callbacks.remove(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
