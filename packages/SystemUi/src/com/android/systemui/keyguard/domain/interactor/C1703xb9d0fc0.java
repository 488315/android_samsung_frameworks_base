package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2", m277f = "KeyguardQuickAffordanceInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2 */
/* loaded from: classes.dex */
final class C1703xb9d0fc0 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1703xb9d0fc0(KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, Continuation<? super C1703xb9d0fc0> continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1703xb9d0fc0(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1703xb9d0fc0) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.this$0;
        return Boolean.valueOf(DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(keyguardQuickAffordanceInteractor.devicePolicyManager, ((UserTrackerImpl) keyguardQuickAffordanceInteractor.userTracker).getUserId()));
    }
}
