package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function3 $onStart$inlined;
    final /* synthetic */ MediaDeviceSession $session$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaDeviceSessionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1(Continuation continuation, MediaDeviceSessionInteractor mediaDeviceSessionInteractor, MediaDeviceSession mediaDeviceSession, Function3 function3) {
        super(3, continuation);
        this.this$0 = mediaDeviceSessionInteractor;
        this.$session$inlined = mediaDeviceSession;
        this.$onStart$inlined = function3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1 mediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1 = new MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$session$inlined, this.$onStart$inlined);
        mediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1.L$1 = obj2;
        return mediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaController access$findControllerForSession = MediaDeviceSessionInteractor.access$findControllerForSession(this.this$0, (List) this.L$1, this.$session$inlined);
            if (access$findControllerForSession == null) {
                flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            } else {
                MediaControllerInteractorImpl mediaControllerInteractorImpl = (MediaControllerInteractorImpl) this.this$0.mediaControllerInteractor;
                mediaControllerInteractorImpl.getClass();
                flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MediaDeviceSessionInteractor$stateChanges$1$1(this.$onStart$inlined, access$findControllerForSession, null), FlowConflatedKt.conflatedCallbackFlow(new MediaControllerInteractorImpl$stateChanges$1(access$findControllerForSession, mediaControllerInteractorImpl, null)));
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowCollector) == coroutineSingletons) {
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
