package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.CaptioningManager;
import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.utils.UserScopedService;
import com.android.systemui.user.utils.UserScopedServiceImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class CaptioningRepositoryImpl implements CaptioningRepository {
    public final CoroutineContext backgroundCoroutineContext;
    public final ReadonlyStateFlow captioningManager;
    public final ReadonlyStateFlow captioningModel;
    public final UserScopedService userScopedCaptioningManagerProvider;

    /* renamed from: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$setIsSystemAudioCaptioningEnabled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $isEnabled;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$isEnabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CaptioningRepositoryImpl.this.new AnonymousClass2(this.$isEnabled, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CaptioningManager captioningManager = (CaptioningManager) CaptioningRepositoryImpl.this.captioningManager.$$delegate_0.getValue();
            if (captioningManager != null) {
                captioningManager.setSystemAudioCaptioningEnabled(this.$isEnabled);
            }
            return Unit.INSTANCE;
        }
    }

    public CaptioningRepositoryImpl(UserScopedService userScopedService, UserRepository userRepository, CoroutineContext coroutineContext, CoroutineScope coroutineScope) {
        this.userScopedCaptioningManagerProvider = userScopedService;
        this.backgroundCoroutineContext = coroutineContext;
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) userRepository).selectedUser;
        Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CaptioningRepositoryImpl this$0;

                /* renamed from: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CaptioningRepositoryImpl captioningRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = captioningRepositoryImpl;
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
                        Object objForUser = ((UserScopedServiceImpl) this.this$0.userScopedCaptioningManagerProvider).forUser(((SelectedUserModel) obj).userInfo.getUserHandle());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(objForUser, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineContext);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.captioningManager = readonlyStateFlowStateIn;
        this.captioningModel = FlowKt.stateIn(FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowStateIn), new CaptioningRepositoryImpl$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }

    public final Object setIsSystemAudioCaptioningEnabled(boolean z, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundCoroutineContext, new AnonymousClass2(z, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
