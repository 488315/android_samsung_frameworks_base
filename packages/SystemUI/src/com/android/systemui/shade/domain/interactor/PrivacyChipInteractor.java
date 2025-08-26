package com.android.systemui.shade.domain.interactor;

import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyDialogControllerV2;
import com.android.systemui.shade.data.repository.PrivacyChipRepository;
import com.android.systemui.shade.data.repository.PrivacyChipRepositoryImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.util.List;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
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
    public final ShadeDialogContextInteractor shadeDialogContextInteractor;

    public PrivacyChipInteractor(CoroutineScope coroutineScope, PrivacyChipRepository privacyChipRepository, PrivacyDialogController privacyDialogController, PrivacyDialogControllerV2 privacyDialogControllerV2, DeviceProvisionedController deviceProvisionedController, ShadeDialogContextInteractor shadeDialogContextInteractor) {
        this.repository = privacyChipRepository;
        this.privacyDialogController = privacyDialogController;
        this.privacyDialogControllerV2 = privacyDialogControllerV2;
        this.deviceProvisionedController = deviceProvisionedController;
        this.shadeDialogContextInteractor = shadeDialogContextInteractor;
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
                        Boolean boolValueOf = Boolean.valueOf(!((List) obj).isEmpty());
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        this.isChipVisible = FlowKt.stateIn(flow, coroutineScope, startedWhileSubscribedWhileSubscribed$default, bool);
        this.isChipEnabled = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, readonlyStateFlow3, new PrivacyChipInteractor$isChipEnabled$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
    }
}
