package com.android.systemui.volume.dialog.domain.interactor;

import com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class VolumeDialogSafetyWarningInteractor {
    public final VolumeDialogSafetyWarningInteractor$special$$inlined$map$1 isShowingSafetyWarning;
    public final VolumeDialogStateInteractor stateInteractor;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1] */
    public VolumeDialogSafetyWarningInteractor(VolumeDialogStateInteractor volumeDialogStateInteractor, final VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
        this.stateInteractor = volumeDialogStateInteractor;
        final ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        this.isShowingSafetyWarning = new Flow() { // from class: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogSafetyWarningInteractor$special$$inlined$map$1

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

                /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
                
                    if (r2.emit(r5, r0) != r1) goto L31;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    FlowCollector flowCollector2;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objFirst = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    boolean z = true;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objFirst);
                        VolumeDialogSafetyWarningModel volumeDialogSafetyWarningModel = ((VolumeDialogStateModel) obj).isShowingSafetyWarning;
                        boolean z2 = volumeDialogSafetyWarningModel instanceof VolumeDialogSafetyWarningModel.Visible;
                        flowCollector = this.$this_unsafeFlow;
                        if (z2) {
                            if ((((VolumeDialogSafetyWarningModel.Visible) volumeDialogSafetyWarningModel).flags & 1025) == 0) {
                                ReadonlyStateFlow readonlyStateFlow = this.$visibilityInteractor$inlined.dialogVisibility;
                                anonymousClass1.L$0 = flowCollector;
                                anonymousClass1.label = 1;
                                objFirst = FlowKt.first(readonlyStateFlow, anonymousClass1);
                                if (objFirst != coroutineSingletons) {
                                    flowCollector2 = flowCollector;
                                }
                            }
                            return coroutineSingletons;
                        }
                        if (!(volumeDialogSafetyWarningModel instanceof VolumeDialogSafetyWarningModel.Invisible)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        z = false;
                        Boolean boolValueOf = Boolean.valueOf(z);
                        anonymousClass1.L$0 = null;
                        anonymousClass1.label = 2;
                    } else {
                        if (i2 != 1) {
                            if (i2 != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(objFirst);
                            return Unit.INSTANCE;
                        }
                        flowCollector2 = (FlowCollector) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(objFirst);
                    }
                    z = objFirst instanceof VolumeDialogVisibilityModel.Visible;
                    flowCollector = flowCollector2;
                    Boolean boolValueOf2 = Boolean.valueOf(z);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, volumeDialogVisibilityInteractor), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
