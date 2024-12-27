package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SharedNotificationContainerViewModel$shadeCollapseFadeIn$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SharedNotificationContainerViewModel this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(!this.Z$0);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.Z$0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$shadeCollapseFadeIn$1(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sharedNotificationContainerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SharedNotificationContainerViewModel$shadeCollapseFadeIn$1 sharedNotificationContainerViewModel$shadeCollapseFadeIn$1 = new SharedNotificationContainerViewModel$shadeCollapseFadeIn$1(this.this$0, continuation);
        sharedNotificationContainerViewModel$shadeCollapseFadeIn$1.L$0 = obj;
        return sharedNotificationContainerViewModel$shadeCollapseFadeIn$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SharedNotificationContainerViewModel$shadeCollapseFadeIn$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0082 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c3  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x00c0 -> B:8:0x001a). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 0
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L3c
            if (r1 == r6) goto L34
            if (r1 == r5) goto L2c
            if (r1 == r4) goto L24
            if (r1 != r3) goto L1c
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)
        L1a:
            r12 = r1
            goto L43
        L1c:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L24:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L83
        L2c:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L6f
        L34:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L62
        L3c:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r12 = (kotlinx.coroutines.flow.FlowCollector) r12
        L43:
            kotlin.coroutines.CoroutineContext r1 = r11.getContext()
            boolean r1 = kotlinx.coroutines.JobKt.isActive(r1)
            if (r1 == 0) goto Lc3
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel r1 = r11.this$0
            kotlinx.coroutines.flow.Flow r1 = r1.isShadeLocked
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$1 r7 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$1
            r7.<init>(r2)
            r11.L$0 = r12
            r11.label = r6
            java.lang.Object r1 = kotlinx.coroutines.flow.FlowKt.first(r1, r7, r11)
            if (r1 != r0) goto L61
            return r0
        L61:
            r1 = r12
        L62:
            java.lang.Boolean r12 = java.lang.Boolean.FALSE
            r11.L$0 = r1
            r11.label = r5
            java.lang.Object r12 = r1.emit(r12, r11)
            if (r12 != r0) goto L6f
            return r0
        L6f:
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel r12 = r11.this$0
            kotlinx.coroutines.flow.Flow r12 = r12.isShadeLocked
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$2 r7 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$2
            r7.<init>(r2)
            r11.L$0 = r1
            r11.label = r4
            java.lang.Object r12 = kotlinx.coroutines.flow.FlowKt.first(r12, r7, r11)
            if (r12 != r0) goto L83
            return r0
        L83:
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel r12 = r11.this$0
            r12.getClass()
            kotlin.jvm.internal.Ref$BooleanRef r7 = new kotlin.jvm.internal.Ref$BooleanRef
            r7.<init>()
            r7.element = r6
            com.android.systemui.keyguard.shared.model.Edge$Companion r8 = com.android.systemui.keyguard.shared.model.Edge.Companion
            com.android.systemui.keyguard.shared.model.KeyguardState r9 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
            com.android.systemui.keyguard.shared.model.KeyguardState r10 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
            com.android.systemui.keyguard.shared.model.Edge$StateToState r8 = com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(r8, r9, r10)
            java.lang.String r9 = com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor.TAG
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r9 = r12.keyguardTransitionInteractor
            kotlinx.coroutines.flow.Flow r8 = r9.isInTransition(r8, r2)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$awaitCollapse$2 r9 = com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$awaitCollapse$2.INSTANCE
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 r10 = new kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1
            kotlinx.coroutines.flow.StateFlow r12 = r12.isOnLockscreenWithoutShade
            r10.<init>(r12, r8, r9)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$awaitCollapse$3 r12 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$awaitCollapse$3
            r12.<init>(r7, r2)
            kotlinx.coroutines.flow.SafeFlow r12 = kotlinx.coroutines.flow.FlowKt.transformWhile(r10, r12)
            com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$3 r7 = new com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1$3
            r7.<init>()
            r11.L$0 = r1
            r11.label = r3
            java.lang.Object r12 = r12.collect(r7, r11)
            if (r12 != r0) goto L1a
            return r0
        Lc3:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$shadeCollapseFadeIn$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
