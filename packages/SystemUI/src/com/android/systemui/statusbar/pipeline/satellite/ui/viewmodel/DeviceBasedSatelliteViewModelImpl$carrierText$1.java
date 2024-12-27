package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class DeviceBasedSatelliteViewModelImpl$carrierText$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Context $context;
    final /* synthetic */ LogBuffer $logBuffer;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SatelliteConnectionState.values().length];
            try {
                iArr[SatelliteConnectionState.On.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SatelliteConnectionState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SatelliteConnectionState.Off.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SatelliteConnectionState.Unknown.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceBasedSatelliteViewModelImpl$carrierText$1(LogBuffer logBuffer, Context context, Continuation continuation) {
        super(3, continuation);
        this.$logBuffer = logBuffer;
        this.$context = context;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        DeviceBasedSatelliteViewModelImpl$carrierText$1 deviceBasedSatelliteViewModelImpl$carrierText$1 = new DeviceBasedSatelliteViewModelImpl$carrierText$1(this.$logBuffer, this.$context, (Continuation) obj3);
        deviceBasedSatelliteViewModelImpl$carrierText$1.Z$0 = booleanValue;
        deviceBasedSatelliteViewModelImpl$carrierText$1.L$0 = (SatelliteConnectionState) obj2;
        return deviceBasedSatelliteViewModelImpl$carrierText$1.invokeSuspend(Unit.INSTANCE);
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
        LogBuffer logBuffer = this.$logBuffer;
        LogMessage obtain = logBuffer.obtain("DeviceBasedSatelliteViewModel", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel.DeviceBasedSatelliteViewModelImpl$carrierText$1.2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                return "Updating carrier text. shouldShow=" + logMessage.getBool1() + " connectionState=" + logMessage.getStr1();
            }
        }, null);
        ((LogMessageImpl) obtain).bool1 = z;
        ((LogMessageImpl) obtain).str1 = satelliteConnectionState.name();
        logBuffer.commit(obtain);
        if (!z) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[satelliteConnectionState.ordinal()];
        if (i == 1 || i == 2) {
            return this.$context.getString(R.string.satellite_connected_carrier_text);
        }
        if (i == 3 || i == 4) {
            return this.$context.getString(R.string.satellite_emergency_only_carrier_text);
        }
        throw new NoWhenBranchMatchedException();
    }
}
