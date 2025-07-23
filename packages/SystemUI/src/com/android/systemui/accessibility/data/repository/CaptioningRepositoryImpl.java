package com.android.systemui.accessibility.data.repository;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.utils.UserScopedService;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CaptioningRepositoryImpl implements CaptioningRepository {
    public final CoroutineContext backgroundCoroutineContext;
    public final ReadonlyStateFlow captioningManager;
    public final ReadonlyStateFlow captioningModel;
    public final UserScopedService userScopedCaptioningManagerProvider;

    public CaptioningRepositoryImpl(UserScopedService userScopedService, UserRepository userRepository, CoroutineContext coroutineContext, CoroutineScope coroutineScope) {
        this.userScopedCaptioningManagerProvider = userScopedService;
        this.backgroundCoroutineContext = coroutineContext;
        final ReadonlyStateFlow readonlyStateFlow = ((UserRepositoryImpl) userRepository).selectedUser;
        Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.user.data.model.SelectedUserModel r5 = (com.android.systemui.user.data.model.SelectedUserModel) r5
                        com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl r6 = r4.this$0
                        com.android.systemui.user.utils.UserScopedService r6 = r6.userScopedCaptioningManagerProvider
                        android.content.pm.UserInfo r5 = r5.userInfo
                        android.os.UserHandle r5 = r5.getUserHandle()
                        com.android.systemui.user.utils.UserScopedServiceImpl r6 = (com.android.systemui.user.utils.UserScopedServiceImpl) r6
                        java.lang.Object r5 = r6.forUser(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.data.repository.CaptioningRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.captioningManager = stateIn;
        this.captioningModel = FlowKt.stateIn(FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateIn), new CaptioningRepositoryImpl$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
    }

    public final Object setIsSystemAudioCaptioningEnabled(boolean z, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundCoroutineContext, new CaptioningRepositoryImpl$setIsSystemAudioCaptioningEnabled$2(this, z, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
