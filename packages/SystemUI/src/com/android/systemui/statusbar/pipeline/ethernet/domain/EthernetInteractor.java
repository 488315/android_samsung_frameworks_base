package com.android.systemui.statusbar.pipeline.ethernet.domain;

import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class EthernetInteractor {
    public final EthernetInteractor$special$$inlined$map$1 icon;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1] */
    public EthernetInteractor(ConnectivityRepository connectivityRepository) {
        final ReadonlyStateFlow readonlyStateFlow = ((ConnectivityRepositoryImpl) connectivityRepository).defaultConnections;
        this.icon = new Flow() { // from class: com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r7 instanceof com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L6d
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel r6 = (com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel) r6
                        com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Ethernet r7 = r6.ethernet
                        boolean r7 = r7.isDefault
                        if (r7 == 0) goto L61
                        int[] r7 = com.android.settingslib.AccessibilityContentDescriptions.ETHERNET_CONNECTION_VALUES
                        boolean r6 = r6.isValidated
                        if (r6 == 0) goto L50
                        com.android.systemui.common.shared.model.Icon$Resource r6 = new com.android.systemui.common.shared.model.Icon$Resource
                        com.android.systemui.common.shared.model.ContentDescription$Resource r2 = new com.android.systemui.common.shared.model.ContentDescription$Resource
                        r7 = r7[r3]
                        r2.<init>(r7)
                        r7 = 2131235614(0x7f08131e, float:1.8087427E38)
                        r6.<init>(r7, r2)
                        goto L62
                    L50:
                        com.android.systemui.common.shared.model.Icon$Resource r6 = new com.android.systemui.common.shared.model.Icon$Resource
                        com.android.systemui.common.shared.model.ContentDescription$Resource r2 = new com.android.systemui.common.shared.model.ContentDescription$Resource
                        r4 = 0
                        r7 = r7[r4]
                        r2.<init>(r7)
                        r7 = 2131235613(0x7f08131d, float:1.8087425E38)
                        r6.<init>(r7, r2)
                        goto L62
                    L61:
                        r6 = 0
                    L62:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L6d
                        return r1
                    L6d:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.ethernet.domain.EthernetInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
