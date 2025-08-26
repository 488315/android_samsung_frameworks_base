package com.android.systemui.dreams.homecontrols.system.domain.interactor;

import android.content.ComponentName;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class HomeControlsComponentInteractor {
    public final ChannelFlowTransformLatest allAuthorizedPanels;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 allAvailableAndAuthorizedPanels;
    public final ControlsListingController controlsListingController;
    public final ReadonlyStateFlow panelComponent;
    public final SecSelectedComponentRepository secSelectedComponentRepository;
    public final HomeControlsComponentInteractor$special$$inlined$map$1 selectedPanel;

    public final class PanelComponent {
        public final ComponentName componentName;
        public final ComponentName panelActivity;

        public PanelComponent(ComponentName componentName, ComponentName componentName2) {
            this.componentName = componentName;
            this.panelActivity = componentName2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PanelComponent)) {
                return false;
            }
            PanelComponent panelComponent = (PanelComponent) obj;
            return Intrinsics.areEqual(this.componentName, panelComponent.componentName) && Intrinsics.areEqual(this.panelActivity, panelComponent.panelActivity);
        }

        public final int hashCode() {
            return this.panelActivity.hashCode() + (this.componentName.hashCode() * 31);
        }

        public final String toString() {
            return "PanelComponent(componentName=" + this.componentName + ", panelActivity=" + this.panelActivity + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1, kotlinx.coroutines.flow.Flow] */
    public HomeControlsComponentInteractor(SelectedComponentRepository selectedComponentRepository, ControlsComponent controlsComponent, AuthorizedPanelsRepository authorizedPanelsRepository, UserRepository userRepository, CoroutineScope coroutineScope, SecSelectedComponentRepository secSelectedComponentRepository) {
        Flow flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        this.secSelectedComponentRepository = secSelectedComponentRepository;
        ControlsListingController controlsListingController = (ControlsListingController) controlsComponent.controlsListingController.orElse(null);
        this.controlsListingController = controlsListingController;
        UserRepositoryImpl userRepositoryImpl = (UserRepositoryImpl) userRepository;
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$1(null, this));
        ?? r0 = new Flow() { // from class: com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        SelectedComponentRepository.SelectedComponent selectedComponent = (SelectedComponentRepository.SelectedComponent) obj;
                        if (selectedComponent == null || !selectedComponent.isPanel) {
                            selectedComponent = null;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(selectedComponent, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.selectedPanel = r0;
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest2 = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2(null, authorizedPanelsRepository));
        this.allAuthorizedPanels = channelFlowTransformLatestTransformLatest2;
        if (controlsListingController == null) {
            flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = EmptyFlow.INSTANCE;
        } else {
            flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new HomeControlsComponentInteractor$allAvailableServices$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new HomeControlsComponentInteractor$allAvailableServices$1(this, null)));
        }
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, channelFlowTransformLatestTransformLatest2, new HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1(null));
        this.allAvailableAndAuthorizedPanels = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, r0, new HomeControlsComponentInteractor$panelComponent$1(null));
        SharingStarted.Companion.getClass();
        this.panelComponent = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$12, coroutineScope, SharingStarted.Companion.Eagerly, null);
    }
}
