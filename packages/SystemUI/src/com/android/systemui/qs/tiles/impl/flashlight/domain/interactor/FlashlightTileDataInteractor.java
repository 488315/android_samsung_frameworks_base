package com.android.systemui.qs.tiles.impl.flashlight.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.flashlight.domain.model.FlashlightTileModel;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class FlashlightTileDataInteractor implements QSTileDataInteractor {
    public final FlashlightController flashlightController;

    /* renamed from: com.android.systemui.qs.tiles.impl.flashlight.domain.interactor.FlashlightTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = FlashlightTileDataInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.impl.flashlight.domain.interactor.FlashlightTileDataInteractor$tileData$1$callback$1, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final FlashlightTileDataInteractor flashlightTileDataInteractor = FlashlightTileDataInteractor.this;
                final ?? r1 = new FlashlightController.FlashlightListener() { // from class: com.android.systemui.qs.tiles.impl.flashlight.domain.interactor.FlashlightTileDataInteractor$tileData$1$callback$1
                    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
                    public final void onFlashlightAvailabilityChanged(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(z ? FlashlightTileModel.FlashlightAvailable.m2929boximpl(((FlashlightControllerImpl) flashlightTileDataInteractor.flashlightController).isEnabled()) : FlashlightTileModel.FlashlightTemporarilyUnavailable.INSTANCE);
                    }

                    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
                    public final void onFlashlightChanged(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(FlashlightTileModel.FlashlightAvailable.m2929boximpl(z));
                    }

                    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
                    public final void onFlashlightError() {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(FlashlightTileModel.FlashlightAvailable.m2929boximpl(false));
                    }
                };
                ((FlashlightControllerImpl) FlashlightTileDataInteractor.this.flashlightController).addCallback(r1);
                final FlashlightTileDataInteractor flashlightTileDataInteractor2 = FlashlightTileDataInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.flashlight.domain.interactor.FlashlightTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((FlashlightControllerImpl) flashlightTileDataInteractor2.flashlightController).removeCallback(r1);
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

    public FlashlightTileDataInteractor(FlashlightController flashlightController) {
        this.flashlightController = flashlightController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(((FlashlightControllerImpl) this.flashlightController).mHasFlashlight));
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(null));
    }
}
