package com.android.systemui.volume.dialog.settings.domain;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class VolumeDialogSettingsButtonInteractor {
    public final DeviceProvisionedController deviceProvisionedController;
    public final ReadonlyStateFlow isVisible;
    public final VolumeDialogVisibilityInteractor visibilityInteractor;
    public final VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor;

    public VolumeDialogSettingsButtonInteractor(CoroutineScope coroutineScope, DeviceProvisionedController deviceProvisionedController, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
        this.deviceProvisionedController = deviceProvisionedController;
        this.volumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        this.visibilityInteractor = volumeDialogVisibilityInteractor;
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(volumeDialogVisibilityInteractor.dialogVisibility, Reflection.getOrCreateKotlinClass(VolumeDialogVisibilityModel.Visible.class));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogSettingsButtonInteractor this$0;

                /* renamed from: com.android.systemui.volume.dialog.settings.domain.VolumeDialogSettingsButtonInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogSettingsButtonInteractor volumeDialogSettingsButtonInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogSettingsButtonInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((DeviceProvisionedControllerImpl) this.this$0.deviceProvisionedController).isCurrentUserSetup() && ((VolumeDialogVisibilityModel.Visible) obj).lockTaskModeState == 0);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterIsInstance$$inlined$filter$2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.isVisible = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }
}
