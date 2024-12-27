package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1 implements Flow {
    public final /* synthetic */ int $defaultBurnInOffset$inlined;
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;
    public final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ int $defaultBurnInOffset$inlined;
        public final /* synthetic */ FlowCollector $this_unsafeFlow;
        public final /* synthetic */ KeyguardBottomAreaViewModel this$0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1, reason: invalid class name */
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

        public AnonymousClass2(FlowCollector flowCollector, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, int i) {
            this.$this_unsafeFlow = flowCollector;
            this.this$0 = keyguardBottomAreaViewModel;
            this.$defaultBurnInOffset$inlined = i;
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
                boolean r0 = r7 instanceof com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r7
                com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1$2$1
                r0.<init>(r7)
            L18:
                java.lang.Object r7 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r7)
                goto L5b
            L27:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L2f:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Number r6 = (java.lang.Number) r6
                float r6 = r6.floatValue()
                com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel r7 = r5.this$0
                com.android.systemui.doze.util.BurnInHelperWrapper r7 = r7.burnInHelperWrapper
                int r2 = r5.$defaultBurnInOffset$inlined
                int r4 = r2 * 2
                r7.getClass()
                r7 = 0
                int r7 = com.android.systemui.doze.util.BurnInHelperKt.getBurnInOffset(r4, r7)
                int r7 = r7 - r2
                float r7 = (float) r7
                float r6 = r6 * r7
                java.lang.Float r7 = new java.lang.Float
                r7.<init>(r6)
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                java.lang.Object r5 = r5.emit(r7, r0)
                if (r5 != r1) goto L5b
                return r1
            L5b:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public KeyguardBottomAreaViewModel$indicationAreaTranslationY$$inlined$map$1(Flow flow, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, int i) {
        this.$this_unsafeTransform$inlined = flow;
        this.this$0 = keyguardBottomAreaViewModel;
        this.$defaultBurnInOffset$inlined = i;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector, this.this$0, this.$defaultBurnInOffset$inlined), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
