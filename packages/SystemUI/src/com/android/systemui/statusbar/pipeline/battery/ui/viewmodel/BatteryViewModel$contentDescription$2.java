package com.android.systemui.statusbar.pipeline.battery.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.statusbar.pipeline.battery.domain.interactor.BatteryAttributionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BatteryViewModel$contentDescription$2 extends SuspendLambda implements Function4 {
    final /* synthetic */ Context $context;
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryViewModel$contentDescription$2(Context context, Continuation continuation) {
        super(4, continuation);
        this.$context = context;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        int intValue = ((Number) obj3).intValue();
        BatteryViewModel$contentDescription$2 batteryViewModel$contentDescription$2 = new BatteryViewModel$contentDescription$2(this.$context, (Continuation) obj4);
        batteryViewModel$contentDescription$2.L$0 = (BatteryAttributionModel) obj;
        batteryViewModel$contentDescription$2.Z$0 = booleanValue;
        batteryViewModel$contentDescription$2.I$0 = intValue;
        return batteryViewModel$contentDescription$2.invokeSuspend(Unit.INSTANCE);
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
        int i = this.I$0;
        return z ? new ContentDescription.Resource(R.string.accessibility_battery_unknown) : batteryAttributionModel == BatteryAttributionModel.Defend ? new ContentDescription.Loaded(this.$context.getString(R.string.accessibility_battery_level_charging_paused, new Integer(i))) : batteryAttributionModel == BatteryAttributionModel.Charging ? new ContentDescription.Loaded(this.$context.getString(R.string.accessibility_battery_level_charging, new Integer(i))) : new ContentDescription.Loaded(this.$context.getString(R.string.accessibility_battery_level, new Integer(i)));
    }
}
