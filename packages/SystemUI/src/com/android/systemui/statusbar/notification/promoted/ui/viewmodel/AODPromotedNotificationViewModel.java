package com.android.systemui.statusbar.notification.promoted.ui.viewmodel;

import android.util.IndentingPrintWriter;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.util.kotlin.ActivatableFlowDumper;
import com.android.systemui.util.kotlin.ActivatableFlowDumperImpl;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class AODPromotedNotificationViewModel extends ExclusiveActivatable implements ActivatableFlowDumper {
    public static final long RECENTLY_ALERTED_THRESHOLD;
    public final /* synthetic */ ActivatableFlowDumperImpl $$delegate_0;
    public final DistinctFlowImpl contentFlow;
    public final Hydrator hydrator;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
    }

    /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AODPromotedNotificationViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ AODPromotedNotificationViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(AODPromotedNotificationViewModel aODPromotedNotificationViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = aODPromotedNotificationViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    AODPromotedNotificationViewModel aODPromotedNotificationViewModel = this.this$0;
                    this.label = 1;
                    if (aODPromotedNotificationViewModel.$$delegate_0.activateFlowDumper(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C04992 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ AODPromotedNotificationViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04992(AODPromotedNotificationViewModel aODPromotedNotificationViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = aODPromotedNotificationViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04992(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04992) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = AODPromotedNotificationViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(AODPromotedNotificationViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new C04992(AODPromotedNotificationViewModel.this, null), 3);
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        RECENTLY_ALERTED_THRESHOLD = DurationKt.toDuration(30, DurationUnit.SECONDS);
    }

    public AODPromotedNotificationViewModel(AODPromotedNotificationInteractor aODPromotedNotificationInteractor, SystemClock systemClock, DumpManager dumpManager) {
        ActivatableFlowDumperImpl activatableFlowDumperImpl = new ActivatableFlowDumperImpl(dumpManager, "AODPromotedNotificationViewModel");
        this.$$delegate_0 = activatableFlowDumperImpl;
        Hydrator hydrator = new Hydrator("AODPromotedNotificationViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        DistinctFlowImpl distinctFlowImpl = aODPromotedNotificationInteractor.content;
        this.contentFlow = distinctFlowImpl;
        hydrator.hydratedStateOf("content", null, distinctFlowImpl);
        final DistinctFlowImpl distinctFlowImpl2 = aODPromotedNotificationInteractor.content;
        hydrator.hydratedStateOf("audiblyAlertedIconVisible", Boolean.FALSE, activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.transformLatest(activatableFlowDumperImpl.dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                    Duration durationM3454boximpl;
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
                        PromotedNotificationContentModel promotedNotificationContentModel = (PromotedNotificationContentModel) obj;
                        if (promotedNotificationContentModel != null) {
                            Duration.Companion companion = Duration.Companion;
                            durationM3454boximpl = Duration.m3454boximpl(Duration.m3461plusLRDsOJo(DurationKt.toDuration(promotedNotificationContentModel.lastAudiblyAlertedMs, DurationUnit.MILLISECONDS), AODPromotedNotificationViewModel.RECENTLY_ALERTED_THRESHOLD));
                        } else {
                            durationM3454boximpl = null;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(durationM3454boximpl, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = distinctFlowImpl2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), "audiblyAlertedIconVisibleUntil"), new AODPromotedNotificationViewModel$audiblyAlertedIconVisibleFlow$1(systemClock, null)), -1, 2)), "audiblyAlertedIconVisible"));
    }

    @Override // com.android.systemui.util.kotlin.ActivatableFlowDumper
    public final Object activateFlowDumper(Continuation continuation) {
        return this.$$delegate_0.activateFlowDumper(continuation);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.$$delegate_0.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final void dumpFlows(IndentingPrintWriter indentingPrintWriter) {
        this.$$delegate_0.dumpFlows(indentingPrintWriter);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final SharedFlow dumpReplayCache(SharedFlow sharedFlow, String str) {
        return this.$$delegate_0.dumpReplayCache(sharedFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final StateFlow dumpValue(StateFlow stateFlow, String str) {
        return this.$$delegate_0.dumpValue(stateFlow, str);
    }

    @Override // com.android.systemui.util.kotlin.FlowDumper
    public final Flow dumpWhileCollecting(Flow flow, String str) {
        return this.$$delegate_0.dumpWhileCollecting(flow, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
