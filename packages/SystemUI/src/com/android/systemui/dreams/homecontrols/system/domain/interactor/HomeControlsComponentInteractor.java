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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsComponentInteractor {
    public final ChannelFlowTransformLatest allAuthorizedPanels;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 allAvailableAndAuthorizedPanels;
    public final ControlsListingController controlsListingController;
    public final ReadonlyStateFlow panelComponent;
    public final SecSelectedComponentRepository secSelectedComponentRepository;
    public final HomeControlsComponentInteractor$special$$inlined$map$1 selectedPanel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$1(null, this));
        ?? r0 = new Flow() { // from class: com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.controls.panels.SelectedComponentRepository$SelectedComponent r5 = (com.android.systemui.controls.panels.SelectedComponentRepository.SelectedComponent) r5
                        if (r5 == 0) goto L3b
                        boolean r6 = r5.isPanel
                        if (r6 != r3) goto L3b
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dreams.homecontrols.system.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.selectedPanel = r0;
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(userRepositoryImpl.selectedUserInfo, new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2(null, authorizedPanelsRepository));
        this.allAuthorizedPanels = transformLatest2;
        if (controlsListingController == null) {
            flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = EmptyFlow.INSTANCE;
        } else {
            flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new HomeControlsComponentInteractor$allAvailableServices$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new HomeControlsComponentInteractor$allAvailableServices$1(this, null)));
        }
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, transformLatest2, new HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1(null));
        this.allAvailableAndAuthorizedPanels = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$12 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, r0, new HomeControlsComponentInteractor$panelComponent$1(null));
        SharingStarted.Companion.getClass();
        this.panelComponent = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$12, coroutineScope, SharingStarted.Companion.Eagerly, null);
    }
}
