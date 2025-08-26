package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
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
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
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

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$tileData$1, reason: invalid class name and case insensitive filesystem */
    final class C10161 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10161(UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomTileDataInteractor.this.new C10161(this.$user, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10161) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlowImpl stateFlowImpl = CustomTileDataInteractor.this.mutableUserFlow;
                UserHandle userHandle = this.$user;
                this.label = 1;
                stateFlowImpl.setValue(userHandle);
                if (Unit.INSTANCE == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$tileData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new AnonymousClass2((Continuation) obj3).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
    }

    public CustomTileDataInteractor(TileSpec.CustomTileSpec customTileSpec, CustomTileDefaultsRepository customTileDefaultsRepository, CustomTileServiceInteractor customTileServiceInteractor, CustomTileInteractor customTileInteractor, CustomTilePackageUpdatesRepository customTilePackageUpdatesRepository, UserRepository userRepository, CoroutineScope coroutineScope, QSTileLogger qSTileLogger) {
        this.tileSpec = customTileSpec;
        this.defaultsRepository = customTileDefaultsRepository;
        this.serviceInteractor = customTileServiceInteractor;
        this.customTileInteractor = customTileInteractor;
        this.packageUpdatesRepository = customTilePackageUpdatesRepository;
        this.tileScope = coroutineScope;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(((UserRepositoryImpl) userRepository).getSelectedUserInfo().getUserHandle());
        this.mutableUserFlow = stateFlowImplMutableStateFlow;
        this.bindingFlow = FlowKt.shareIn(FlowKt.transformLatest(stateFlowImplMutableStateFlow, new CustomTileDataInteractor$special$$inlined$flatMapLatest$1(null, this, qSTileLogger)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
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
                        Boolean boolValueOf = Boolean.valueOf(((CustomTileDefaults) obj) instanceof CustomTileDefaults.Result);
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
                Object objCollect = customTileDefaultsRepositoryImpl$defaults$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        CoroutineTracingKt.launchTraced$default(this.tileScope, null, null, new C10161(userHandle, null), 7);
        return FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.bindingFlow, readonlyStateFlow, new AnonymousClass2(null)), new CustomTileDataInteractor$tileData$$inlined$flatMapLatest$1(null, this, userHandle));
    }
}
