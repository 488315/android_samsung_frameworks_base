package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.FlowTracing$$ExternalSyntheticLambda1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AccessibilityRepositoryImpl$isEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AccessibilityRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityRepositoryImpl$isEnabled$1(AccessibilityRepositoryImpl accessibilityRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = accessibilityRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AccessibilityRepositoryImpl$isEnabled$1 accessibilityRepositoryImpl$isEnabled$1 = new AccessibilityRepositoryImpl$isEnabled$1(this.this$0, continuation);
        accessibilityRepositoryImpl$isEnabled$1.L$0 = obj;
        return accessibilityRepositoryImpl$isEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityRepositoryImpl$isEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final AccessibilityRepositoryImpl$isEnabled$1$listener$1 accessibilityRepositoryImpl$isEnabled$1$listener$1 = new AccessibilityRepositoryImpl$isEnabled$1$listener$1(producerScope);
            AccessibilityManager.AccessibilityStateChangeListener accessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() { // from class: com.android.systemui.accessibility.data.repository.AccessibilityRepositoryKt$sam$android_view_accessibility_AccessibilityManager_AccessibilityStateChangeListener$0
                @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
                public final /* synthetic */ void onAccessibilityStateChanged(boolean z) {
                    Function1.this.mo779invoke(Boolean.valueOf(z));
                }
            };
            this.this$0.manager.addAccessibilityStateChangeListener(accessibilityStateChangeListener);
            ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Boolean.valueOf(this.this$0.manager.isEnabled()));
            FlowTracing flowTracing = FlowTracing.INSTANCE;
            AccessibilityRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0 accessibilityRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0 = new AccessibilityRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0(this.this$0, accessibilityStateChangeListener, 0);
            this.label = 1;
            flowTracing.getClass();
            Object awaitClose = ProduceKt.awaitClose(producerScope, new FlowTracing$$ExternalSyntheticLambda1("AccessibilityRepository", accessibilityRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0), this);
            if (awaitClose != obj2) {
                awaitClose = Unit.INSTANCE;
            }
            if (awaitClose == obj2) {
                return obj2;
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
