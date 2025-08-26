package com.android.systemui.util;

import android.hardware.devicestate.DeviceStateManager;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class SecQsUiDisplayModeInteractor$foldState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SecQsUiDisplayModeInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQsUiDisplayModeInteractor$foldState$1(SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = secQsUiDisplayModeInteractor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, SecQsUiDisplayModeInteractor$foldState$1$deviceStateCallback$1 secQsUiDisplayModeInteractor$foldState$1$deviceStateCallback$1) {
        secQsUiDisplayModeInteractor.deviceStateManager.unregisterCallback(secQsUiDisplayModeInteractor$foldState$1$deviceStateCallback$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SecQsUiDisplayModeInteractor$foldState$1 secQsUiDisplayModeInteractor$foldState$1 = new SecQsUiDisplayModeInteractor$foldState$1(this.this$0, continuation);
        secQsUiDisplayModeInteractor$foldState$1.L$0 = obj;
        return secQsUiDisplayModeInteractor$foldState$1;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.devicestate.DeviceStateManager$DeviceStateCallback, com.android.systemui.util.SecQsUiDisplayModeInteractor$foldState$1$deviceStateCallback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.this$0;
            final ?? r1 = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.util.SecQsUiDisplayModeInteractor$foldState$1$deviceStateCallback$1
                public void onDeviceStateChanged(android.hardware.devicestate.DeviceState deviceState) {
                    int identifier = deviceState.getIdentifier();
                    boolean z = true;
                    if (identifier != 0) {
                        if (identifier == 1) {
                            return;
                        }
                        if (identifier != 5) {
                            z = false;
                        }
                    }
                    if (Intrinsics.areEqual(secQsUiDisplayModeInteractor.wasFolded, Boolean.valueOf(z))) {
                        return;
                    }
                    Boolean bool = secQsUiDisplayModeInteractor.wasFolded;
                    int identifier2 = deviceState.getIdentifier();
                    StringBuilder sb = new StringBuilder("onDeviceStateChanged from ");
                    sb.append(bool);
                    sb.append(" -> to ");
                    sb.append(z);
                    sb.append(", identifier = ");
                    RecyclerView$$ExternalSyntheticOutline0.m(identifier2, "SecQsUiDisplayModeInteractor", sb);
                    SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor2 = secQsUiDisplayModeInteractor;
                    Boolean boolValueOf = Boolean.valueOf(z);
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(z ? SecQsUiDisplayModeInteractor.FoldState.FOLD : SecQsUiDisplayModeInteractor.FoldState.UNFOLD);
                    secQsUiDisplayModeInteractor2.wasFolded = boolValueOf;
                }
            };
            this.this$0.deviceStateManager.registerCallback(this.this$0.mainExecutor, (DeviceStateManager.DeviceStateCallback) r1);
            final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.SecQsUiDisplayModeInteractor$foldState$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return SecQsUiDisplayModeInteractor$foldState$1.invokeSuspend$lambda$0(secQsUiDisplayModeInteractor2, r1);
                }
            };
            this.label = 1;
            if (BuildScopeKt.awaitClose(function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((SecQsUiDisplayModeInteractor$foldState$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
