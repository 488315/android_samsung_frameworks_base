package com.android.systemui.keyguard.domain.interactor;

import android.os.Trace;
import com.android.app.tracing.TraceProxy_platformKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceData;
import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public final class KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1(String str, Continuation continuation, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = keyguardQuickAffordanceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1 keyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1 = new KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1(this.$spanName, continuation, this.this$0);
        keyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1.L$0 = obj;
        return keyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = this.$spanName;
        TraceData traceData = (TraceData) TraceContextElementKt.traceThreadLocal.get();
        boolean isEnabled = Trace.isEnabled();
        if (traceData == null && !isEnabled) {
            str = "<none>";
        }
        if (traceData != null) {
            traceData.beginSpan(str);
        }
        int nextInt = isEnabled ? ThreadLocalRandom.current().nextInt() : 0;
        if (isEnabled) {
            TraceProxy_platformKt.asyncTraceForTrackBegin(nextInt, "Coroutines", str);
        }
        try {
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.this$0;
            return Boolean.valueOf(DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(keyguardQuickAffordanceInteractor.devicePolicyManager, ((UserTrackerImpl) keyguardQuickAffordanceInteractor.userTracker).getUserId()));
        } finally {
            if (isEnabled) {
                TraceProxy_platformKt.asyncTraceForTrackEnd(nextInt, "Coroutines");
            }
            if (traceData != null) {
                traceData.endSpan();
            }
        }
    }
}
