package com.android.systemui.qs.tiles.impl.battery.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.battery.domain.model.BatterySaverTileModel;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.kotlin.BatteryControllerExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public class BatterySaverTileDataInteractor implements QSTileDataInteractor {
    public final BatteryController batteryController;
    public final CoroutineContext bgCoroutineContext;

    /* renamed from: com.android.systemui.qs.tiles.impl.battery.domain.interactor.BatterySaverTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function4 {
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
            ((Number) obj3).intValue();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj4);
            anonymousClass1.Z$0 = zBooleanValue;
            anonymousClass1.Z$1 = zBooleanValue2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new BatterySaverTileModel.Standard(this.Z$0, this.Z$1);
        }
    }

    public BatterySaverTileDataInteractor(CoroutineContext coroutineContext, BatteryController batteryController) {
        this.bgCoroutineContext = coroutineContext;
        this.batteryController = batteryController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        BatteryController batteryController = this.batteryController;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(BatteryControllerExtKt.isDevicePluggedIn(batteryController));
        CoroutineContext coroutineContext = this.bgCoroutineContext;
        return FlowKt.combine(FlowKt.flowOn(flowDistinctUntilChanged, coroutineContext), FlowKt.flowOn(FlowKt.distinctUntilChanged(BatteryControllerExtKt.isBatteryPowerSaveEnabled(batteryController)), coroutineContext), FlowKt.flowOn(FlowKt.distinctUntilChanged(BatteryControllerExtKt.getBatteryLevel(batteryController)), coroutineContext), new AnonymousClass1(null));
    }
}
