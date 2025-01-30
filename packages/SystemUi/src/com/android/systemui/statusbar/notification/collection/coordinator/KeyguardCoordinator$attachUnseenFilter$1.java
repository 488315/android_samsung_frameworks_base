package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$attachUnseenFilter$1", m277f = "KeyguardCoordinator.kt", m278l = {112}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class KeyguardCoordinator$attachUnseenFilter$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$attachUnseenFilter$1(KeyguardCoordinator keyguardCoordinator, Continuation<? super KeyguardCoordinator$attachUnseenFilter$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardCoordinator$attachUnseenFilter$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardCoordinator$attachUnseenFilter$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardCoordinator keyguardCoordinator = this.this$0;
            this.label = 1;
            Object collectLatest = FlowKt.collectLatest(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.transformLatest(((KeyguardRepositoryImpl) keyguardCoordinator.keyguardRepository).isKeyguardShowing, new C2817xc0240c15(keyguardCoordinator, null))), new C2818xc0240c16(keyguardCoordinator, null)), new KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2(keyguardCoordinator, new Ref$BooleanRef(), null), this);
            if (collectLatest != obj2) {
                collectLatest = Unit.INSTANCE;
            }
            if (collectLatest == obj2) {
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
