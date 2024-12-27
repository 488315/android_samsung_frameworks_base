package com.android.systemui.keyguard.ui.viewmodel;

import android.R;
import com.android.settingslib.Utils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceEntryForegroundViewModel$color$1$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $useBgProtection;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryForegroundViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryForegroundViewModel$color$1$1$2(DeviceEntryForegroundViewModel deviceEntryForegroundViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryForegroundViewModel;
        this.$useBgProtection = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryForegroundViewModel$color$1$1$2 deviceEntryForegroundViewModel$color$1$1$2 = new DeviceEntryForegroundViewModel$color$1$1$2(this.this$0, this.$useBgProtection, continuation);
        deviceEntryForegroundViewModel$color$1$1$2.L$0 = obj;
        return deviceEntryForegroundViewModel$color$1$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryForegroundViewModel$color$1$1$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            DeviceEntryForegroundViewModel deviceEntryForegroundViewModel = this.this$0;
            Integer num = new Integer(this.$useBgProtection ? Utils.getColorAttrDefaultColor(deviceEntryForegroundViewModel.context, R.attr.textColorPrimary, 0) : Utils.getColorAttrDefaultColor(deviceEntryForegroundViewModel.context, com.android.systemui.R.attr.wallpaperTextColorAccent, 0));
            this.label = 1;
            if (flowCollector.emit(num, this) == coroutineSingletons) {
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
