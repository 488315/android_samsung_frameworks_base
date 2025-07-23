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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class LocationControllerExtKt$isLocationEnabledFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocationController $this_isLocationEnabledFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocationControllerExtKt$isLocationEnabledFlow$1(LocationController locationController, Continuation continuation) {
        super(2, continuation);
        this.$this_isLocationEnabledFlow = locationController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(LocationController locationController, LocationControllerExtKt$isLocationEnabledFlow$1$locationCallback$1 locationControllerExtKt$isLocationEnabledFlow$1$locationCallback$1) {
        ((LocationControllerImpl) locationController).removeCallback(locationControllerExtKt$isLocationEnabledFlow$1$locationCallback$1);
        return Unit.INSTANCE;
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
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(Boolean.valueOf(z));
                }

                @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
                public /* bridge */ /* synthetic */ void onLocationActiveChanged(boolean z) {
                }
            };
            ((LocationControllerImpl) this.$this_isLocationEnabledFlow).addCallback(r1);
            final LocationController locationController = this.$this_isLocationEnabledFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.LocationControllerExtKt$isLocationEnabledFlow$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit invokeSuspend$lambda$0;
                    invokeSuspend$lambda$0 = LocationControllerExtKt$isLocationEnabledFlow$1.invokeSuspend$lambda$0(LocationController.this, r1);
                    return invokeSuspend$lambda$0;
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
