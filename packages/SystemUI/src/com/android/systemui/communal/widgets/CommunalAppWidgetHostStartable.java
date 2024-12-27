package com.android.systemui.communal.widgets;

import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

public final class CommunalAppWidgetHostStartable implements CoreStartable {
    public final CommunalAppWidgetHost appWidgetHost;
    public final CoroutineScope bgScope;
    public final CommunalInteractor communalInteractor;
    public final CommunalWidgetHost communalWidgetHost;
    public final CoroutineDispatcher uiDispatcher;
    public final UserTracker userTracker;

    public CommunalAppWidgetHostStartable(CommunalAppWidgetHost communalAppWidgetHost, CommunalWidgetHost communalWidgetHost, CommunalInteractor communalInteractor, UserTracker userTracker, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.appWidgetHost = communalAppWidgetHost;
        this.communalWidgetHost = communalWidgetHost;
        this.communalInteractor = communalInteractor;
        this.userTracker = userTracker;
        this.bgScope = coroutineScope;
        this.uiDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        CommunalInteractor communalInteractor = this.communalInteractor;
        final Flow pairwise = FlowKt.pairwise(booleanFlowOperators.anyOf(communalInteractor.isCommunalAvailable, communalInteractor.editModeOpen), Boolean.FALSE);
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.sample(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1

            /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1$2$1 r0 = (com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1$2$1 r0 = new com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.util.kotlin.WithPrev r6 = (com.android.systemui.util.kotlin.WithPrev) r6
                        java.lang.Object r2 = r6.component1()
                        java.lang.Boolean r2 = (java.lang.Boolean) r2
                        boolean r2 = r2.booleanValue()
                        java.lang.Object r6 = r6.component2()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r2 == r6) goto L56
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$start$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new CommunalAppWidgetHostStartable$start$2(this, null)), communalInteractor.communalWidgets, CommunalAppWidgetHostStartable$start$4.INSTANCE), new CommunalAppWidgetHostStartable$start$5(this, null));
        CoroutineScope coroutineScope = this.bgScope;
        kotlinx.coroutines.flow.FlowKt.launchIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope);
        kotlinx.coroutines.flow.FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.appWidgetHost.appWidgetIdToRemove, new CommunalAppWidgetHostStartable$start$6(this, null)), coroutineScope);
    }
}
