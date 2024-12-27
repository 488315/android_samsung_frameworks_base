package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class CustomTileDataInteractor implements QSTileDataInteractor {
    public final ReadonlySharedFlow bindingFlow;
    public final CustomTileInteractor customTileInteractor;
    public final CustomTileDefaultsRepository defaultsRepository;
    public final StateFlowImpl mutableUserFlow;
    public final CustomTilePackageUpdatesRepository packageUpdatesRepository;
    public final CustomTileServiceInteractor serviceInteractor;
    public final CoroutineScope tileScope;
    public final TileSpec.CustomTileSpec tileSpec;

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomTileDataInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlySharedFlow readonlySharedFlow = CustomTileDataInteractor.this.bindingFlow;
                this.label = 1;
                if (FlowKt.first(readonlySharedFlow, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public CustomTileDataInteractor(TileSpec.CustomTileSpec customTileSpec, CustomTileDefaultsRepository customTileDefaultsRepository, CustomTileServiceInteractor customTileServiceInteractor, CustomTileInteractor customTileInteractor, CustomTilePackageUpdatesRepository customTilePackageUpdatesRepository, UserRepository userRepository, CoroutineScope coroutineScope) {
        this.tileSpec = customTileSpec;
        this.defaultsRepository = customTileDefaultsRepository;
        this.serviceInteractor = customTileServiceInteractor;
        this.customTileInteractor = customTileInteractor;
        this.packageUpdatesRepository = customTilePackageUpdatesRepository;
        this.tileScope = coroutineScope;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(((UserRepositoryImpl) userRepository).getSelectedUserInfo().getUserHandle());
        this.mutableUserFlow = MutableStateFlow;
        this.bindingFlow = FlowKt.shareIn(FlowKt.transformLatest(MutableStateFlow, new CustomTileDataInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        ComponentName componentName = this.tileSpec.componentName;
        CustomTileDefaultsRepositoryImpl customTileDefaultsRepositoryImpl = (CustomTileDefaultsRepositoryImpl) this.defaultsRepository;
        customTileDefaultsRepositoryImpl.defaultsRequests.tryEmit(new CustomTileDefaultsRepositoryImpl.DefaultsRequest(userHandle, componentName, false));
        final CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1 customTileDefaultsRepositoryImpl$defaults$$inlined$map$1 = new CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1(new CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1(customTileDefaultsRepositoryImpl.defaults, userHandle));
        return new Flow() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1

            /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults r5 = (com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults) r5
                        boolean r5 = r5 instanceof com.android.systemui.qs.tiles.impl.custom.data.entity.CustomTileDefaults.Result
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$availability$lambda$4$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.qs.tiles.base.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlySharedFlow readonlySharedFlow) {
        BuildersKt.launch$default(this.tileScope, null, null, new CustomTileDataInteractor$tileData$1(this, userHandle, null), 3);
        return FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.bindingFlow, readonlySharedFlow, new CustomTileDataInteractor$tileData$2(null)), new CustomTileDataInteractor$tileData$$inlined$flatMapLatest$1(null, this, userHandle));
    }
}
