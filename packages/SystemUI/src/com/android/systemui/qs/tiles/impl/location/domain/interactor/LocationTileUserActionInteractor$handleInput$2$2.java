package com.android.systemui.qs.tiles.impl.location.domain.interactor;

import com.android.systemui.statusbar.policy.LocationControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LocationTileUserActionInteractor$handleInput$2$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $wasEnabled;
    int label;
    final /* synthetic */ LocationTileUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocationTileUserActionInteractor$handleInput$2$2(LocationTileUserActionInteractor locationTileUserActionInteractor, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = locationTileUserActionInteractor;
        this.$wasEnabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LocationTileUserActionInteractor$handleInput$2$2(this.this$0, this.$wasEnabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LocationTileUserActionInteractor$handleInput$2$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((LocationControllerImpl) this.this$0.locationController).setLocationEnabled(!this.$wasEnabled));
    }
}
