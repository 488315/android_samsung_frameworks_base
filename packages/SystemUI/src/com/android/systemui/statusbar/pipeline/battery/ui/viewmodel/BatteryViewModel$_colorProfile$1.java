package com.android.systemui.statusbar.pipeline.battery.ui.viewmodel;

import com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryAttributionModel;
import com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryColors;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class BatteryViewModel$_colorProfile$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BatteryAttributionModel.values().length];
            try {
                iArr[BatteryAttributionModel.Charging.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BatteryAttributionModel.Defend.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BatteryAttributionModel.PowerSave.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BatteryViewModel$_colorProfile$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        BatteryViewModel$_colorProfile$1 batteryViewModel$_colorProfile$1 = new BatteryViewModel$_colorProfile$1((Continuation) obj3);
        batteryViewModel$_colorProfile$1.L$0 = (BatteryAttributionModel) obj;
        batteryViewModel$_colorProfile$1.Z$0 = zBooleanValue;
        return batteryViewModel$_colorProfile$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BatteryAttributionModel batteryAttributionModel = (BatteryAttributionModel) this.L$0;
        boolean z = this.Z$0;
        int i = batteryAttributionModel == null ? -1 : WhenMappings.$EnumSwitchMapping$0[batteryAttributionModel.ordinal()];
        return (i == 1 || i == 2) ? new ColorProfile(BatteryColors.DarkThemeChargingColors.INSTANCE, BatteryColors.LightThemeChargingColors.INSTANCE) : i != 3 ? z ? new ColorProfile(BatteryColors.DarkThemeErrorColors.INSTANCE, BatteryColors.LightThemeErrorColors.INSTANCE) : new ColorProfile(BatteryColors.DarkThemeDefaultColors.INSTANCE, BatteryColors.LightThemeDefaultColors.INSTANCE) : new ColorProfile(BatteryColors.DarkThemePowerSaveColors.INSTANCE, BatteryColors.LightThemePowerSaveColors.INSTANCE);
    }
}
