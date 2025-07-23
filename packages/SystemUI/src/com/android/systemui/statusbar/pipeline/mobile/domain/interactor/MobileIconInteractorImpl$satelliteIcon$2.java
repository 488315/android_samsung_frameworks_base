package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import com.android.systemui.statusbar.pipeline.satellite.ui.model.SatelliteIconModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileIconInteractorImpl$satelliteIcon$2 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$satelliteIcon$2(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        MobileIconInteractorImpl$satelliteIcon$2 mobileIconInteractorImpl$satelliteIcon$2 = new MobileIconInteractorImpl$satelliteIcon$2(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$satelliteIcon$2.I$0 = intValue;
        mobileIconInteractorImpl$satelliteIcon$2.Z$0 = booleanValue;
        return mobileIconInteractorImpl$satelliteIcon$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Icon.Resource fromSignalStrengthVZW;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        boolean z = this.Z$0;
        MobileIconInteractorImpl mobileIconInteractorImpl = this.this$0;
        if (!mobileIconInteractorImpl.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_USA_VZW, mobileIconInteractorImpl.slotId, new Object[0])) {
            MobileIconInteractorImpl mobileIconInteractorImpl2 = this.this$0;
            if (!Intrinsics.areEqual(mobileIconInteractorImpl2.carrierInfraMediator.get(CarrierInfraMediator.Values.ICON_BRANDING, mobileIconInteractorImpl2.slotId, new Object[0]), "VZW_OPEN")) {
                MobileIconInteractorImpl mobileIconInteractorImpl3 = this.this$0;
                if (!ArraysKt___ArraysKt.contains(mobileIconInteractorImpl3.carrierIdOfVzwMVNO, mobileIconInteractorImpl3.connectionRepository.carrierId.getValue())) {
                    if (z) {
                        if (this.this$0.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_KDDI_DEVICE, 0, new Object[0])) {
                            SatelliteIconModel.INSTANCE.getClass();
                            fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrengthKDDI(i);
                            if (fromSignalStrengthVZW == null) {
                                fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrengthKDDI(0);
                                fromSignalStrengthVZW.getClass();
                            }
                        } else {
                            SatelliteIconModel.INSTANCE.getClass();
                            fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrength(i);
                            if (fromSignalStrengthVZW == null) {
                                fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrength(0);
                                fromSignalStrengthVZW.getClass();
                            }
                        }
                    } else if (this.this$0.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.IS_KDDI_DEVICE, 0, new Object[0])) {
                        SatelliteIconModel satelliteIconModel = SatelliteIconModel.INSTANCE;
                        SatelliteConnectionState satelliteConnectionState = SatelliteConnectionState.On;
                        satelliteIconModel.getClass();
                        int i2 = SatelliteIconModel.WhenMappings.$EnumSwitchMapping$0[satelliteConnectionState.ordinal()];
                        if (i2 == 1 || i2 == 2 || i2 == 3) {
                            fromSignalStrengthVZW = new Icon.Resource(R.drawable.stat_sys_sos_satellite_anim_2_level, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_available));
                        } else {
                            if (i2 != 4) {
                                throw new NoWhenBranchMatchedException();
                            }
                            fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrengthKDDI(i);
                        }
                        fromSignalStrengthVZW.getClass();
                    } else {
                        SatelliteIconModel satelliteIconModel2 = SatelliteIconModel.INSTANCE;
                        SatelliteConnectionState satelliteConnectionState2 = SatelliteConnectionState.On;
                        satelliteIconModel2.getClass();
                        fromSignalStrengthVZW = SatelliteIconModel.fromConnectionState(satelliteConnectionState2, i);
                        fromSignalStrengthVZW.getClass();
                    }
                    return new SignalIconModel.Satellite(i, fromSignalStrengthVZW);
                }
            }
        }
        SatelliteIconModel.INSTANCE.getClass();
        fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrengthVZW(i);
        if (fromSignalStrengthVZW == null) {
            fromSignalStrengthVZW = SatelliteIconModel.fromSignalStrengthVZW(0);
            fromSignalStrengthVZW.getClass();
        }
        return new SignalIconModel.Satellite(i, fromSignalStrengthVZW);
    }
}
