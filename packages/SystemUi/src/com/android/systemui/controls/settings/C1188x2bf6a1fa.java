package com.android.systemui.controls.settings;

import android.content.pm.UserInfo;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1", m277f = "ControlsSettingsRepositoryImpl.kt", m278l = {190}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1 */
/* loaded from: classes.dex */
public final class C1188x2bf6a1fa extends SuspendLambda implements Function3 {
    final /* synthetic */ String $setting$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ ControlsSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1188x2bf6a1fa(Continuation continuation, ControlsSettingsRepositoryImpl controlsSettingsRepositoryImpl, String str) {
        super(3, continuation);
        this.this$0 = controlsSettingsRepositoryImpl;
        this.$setting$inlined = str;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C1188x2bf6a1fa c1188x2bf6a1fa = new C1188x2bf6a1fa((Continuation) obj3, this.this$0, this.$setting$inlined);
        c1188x2bf6a1fa.L$0 = (FlowCollector) obj;
        c1188x2bf6a1fa.L$1 = obj2;
        return c1188x2bf6a1fa.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            UserInfo userInfo = (UserInfo) this.L$1;
            ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
            ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1 controlsSettingsRepositoryImpl$makeFlowForSetting$1$1 = new ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1(this.this$0, userInfo, this.$setting$inlined, null);
            conflatedCallbackFlow.getClass();
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(ConflatedCallbackFlow.conflatedCallbackFlow(controlsSettingsRepositoryImpl$makeFlowForSetting$1$1), this.this$0.backgroundDispatcher));
            this.label = 1;
            if (FlowKt.emitAll(this, distinctUntilChanged, flowCollector) == coroutineSingletons) {
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
