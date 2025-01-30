package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2", m277f = "KeyguardCoordinator.kt", m278l = {152, 162}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$BooleanRef $clearUnseenOnBeginTracking;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2(KeyguardCoordinator keyguardCoordinator, Ref$BooleanRef ref$BooleanRef, Continuation<? super KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2> continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
        this.$clearUnseenOnBeginTracking = ref$BooleanRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2 keyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2 = new KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2(this.this$0, this.$clearUnseenOnBeginTracking, continuation);
        keyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardCoordinator$trackUnseenNotificationsWhileUnlocked$2) create(Boolean.valueOf(((Boolean) obj).booleanValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.Z$0) {
                Ref$BooleanRef ref$BooleanRef = this.$clearUnseenOnBeginTracking;
                if (ref$BooleanRef.element) {
                    ref$BooleanRef.element = false;
                    LogBuffer.log$default(this.this$0.logger.buffer, "KeyguardCoordinator", LogLevel.DEBUG, "Notifications have been marked as seen now that device is unlocked.", null, 8, null);
                    this.this$0.unseenNotifications.clear();
                }
                invalidateList("keyguard no longer showing");
                KeyguardCoordinator keyguardCoordinator = this.this$0;
                this.label = 2;
                keyguardCoordinator.getClass();
                Object coroutineScope = CoroutineScopeKt.coroutineScope(new KeyguardCoordinator$trackUnseenNotifications$2(keyguardCoordinator, null), this);
                if (coroutineScope != obj2) {
                    coroutineScope = Unit.INSTANCE;
                }
                if (coroutineScope == obj2) {
                    return obj2;
                }
            } else {
                KeyguardCoordinator keyguardCoordinator2 = this.this$0;
                long j = KeyguardCoordinator.SEEN_TIMEOUT;
                this.label = 1;
                Object first = FlowKt.first(FlowKt.transformLatest(((KeyguardRepositoryImpl) keyguardCoordinator2.keyguardRepository).isDozing, new KeyguardCoordinator$awaitTimeSpentNotDozing$2(j, null)), this);
                if (first != obj2) {
                    first = Unit.INSTANCE;
                }
                if (first == obj2) {
                    return obj2;
                }
                this.$clearUnseenOnBeginTracking.element = true;
                LogBuffer.log$default(this.this$0.logger.buffer, "KeyguardCoordinator", LogLevel.DEBUG, "Notifications on lockscreen will be marked as seen when unlocked.", null, 8, null);
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
            this.$clearUnseenOnBeginTracking.element = true;
            LogBuffer.log$default(this.this$0.logger.buffer, "KeyguardCoordinator", LogLevel.DEBUG, "Notifications on lockscreen will be marked as seen when unlocked.", null, 8, null);
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
