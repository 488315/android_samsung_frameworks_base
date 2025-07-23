package com.android.systemui.volume.dialog.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSafetyWarningInteractor {
    public final VolumeDialogSafetyWarningInteractor$special$$inlined$map$1 isShowingSafetyWarning;
    public final VolumeDialogStateInteractor stateInteractor;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1] */
    public VolumeDialogSafetyWarningInteractor(VolumeDialogStateInteractor volumeDialogStateInteractor, final VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
        this.stateInteractor = volumeDialogStateInteractor;
        final ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        this.isShowingSafetyWarning = new Flow() { // from class: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogVisibilityInteractor $visibilityInteractor$inlined;

                /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$visibilityInteractor$inlined = volumeDialogVisibilityInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:19:0x0075, code lost:
                
                    if (r2.emit(r5, r0) == r1) goto L30;
                 */
                /* JADX WARN: Removed duplicated region for block: B:21:0x003a  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 2
                        r4 = 1
                        if (r2 == 0) goto L3a
                        if (r2 == r4) goto L32
                        if (r2 != r3) goto L2a
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L78
                    L2a:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L32:
                        java.lang.Object r5 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5f
                    L3a:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r6
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel r6 = r6.isShowingSafetyWarning
                        boolean r7 = r6 instanceof com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel.Visible
                        kotlinx.coroutines.flow.FlowCollector r2 = r5.$this_unsafeFlow
                        if (r7 == 0) goto L63
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel$Visible r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel.Visible) r6
                        int r6 = r6.flags
                        r6 = r6 & 1025(0x401, float:1.436E-42)
                        if (r6 != 0) goto L68
                        com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor r5 = r5.$visibilityInteractor$inlined
                        kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.dialogVisibility
                        r0.L$0 = r2
                        r0.label = r4
                        java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r5, r0)
                        if (r7 != r1) goto L5e
                        goto L77
                    L5e:
                        r5 = r2
                    L5f:
                        boolean r4 = r7 instanceof com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel.Visible
                        r2 = r5
                        goto L68
                    L63:
                        boolean r5 = r6 instanceof com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel.Invisible
                        if (r5 == 0) goto L7b
                        r4 = 0
                    L68:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r4)
                        r6 = 0
                        r0.L$0 = r6
                        r0.label = r3
                        java.lang.Object r5 = r2.emit(r5, r0)
                        if (r5 != r1) goto L78
                    L77:
                        return r1
                    L78:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    L7b:
                        kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
                        r5.<init>()
                        throw r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, volumeDialogVisibilityInteractor), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
