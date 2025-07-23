package com.android.systemui.statusbar.notification.collection.coordinator;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1 implements Flow {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2<T> implements FlowCollector {
        final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends ContinuationImpl {
            Object L$0;
            int label;
            /* synthetic */ Object result;

            public AnonymousClass1(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass2.this.emit(null, this);
            }
        }

        public AnonymousClass2(FlowCollector flowCollector) {
            this.$this_unsafeFlow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r7
                com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1$2$1
                r0.<init>(r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r7)
                goto L56
            L27:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L2f:
                kotlin.ResultKt.throwOnFailure(r7)
                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                com.android.compose.animation.scene.ObservableTransitionState r6 = (com.android.compose.animation.scene.ObservableTransitionState) r6
                com.android.compose.animation.scene.SceneKey r7 = com.android.systemui.scene.shared.model.Scenes.Gone
                r2 = 0
                boolean r4 = com.android.compose.animation.scene.ObservableTransitionState.isTransitioning$default(r6, r2, r7, r3)
                if (r4 != 0) goto L48
                r4 = 2
                boolean r6 = com.android.compose.animation.scene.ObservableTransitionState.isIdle$default(r6, r7, r2, r4)
                if (r6 != 0) goto L48
                r6 = r3
                goto L49
            L48:
                r6 = 0
            L49:
                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)
                r0.label = r3
                java.lang.Object r5 = r5.emit(r6, r0)
                if (r5 != r1) goto L56
                return r1
            L56:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public OriginalUnseenKeyguardCoordinator$trackSeenNotifications$$inlined$map$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
