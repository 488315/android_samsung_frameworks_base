package kotlinx.coroutines.flow;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.SubscriptionCountStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1", m277f = "Share.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber, IKnoxCustomManager.Stub.TRANSACTION_setAutoCallPickupState, IKnoxCustomManager.Stub.TRANSACTION_getAutoCallPickupState, IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionType}, m279m = "invokeSuspend")
/* loaded from: classes3.dex */
final class FlowKt__ShareKt$launchSharing$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $initialValue;
    final /* synthetic */ MutableSharedFlow $shared;
    final /* synthetic */ SharingStarted $started;
    final /* synthetic */ Flow $upstream;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1", m277f = "Share.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$1 */
    final class C48461 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;

        public C48461(Continuation<? super C48461> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C48461 c48461 = new C48461(continuation);
            c48461.I$0 = ((Number) obj).intValue();
            return c48461;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C48461) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2", m277f = "Share.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setForceAutoShutDownState}, m279m = "invokeSuspend")
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharing$1$2 */
    final class C48472 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $initialValue;
        final /* synthetic */ MutableSharedFlow $shared;
        final /* synthetic */ Flow $upstream;
        /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        public C48472(Flow flow, MutableSharedFlow mutableSharedFlow, Object obj, Continuation<? super C48472> continuation) {
            super(2, continuation);
            this.$upstream = flow;
            this.$shared = mutableSharedFlow;
            this.$initialValue = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C48472 c48472 = new C48472(this.$upstream, this.$shared, this.$initialValue, continuation);
            c48472.L$0 = obj;
            return c48472;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C48472) create((SharingCommand) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                } else if (i2 == 3) {
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
    public FlowKt__ShareKt$launchSharing$1(SharingStarted sharingStarted, Flow flow, MutableSharedFlow mutableSharedFlow, Object obj, Continuation<? super FlowKt__ShareKt$launchSharing$1> continuation) {
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0067 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        MutableSharedFlow mutableSharedFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    ResultKt.throwOnFailure(obj);
                    flow = this.$upstream;
                    mutableSharedFlow = this.$shared;
                    this.label = 3;
                    if (flow.collect(mutableSharedFlow, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                if (i != 3 && i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        SharingStarted sharingStarted = this.$started;
        SharingStarted.Companion.getClass();
        if (sharingStarted == SharingStarted.Companion.Eagerly) {
            Flow flow2 = this.$upstream;
            MutableSharedFlow mutableSharedFlow2 = this.$shared;
            this.label = 1;
            if (flow2.collect(mutableSharedFlow2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            SharingStarted sharingStarted2 = this.$started;
            if (sharingStarted2 == SharingStarted.Companion.Lazily) {
                SubscriptionCountStateFlow subscriptionCount = ((AbstractSharedFlow) this.$shared).getSubscriptionCount();
                C48461 c48461 = new C48461(null);
                this.label = 2;
                if (FlowKt.first(subscriptionCount, c48461, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                flow = this.$upstream;
                mutableSharedFlow = this.$shared;
                this.label = 3;
                if (flow.collect(mutableSharedFlow, this) == coroutineSingletons) {
                }
            } else {
                Flow distinctUntilChanged = FlowKt.distinctUntilChanged(sharingStarted2.command(((AbstractSharedFlow) this.$shared).getSubscriptionCount()));
                C48472 c48472 = new C48472(this.$upstream, this.$shared, this.$initialValue, null);
                this.label = 4;
                if (FlowKt.collectLatest(distinctUntilChanged, c48472, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        }
        return Unit.INSTANCE;
    }
}
