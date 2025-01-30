package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$2", m277f = "KeyguardCoordinator.kt", m278l = {}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$isTrackingUnseen$2 */
/* loaded from: classes2.dex */
final class C2818xc0240c16 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2818xc0240c16(KeyguardCoordinator keyguardCoordinator, Continuation<? super C2818xc0240c16> continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        C2818xc0240c16 c2818xc0240c16 = new C2818xc0240c16(this.this$0, continuation);
        c2818xc0240c16.Z$0 = ((Boolean) obj).booleanValue();
        return c2818xc0240c16;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C2818xc0240c16) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        KeyguardCoordinatorLogger keyguardCoordinatorLogger = this.this$0.logger;
        keyguardCoordinatorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardCoordinatorLogger$logTrackingUnseen$2 keyguardCoordinatorLogger$logTrackingUnseen$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinatorLogger$logTrackingUnseen$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return (((LogMessage) obj2).getBool1() ? "Start" : "Stop").concat(" tracking unseen notifications.");
            }
        };
        LogBuffer logBuffer = keyguardCoordinatorLogger.buffer;
        LogMessage obtain = logBuffer.obtain("KeyguardCoordinator", logLevel, keyguardCoordinatorLogger$logTrackingUnseen$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
