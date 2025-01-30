package com.android.keyguard;

import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.keyguard.WifiTextManager$register$1", m277f = "WifiTextManager.kt", m278l = {26}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class WifiTextManager$register$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $update;
    int label;
    final /* synthetic */ WifiTextManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiTextManager$register$1(WifiTextManager wifiTextManager, Function2 function2, Continuation<? super WifiTextManager$register$1> continuation) {
        super(2, continuation);
        this.this$0 = wifiTextManager;
        this.$update = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WifiTextManager$register$1(this.this$0, this.$update, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiTextManager$register$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final WifiTextManager wifiTextManager = this.this$0;
            WifiInteractorImpl$special$$inlined$map$1 wifiInteractorImpl$special$$inlined$map$1 = ((WifiInteractorImpl) wifiTextManager.wifiInteractor).ssid;
            final Function2 function2 = this.$update;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.WifiTextManager$register$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    String str = (String) obj2;
                    WifiTextManager wifiTextManager2 = WifiTextManager.this;
                    wifiTextManager2.ssid = str;
                    function2.invoke(str, Boolean.valueOf(wifiTextManager2.connected));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (wifiInteractorImpl$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
