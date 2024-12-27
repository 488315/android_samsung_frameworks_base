package com.android.systemui.dreams.homecontrols.domain.interactor;

import android.app.DreamManager;
import android.content.ComponentName;
import android.os.PowerManager;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HomeControlsComponentInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long MAX_UPDATE_CORRELATION_DELAY;
    public final ChannelFlowTransformLatest allAuthorizedPanels;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 allAvailableAndAuthorizedPanels;
    public final ControlsListingController controlsListingController;
    public final DreamManager dreamManager;
    public final PackageChangeInteractor packageChangeInteractor;
    public final ReadonlyStateFlow panelComponent;
    public final PowerManager powerManager;
    public final SecSelectedComponentRepository secSelectedComponentRepository;
    public final HomeControlsComponentInteractor$special$$inlined$map$1 selectedPanel;
    public final SystemClock systemClock;
    public final SharedFlowImpl taskFragmentFinished;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        MAX_UPDATE_CORRELATION_DELAY = DurationKt.toDuration(500, DurationUnit.MILLISECONDS);
    }

    public HomeControlsComponentInteractor(SelectedComponentRepository selectedComponentRepository, SecSelectedComponentRepository secSelectedComponentRepository, ControlsComponent controlsComponent, AuthorizedPanelsRepository authorizedPanelsRepository, UserRepository userRepository, PackageChangeInteractor packageChangeInteractor, SystemClock systemClock, PowerManager powerManager, DreamManager dreamManager, CoroutineScope coroutineScope) {
        Flow flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        this.secSelectedComponentRepository = secSelectedComponentRepository;
        this.packageChangeInteractor = packageChangeInteractor;
        this.systemClock = systemClock;
        this.powerManager = powerManager;
        this.dreamManager = dreamManager;
        ControlsListingController controlsListingController = (ControlsListingController) controlsComponent.controlsListingController.orElse(null);
        this.controlsListingController = controlsListingController;
        HomeControlsComponentInteractor$special$$inlined$flatMapLatest$1 homeControlsComponentInteractor$special$$inlined$flatMapLatest$1 = new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$1(null, this);
        UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(userRepositoryImpl$special$$inlined$map$2, homeControlsComponentInteractor$special$$inlined$flatMapLatest$1);
        Flow flow = new Flow() { // from class: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1$2$1
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
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(userRepositoryImpl$special$$inlined$map$2, new HomeControlsComponentInteractor$special$$inlined$flatMapLatest$2(null, authorizedPanelsRepository));
        if (controlsListingController == null) {
            flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = EmptyFlow.INSTANCE;
        } else {
            flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new HomeControlsComponentInteractor$allAvailableServices$2(this, null), FlowConflatedKt.conflatedCallbackFlow(new HomeControlsComponentInteractor$allAvailableServices$1(this, null)));
        }
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, transformLatest2, new HomeControlsComponentInteractor$allAvailableAndAuthorizedPanels$1(null)), flow, new HomeControlsComponentInteractor$panelComponent$1(null));
        SharingStarted.Companion.getClass();
        this.panelComponent = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, SharingStarted.Companion.Eagerly, null);
        this.taskFragmentFinished = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
    }

    public final Object monitorUpdatesAndRestart(Continuation continuation) {
        SharedFlowImpl sharedFlowImpl = this.taskFragmentFinished;
        sharedFlowImpl.resetReplayCache();
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(this.panelComponent, new HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$flatMapLatest$1(null, this));
        final Flow sample = com.android.systemui.util.kotlin.FlowKt.sample(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(new Flow() { // from class: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1$2$1 r0 = (com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1$2$1 r0 = new com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.common.shared.model.PackageChangeModel r6 = (com.android.systemui.common.shared.model.PackageChangeModel) r6
                        boolean r2 = r6 instanceof com.android.systemui.common.shared.model.PackageChangeModel.UpdateStarted
                        if (r2 != 0) goto L3d
                        boolean r6 = r6 instanceof com.android.systemui.common.shared.model.PackageChangeModel.UpdateFinished
                        if (r6 == 0) goto L48
                    L3d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, HomeControlsComponentInteractor$monitorUpdatesAndRestart$4.INSTANCE)), sharedFlowImpl, HomeControlsComponentInteractor$monitorUpdatesAndRestart$6.INSTANCE);
        Object collect = new Flow() { // from class: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
                        com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2$2$1 r0 = (com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2$2$1 r0 = new com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2$2$1
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L61
                    L27:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r10)
                        r10 = r9
                        kotlin.Pair r10 = (kotlin.Pair) r10
                        java.lang.Object r2 = r10.component1()
                        com.android.systemui.common.shared.model.PackageChangeModel$UpdateStarted r2 = (com.android.systemui.common.shared.model.PackageChangeModel.UpdateStarted) r2
                        java.lang.Object r10 = r10.component2()
                        java.lang.Number r10 = (java.lang.Number) r10
                        long r4 = r10.longValue()
                        long r6 = r2.timeMillis
                        long r6 = r6 - r4
                        long r4 = java.lang.Math.abs(r6)
                        long r6 = com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor.MAX_UPDATE_CORRELATION_DELAY
                        long r6 = kotlin.time.Duration.m2538getInWholeMillisecondsimpl(r6)
                        int r10 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                        if (r10 > 0) goto L61
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        java.lang.Object r8 = r8.emit(r9, r0)
                        if (r8 != r1) goto L61
                        return r1
                    L61:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
            }
        }.collect(new FlowCollector() { // from class: com.android.systemui.dreams.homecontrols.domain.interactor.HomeControlsComponentInteractor$monitorUpdatesAndRestart$8
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                HomeControlsComponentInteractor.this.dreamManager.startDream();
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
