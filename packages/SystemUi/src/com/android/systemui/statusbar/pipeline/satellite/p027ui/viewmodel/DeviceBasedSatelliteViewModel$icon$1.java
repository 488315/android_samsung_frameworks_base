package com.android.systemui.statusbar.pipeline.satellite.p027ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.satellite.p027ui.model.SatelliteIconModel;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModel$icon$1", m277f = "DeviceBasedSatelliteViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class DeviceBasedSatelliteViewModel$icon$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public DeviceBasedSatelliteViewModel$icon$1(Continuation<? super DeviceBasedSatelliteViewModel$icon$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj3).intValue();
        DeviceBasedSatelliteViewModel$icon$1 deviceBasedSatelliteViewModel$icon$1 = new DeviceBasedSatelliteViewModel$icon$1((Continuation) obj4);
        deviceBasedSatelliteViewModel$icon$1.Z$0 = booleanValue;
        deviceBasedSatelliteViewModel$icon$1.L$0 = (SatelliteConnectionState) obj2;
        deviceBasedSatelliteViewModel$icon$1.I$0 = intValue;
        return deviceBasedSatelliteViewModel$icon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        SatelliteConnectionState satelliteConnectionState = (SatelliteConnectionState) this.L$0;
        int i = this.I$0;
        if (!z) {
            return null;
        }
        SatelliteIconModel.INSTANCE.getClass();
        int i2 = SatelliteIconModel.WhenMappings.$EnumSwitchMapping$0[satelliteConnectionState.ordinal()];
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_anim, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_available));
        }
        if (i2 == 4) {
            return SatelliteIconModel.fromSignalStrength(i);
        }
        throw new NoWhenBranchMatchedException();
    }
}
