package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.Events;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DemoModeMobileConnectionDataSourceKairosImpl implements KairosBuilder, DemoModeMobileConnectionDataSourceKairos {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0;
    public final DemoModeMobileConnectionDataSourceKairosImpl$special$$inlined$map$1 _mobileCommands;
    public final Events mobileEvents;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Module {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSourceKairosImpl$special$$inlined$map$1] */
    public DemoModeMobileConnectionDataSourceKairosImpl(DemoModeController demoModeController) {
        KairosBuilderImpl kairosBuilderImpl = new KairosBuilderImpl();
        this.$$delegate_0 = kairosBuilderImpl;
        final Flow demoFlowForCommand = demoModeController.demoFlowForCommand();
        this._mobileCommands = new Flow() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSourceKairosImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSourceKairosImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DemoModeMobileConnectionDataSourceKairosImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSourceKairosImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DemoModeMobileConnectionDataSourceKairosImpl demoModeMobileConnectionDataSourceKairosImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = demoModeMobileConnectionDataSourceKairosImpl;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$MobileDisabled] */
                /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel$Mobile] */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r22, kotlin.coroutines.Continuation r23) {
                    /*
                        Method dump skipped, instructions count: 590
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSourceKairosImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.mobileEvents = kairosBuilderImpl.buildEvents(new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSourceKairosImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                DemoModeMobileConnectionDataSourceKairosImpl demoModeMobileConnectionDataSourceKairosImpl = DemoModeMobileConnectionDataSourceKairosImpl.this;
                BuildScopeImpl buildScopeImpl = (BuildScopeImpl) ((BuildScope) obj);
                buildScopeImpl.getClass();
                return BuildScope.DefaultImpls.toEvents(buildScopeImpl, demoModeMobileConnectionDataSourceKairosImpl._mobileCommands);
            }
        });
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
