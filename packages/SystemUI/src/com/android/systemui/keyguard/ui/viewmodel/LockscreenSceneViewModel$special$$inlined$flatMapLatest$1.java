package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenSceneViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ CommunalInteractor $communalInteractor$inlined;
    final /* synthetic */ DeviceEntryInteractor $deviceEntryInteractor$inlined;
    final /* synthetic */ ShadeInteractor $shadeInteractor$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ LockscreenSceneViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenSceneViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, DeviceEntryInteractor deviceEntryInteractor, CommunalInteractor communalInteractor, ShadeInteractor shadeInteractor, LockscreenSceneViewModel lockscreenSceneViewModel) {
        super(3, continuation);
        this.$deviceEntryInteractor$inlined = deviceEntryInteractor;
        this.$communalInteractor$inlined = communalInteractor;
        this.$shadeInteractor$inlined = shadeInteractor;
        this.this$0 = lockscreenSceneViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        LockscreenSceneViewModel$special$$inlined$flatMapLatest$1 lockscreenSceneViewModel$special$$inlined$flatMapLatest$1 = new LockscreenSceneViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$deviceEntryInteractor$inlined, this.$communalInteractor$inlined, this.$shadeInteractor$inlined, this.this$0);
        lockscreenSceneViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        lockscreenSceneViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return lockscreenSceneViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = !((Boolean) this.L$1).booleanValue() ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(MapsKt__MapsKt.emptyMap()) : FlowKt.combine(this.$deviceEntryInteractor$inlined.isUnlocked, this.$communalInteractor$inlined.isCommunalAvailable, ((ShadeInteractorImpl) this.$shadeInteractor$inlined).baseShadeInteractor.getShadeMode(), new LockscreenSceneViewModel$destinationScenes$1$1(this.this$0, null));
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
