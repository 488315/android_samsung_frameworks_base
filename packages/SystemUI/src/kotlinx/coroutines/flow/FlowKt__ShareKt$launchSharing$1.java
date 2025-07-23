package kotlinx.coroutines.flow;

import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class FlowKt__ShareKt$launchSharing$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $initialValue;
    final /* synthetic */ MutableSharedFlow $shared;
    final /* synthetic */ SharingStarted $started;
    final /* synthetic */ Flow $upstream;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.I$0 = ((Number) obj).intValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(this.I$0 > 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $initialValue;
        final /* synthetic */ MutableSharedFlow $shared;
        final /* synthetic */ Flow $upstream;
        /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2$WhenMappings */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[SharingCommand.values().length];
                try {
                    iArr[SharingCommand.START.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[SharingCommand.STOP.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[SharingCommand.STOP_AND_RESET_REPLAY_CACHE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Flow flow, MutableSharedFlow mutableSharedFlow, Object obj, Continuation continuation) {
            super(2, continuation);
            this.$upstream = flow;
            this.$shared = mutableSharedFlow;
            this.$initialValue = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$upstream, this.$shared, this.$initialValue, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((SharingCommand) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                int i2 = WhenMappings.$EnumSwitchMapping$0[((SharingCommand) this.L$0).ordinal()];
                if (i2 == 1) {
                    Flow flow = this.$upstream;
                    MutableSharedFlow mutableSharedFlow = this.$shared;
                    this.label = 1;
                    if (flow.collect(mutableSharedFlow, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else if (i2 != 2) {
                    if (i2 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    Object obj2 = this.$initialValue;
                    if (obj2 == SharedFlowKt.NO_VALUE) {
                        this.$shared.resetReplayCache();
                    } else {
                        this.$shared.tryEmit(obj2);
                    }
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowKt__ShareKt$launchSharing$1(SharingStarted sharingStarted, Flow flow, MutableSharedFlow mutableSharedFlow, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$started = sharingStarted;
        this.$upstream = flow;
        this.$shared = mutableSharedFlow;
        this.$initialValue = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FlowKt__ShareKt$launchSharing$1(this.$started, this.$upstream, this.$shared, this.$initialValue, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__ShareKt$launchSharing$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0063, code lost:
    
        if (r8.collect(r1, r7) == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x003b, code lost:
    
        if (r8.collect(r1, r7) == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0056, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.first(r8, r1, r7) == r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0085, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.collectLatest(r8, r1, r7) == r0) goto L28;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            if (r1 == 0) goto L23
            if (r1 == r5) goto L1f
            if (r1 == r4) goto L1b
            if (r1 == r3) goto L1f
            if (r1 != r2) goto L13
            goto L1f
        L13:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1b:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L59
        L1f:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L88
        L23:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.SharingStarted r8 = r7.$started
            kotlinx.coroutines.flow.SharingStarted$Companion r1 = kotlinx.coroutines.flow.SharingStarted.Companion
            r1.getClass()
            kotlinx.coroutines.flow.StartedEagerly r1 = kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
            if (r8 != r1) goto L3e
            kotlinx.coroutines.flow.Flow r8 = r7.$upstream
            kotlinx.coroutines.flow.MutableSharedFlow r1 = r7.$shared
            r7.label = r5
            java.lang.Object r7 = r8.collect(r1, r7)
            if (r7 != r0) goto L88
            goto L87
        L3e:
            kotlinx.coroutines.flow.SharingStarted r8 = r7.$started
            kotlinx.coroutines.flow.StartedLazily r1 = kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
            r5 = 0
            if (r8 != r1) goto L66
            kotlinx.coroutines.flow.MutableSharedFlow r8 = r7.$shared
            kotlinx.coroutines.flow.StateFlow r8 = r8.getSubscriptionCount()
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1 r1 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1
            r1.<init>(r5)
            r7.label = r4
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.first(r8, r1, r7)
            if (r8 != r0) goto L59
            goto L87
        L59:
            kotlinx.coroutines.flow.Flow r8 = r7.$upstream
            kotlinx.coroutines.flow.MutableSharedFlow r1 = r7.$shared
            r7.label = r3
            java.lang.Object r7 = r8.collect(r1, r7)
            if (r7 != r0) goto L88
            goto L87
        L66:
            kotlinx.coroutines.flow.MutableSharedFlow r1 = r7.$shared
            kotlinx.coroutines.flow.StateFlow r1 = r1.getSubscriptionCount()
            kotlinx.coroutines.flow.Flow r8 = r8.command(r1)
            kotlinx.coroutines.flow.Flow r8 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r8)
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2 r1 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2
            kotlinx.coroutines.flow.Flow r3 = r7.$upstream
            kotlinx.coroutines.flow.MutableSharedFlow r4 = r7.$shared
            java.lang.Object r6 = r7.$initialValue
            r1.<init>(r3, r4, r6, r5)
            r7.label = r2
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.collectLatest(r8, r1, r7)
            if (r7 != r0) goto L88
        L87:
            return r0
        L88:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
