package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public final class WifiInteractorImpl$special$$inlined$map$1 implements Flow {
    public final /* synthetic */ Flow $this_unsafeTransform$inlined;

    /* renamed from: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
    public final class AnonymousClass2 implements FlowCollector {
        public final /* synthetic */ FlowCollector $this_unsafeFlow;

        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
            /*
                r4 = this;
                boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                if (r0 == 0) goto L13
                r0 = r6
                com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1$2$1
                r0.<init>(r6)
            L18:
                java.lang.Object r6 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r6)
                goto L73
            L27:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L2f:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel r5 = (com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel) r5
                boolean r6 = r5 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Unavailable
                r2 = 0
                if (r6 == 0) goto L3a
                goto L68
            L3a:
                boolean r6 = r5 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Invalid
                if (r6 == 0) goto L3f
                goto L68
            L3f:
                boolean r6 = r5 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Inactive
                if (r6 == 0) goto L44
                goto L68
            L44:
                boolean r6 = r5 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.CarrierMerged
                if (r6 == 0) goto L49
                goto L68
            L49:
                boolean r6 = r5 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Active
                if (r6 == 0) goto L76
                com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel$Active r5 = (com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Active) r5
                boolean r6 = r5.isPasspointAccessPoint
                if (r6 != 0) goto L66
                boolean r6 = r5.isOnlineSignUpForPasspointAccessPoint
                if (r6 == 0) goto L58
                goto L66
            L58:
                java.lang.String r5 = r5.ssid
                if (r5 == 0) goto L68
                java.lang.String r6 = "<unknown ssid>"
                boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                if (r6 != 0) goto L68
                r2 = r5
                goto L68
            L66:
                java.lang.String r2 = r5.passpointProviderFriendlyName
            L68:
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                java.lang.Object r4 = r4.emit(r2, r0)
                if (r4 != r1) goto L73
                return r1
            L73:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            L76:
                kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                r4.<init>()
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public WifiInteractorImpl$special$$inlined$map$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    @Override // kotlinx.coroutines.flow.Flow
    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
        Object collect = this.$this_unsafeTransform$inlined.collect(new AnonymousClass2(flowCollector), continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
