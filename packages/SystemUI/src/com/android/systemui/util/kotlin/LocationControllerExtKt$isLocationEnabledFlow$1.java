package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LocationControllerExtKt$isLocationEnabledFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocationController $this_isLocationEnabledFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocationControllerExtKt$isLocationEnabledFlow$1(LocationController locationController, Continuation continuation) {
        super(2, continuation);
        this.$this_isLocationEnabledFlow = locationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LocationControllerExtKt$isLocationEnabledFlow$1 locationControllerExtKt$isLocationEnabledFlow$1 = new LocationControllerExtKt$isLocationEnabledFlow$1(this.$this_isLocationEnabledFlow, continuation);
        locationControllerExtKt$isLocationEnabledFlow$1.L$0 = obj;
        return locationControllerExtKt$isLocationEnabledFlow$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.util.kotlin.LocationControllerExtKt$isLocationEnabledFlow$1$locationCallback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new LocationController.LocationChangeCallback() { // from class: com.android.systemui.util.kotlin.LocationControllerExtKt$isLocationEnabledFlow$1$locationCallback$1
                @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
                public void onLocationSettingsChanged(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(z));
                }

                @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
                public /* bridge */ /* synthetic */ void onLocationActiveChanged(boolean z) {
                }
            };
            ((LocationControllerImpl) this.$this_isLocationEnabledFlow).addCallback(r1);
            final LocationController locationController = this.$this_isLocationEnabledFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.LocationControllerExtKt$isLocationEnabledFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2316invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2316invoke() {
                    ((LocationControllerImpl) LocationController.this).removeCallback(r1);
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((LocationControllerExtKt$isLocationEnabledFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
