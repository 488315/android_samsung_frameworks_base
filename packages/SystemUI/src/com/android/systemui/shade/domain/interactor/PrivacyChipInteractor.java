package com.android.systemui.shade.domain.interactor;

import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyDialogControllerV2;
import com.android.systemui.shade.data.repository.PrivacyChipRepository;
import com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

public final class PrivacyChipInteractor {
    public final DeviceProvisionedController deviceProvisionedController;
    public final ReadonlyStateFlow isChipEnabled;
    public final ReadonlyStateFlow isChipVisible;
    public final ReadonlyStateFlow isLocationIndicationEnabled;
    public final ReadonlyStateFlow isMicCameraIndicationEnabled;
    public final PrivacyDialogController privacyDialogController;
    public final PrivacyDialogControllerV2 privacyDialogControllerV2;
    public final ReadonlyStateFlow privacyItems;
    public final PrivacyChipRepository repository;

    public PrivacyChipInteractor(CoroutineScope coroutineScope, PrivacyChipRepository privacyChipRepository, PrivacyDialogController privacyDialogController, PrivacyDialogControllerV2 privacyDialogControllerV2, DeviceProvisionedController deviceProvisionedController) {
        PrivacyChipRepositoryImpl privacyChipRepositoryImpl = (PrivacyChipRepositoryImpl) privacyChipRepository;
        final ReadonlyStateFlow readonlyStateFlow = privacyChipRepositoryImpl.privacyItems;
        this.privacyItems = readonlyStateFlow;
        ReadonlyStateFlow readonlyStateFlow2 = privacyChipRepositoryImpl.isMicCameraIndicationEnabled;
        this.isMicCameraIndicationEnabled = readonlyStateFlow2;
        ReadonlyStateFlow readonlyStateFlow3 = privacyChipRepositoryImpl.isLocationIndicationEnabled;
        this.isLocationIndicationEnabled = readonlyStateFlow3;
        Flow flow = new Flow() { // from class: com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.util.Collection r5 = (java.util.Collection) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.PrivacyChipInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isChipVisible = FlowKt.stateIn(flow, coroutineScope, WhileSubscribed$default, bool);
        this.isChipEnabled = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, readonlyStateFlow3, new PrivacyChipInteractor$isChipEnabled$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
    }
}
