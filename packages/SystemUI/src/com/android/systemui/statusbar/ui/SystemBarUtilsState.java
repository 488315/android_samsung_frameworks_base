package com.android.systemui.statusbar.ui;

import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class SystemBarUtilsState {
    public final Flow statusBarHeight;

    public SystemBarUtilsState(CoroutineContext coroutineContext, CoroutineContext coroutineContext2, ConfigurationController configurationController, final SystemBarUtilsProxy systemBarUtilsProxy) {
        final Flow conflate = FlowKt.conflate(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SystemBarUtilsState$statusBarHeight$1(null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController)), coroutineContext2));
        FlowKt.conflate(FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ SystemBarUtilsProxy $proxy$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SystemBarUtilsProxy systemBarUtilsProxy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$proxy$inlined = systemBarUtilsProxy;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                    /*
                        r3 = this;
                        boolean r4 = r5 instanceof com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r4 == 0) goto L13
                        r4 = r5
                        com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1$2$1 r4 = (com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r4
                        int r0 = r4.label
                        r1 = -2147483648(0xffffffff80000000, float:-0.0)
                        r2 = r0 & r1
                        if (r2 == 0) goto L13
                        int r0 = r0 - r1
                        r4.label = r0
                        goto L18
                    L13:
                        com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1$2$1 r4 = new com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1$2$1
                        r4.<init>(r5)
                    L18:
                        java.lang.Object r5 = r4.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r4.label
                        r2 = 1
                        if (r1 == 0) goto L2f
                        if (r1 != r2) goto L27
                        kotlin.ResultKt.throwOnFailure(r5)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                        java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                        r3.<init>(r4)
                        throw r3
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r5)
                        com.android.systemui.statusbar.ui.SystemBarUtilsProxy r5 = r3.$proxy$inlined
                        com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl r5 = (com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl) r5
                        android.content.Context r5 = r5.context
                        int r5 = com.android.internal.policy.SystemBarUtils.getStatusBarHeight(r5)
                        java.lang.Integer r1 = new java.lang.Integer
                        r1.<init>(r5)
                        r4.label = r2
                        kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                        java.lang.Object r3 = r3.emit(r1, r4)
                        if (r3 != r0) goto L4c
                        return r0
                    L4c:
                        kotlin.Unit r3 = kotlin.Unit.INSTANCE
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, systemBarUtilsProxy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineContext));
        final Flow conflate2 = FlowKt.conflate(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SystemBarUtilsState$statusBarHeaderHeightKeyguard$1(null), ConfigurationControllerExtKt.getOnConfigChanged(configurationController)), coroutineContext2));
        FlowKt.conflate(FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ SystemBarUtilsProxy $proxy$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SystemBarUtilsProxy systemBarUtilsProxy) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$proxy$inlined = systemBarUtilsProxy;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                    /*
                        r3 = this;
                        boolean r4 = r5 instanceof com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r4 == 0) goto L13
                        r4 = r5
                        com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2$2$1 r4 = (com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r4
                        int r0 = r4.label
                        r1 = -2147483648(0xffffffff80000000, float:-0.0)
                        r2 = r0 & r1
                        if (r2 == 0) goto L13
                        int r0 = r0 - r1
                        r4.label = r0
                        goto L18
                    L13:
                        com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2$2$1 r4 = new com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2$2$1
                        r4.<init>(r5)
                    L18:
                        java.lang.Object r5 = r4.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r4.label
                        r2 = 1
                        if (r1 == 0) goto L2f
                        if (r1 != r2) goto L27
                        kotlin.ResultKt.throwOnFailure(r5)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                        java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                        r3.<init>(r4)
                        throw r3
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r5)
                        com.android.systemui.statusbar.ui.SystemBarUtilsProxy r5 = r3.$proxy$inlined
                        com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl r5 = (com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl) r5
                        int r5 = r5.getStatusBarHeaderHeightKeyguard()
                        java.lang.Integer r1 = new java.lang.Integer
                        r1.<init>(r5)
                        r4.label = r2
                        kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                        java.lang.Object r3 = r3.emit(r1, r4)
                        if (r3 != r0) goto L4a
                        return r0
                    L4a:
                        kotlin.Unit r3 = kotlin.Unit.INSTANCE
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.ui.SystemBarUtilsState$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, systemBarUtilsProxy), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineContext));
    }
}
