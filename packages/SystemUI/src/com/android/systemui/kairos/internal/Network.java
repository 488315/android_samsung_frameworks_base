package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.MuxNode;
import com.android.systemui.kairos.internal.Output;
import com.android.systemui.kairos.internal.store.MutableMapK;
import com.android.systemui.kairos.internal.util.LogIndent;
import com.android.systemui.kairos.util.Maybe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlin.time.LongSaturatedMathKt;
import kotlin.time.MonotonicTimeSource;
import kotlin.time.TimeSource$Monotonic;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.YieldKt;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes2.dex */
public final class Network implements NetworkScope {
    public final CoroutineScope coroutineScope;
    public volatile long epoch;
    public final Object networkId = Long.valueOf(NetworkKt.nextNetworkId.getAndIncrement());
    public final SchedulerImpl compactor = new SchedulerImpl(new Network$$ExternalSyntheticLambda0(0));
    public final SchedulerImpl scheduler = new SchedulerImpl(new Network$$ExternalSyntheticLambda0(1));
    public final TransactionStore transactionStore = new TransactionStore();
    public final ArrayDeque stateWrites = new ArrayDeque();
    public final HashMap outputsByDispatcher = new HashMap();
    public final ArrayDeque muxMovers = new ArrayDeque();
    public final ArrayDeque deactivations = new ArrayDeque();
    public final ArrayDeque outputDeactivations = new ArrayDeque();
    public final MutexImpl transactionMutex = MutexKt.Mutex$default();
    public final BufferedChannel inputScheduleChan = ChannelKt.Channel$default(0, null, null, 7);

    /* renamed from: com.android.systemui.kairos.internal.Network$doTransaction$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
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
            return Network.this.doTransaction(this);
        }
    }

    /* renamed from: com.android.systemui.kairos.internal.Network$evalOutputs$1, reason: invalid class name and case insensitive filesystem */
    final class C08831 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C08831(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return Network.this.evalOutputs(null, this);
        }
    }

    /* renamed from: com.android.systemui.kairos.internal.Network$evalOutputs$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ EvalScope $evalScope;
        final /* synthetic */ Ref$BooleanRef $launchedAny;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.kairos.internal.Network$evalOutputs$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ EvalScope $evalScope;
            final /* synthetic */ ArrayDeque $outputs;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.kairos.internal.Network$evalOutputs$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02071 extends SuspendLambda implements Function2 {
                final /* synthetic */ EvalScope $evalScope;
                final /* synthetic */ Output $output;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02071(Output output, EvalScope evalScope, Continuation continuation) {
                    super(2, continuation);
                    this.$output = output;
                    this.$evalScope = evalScope;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02071(this.$output, this.$evalScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02071) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    Output output = this.$output;
                    EvalScope evalScope = this.$evalScope;
                    Object obj2 = output.result;
                    Output.NoResult noResult = Output.NoResult.INSTANCE;
                    if (obj2 == noResult) {
                        throw new IllegalStateException("output visited with null upstream result");
                    }
                    output.result = noResult;
                    output.onEmit.invoke(evalScope, obj2);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(ArrayDeque arrayDeque, EvalScope evalScope, Continuation continuation) {
                super(2, continuation);
                this.$outputs = arrayDeque;
                this.$evalScope = evalScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$outputs, this.$evalScope, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                while (!this.$outputs.isEmpty()) {
                    BuildersKt.launch$default(coroutineScope, null, null, new C02071((Output) this.$outputs.removeFirst(), this.$evalScope, null), 3);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Ref$BooleanRef ref$BooleanRef, EvalScope evalScope, Continuation continuation) {
            super(2, continuation);
            this.$launchedAny = ref$BooleanRef;
            this.$evalScope = evalScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = Network.this.new AnonymousClass2(this.$launchedAny, this.$evalScope, continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            for (Map.Entry entry : Network.this.outputsByDispatcher.entrySet()) {
                ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) entry.getKey();
                ArrayDeque arrayDeque = (ArrayDeque) entry.getValue();
                if (!arrayDeque.isEmpty()) {
                    this.$launchedAny.element = true;
                    BuildersKt.launch$default(coroutineScope, continuationInterceptor, null, new AnonymousClass1(arrayDeque, this.$evalScope, null), 2);
                }
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.kairos.internal.Network$runInputScheduler$1, reason: invalid class name and case insensitive filesystem */
    final class C08841 extends ContinuationImpl {
        long J$0;
        long J$1;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;

        public C08841(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return Network.this.runInputScheduler(this);
        }
    }

    public Network(CoroutineScope coroutineScope) {
        this.coroutineScope = coroutineScope;
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a0 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00a1 -> B:27:0x00a7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object doTransaction(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        final Network network;
        int i;
        SchedulerImpl schedulerImpl;
        DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$1;
        Object objEvalOutputs;
        int i2;
        int i3;
        int i4 = 1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i5 = anonymousClass1.label;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i5 - Integer.MIN_VALUE;
                network = this;
            } else {
                network = this;
                anonymousClass1 = network.new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i6 = anonymousClass1.label;
        if (i6 == 0) {
            ResultKt.throwOnFailure(obj);
            i = 0;
            schedulerImpl = network.scheduler;
            schedulerImpl.getClass();
            Ref$IntRef ref$IntRef = new Ref$IntRef();
            while (!schedulerImpl.scheduledQ.isEmpty()) {
            }
            deferScopeKt$deferScope$scope$1 = new DeferScopeKt$deferScope$scope$1();
            EvalScopeImpl evalScopeImpl = new EvalScopeImpl(network, deferScopeKt$deferScope$scope$1);
            anonymousClass1.L$0 = network;
            anonymousClass1.L$1 = deferScopeKt$deferScope$scope$1;
            anonymousClass1.I$0 = i;
            anonymousClass1.label = 1;
            objEvalOutputs = network.evalOutputs(evalScopeImpl, anonymousClass1);
            if (objEvalOutputs != coroutineSingletons) {
            }
        } else {
            if (i6 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i7 = anonymousClass1.I$0;
            deferScopeKt$deferScope$scope$1 = (DeferScopeKt$deferScope$scope$1) anonymousClass1.L$1;
            Network network2 = (Network) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            deferScopeKt$deferScope$scope$1.drainDeferrals();
            if (zBooleanValue) {
                DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$12 = new DeferScopeKt$deferScope$scope$1();
                EvalScopeImpl evalScopeImpl2 = new EvalScopeImpl(network2, deferScopeKt$deferScope$scope$12);
                while (!network2.stateWrites.isEmpty()) {
                    StateSource stateSource = (StateSource) network2.stateWrites.removeFirst();
                    NodeConnection nodeConnection = stateSource.upstreamConnection;
                    if (nodeConnection == null) {
                        nodeConnection = null;
                    }
                    stateSource._current = new CompletableLazy(nodeConnection.directUpstream.getPushEvent(evalScopeImpl2), null, 2, null);
                    stateSource.writeEpoch = evalScopeImpl2.$$delegate_0.getEpoch() + 1;
                }
                Unit unit = Unit.INSTANCE;
                deferScopeKt$deferScope$scope$12.drainDeferrals();
                network2.transactionStore.storage.store.clear();
                network2.epoch++;
                DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$13 = new DeferScopeKt$deferScope$scope$1();
                EvalScopeImpl evalScopeImpl3 = new EvalScopeImpl(network2, deferScopeKt$deferScope$scope$13);
                while (!network2.muxMovers.isEmpty()) {
                    MuxDeferredNode muxDeferredNode = (MuxDeferredNode) network2.muxMovers.removeFirst();
                    if (muxDeferredNode.name != null) {
                        Iterable iterable = muxDeferredNode.patchData;
                        muxDeferredNode.toString();
                        Objects.toString(iterable);
                    }
                    Iterable<Map.Entry> iterable2 = muxDeferredNode.patchData;
                    if (iterable2 == null) {
                        i2 = i4;
                    } else {
                        muxDeferredNode.patchData = null;
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        for (Map.Entry entry : iterable2) {
                            Object key = entry.getKey();
                            Maybe maybe = (Maybe) entry.getValue();
                            if (maybe instanceof Maybe.Present) {
                                arrayList.add(new Pair(key, ((Maybe.Present) maybe).value));
                            } else {
                                if (!Intrinsics.areEqual(maybe, Maybe.Absent.INSTANCE)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                arrayList2.add(key);
                            }
                        }
                        ArrayList arrayList3 = new ArrayList();
                        int size = arrayList2.size();
                        int i8 = 0;
                        while (i8 < size) {
                            Object obj2 = arrayList2.get(i8);
                            i8 += i4;
                            MutableMapK mutableMapK = muxDeferredNode.switchedIn;
                            if (mutableMapK == null) {
                                mutableMapK = null;
                            }
                            MuxNode.BranchNode branchNode = (MuxNode.BranchNode) mutableMapK.remove(obj2);
                            if (branchNode != null) {
                                NodeConnection nodeConnection2 = branchNode.upstream;
                                if (nodeConnection2 == null) {
                                    nodeConnection2 = null;
                                }
                                arrayList3.add(nodeConnection2);
                                i3 = i4;
                                nodeConnection2.schedulerUpstream.removeDownstream(branchNode.schedulable);
                                if (nodeConnection2.schedulerUpstream.getDepthTracker().snapshotIsDirect) {
                                    muxDeferredNode.depthTracker.removeDirectUpstream(nodeConnection2.schedulerUpstream.getDepthTracker().snapshotDirectDepth);
                                } else {
                                    muxDeferredNode.depthTracker.removeIndirectUpstream(nodeConnection2.schedulerUpstream.getDepthTracker().snapshotIndirectDepth);
                                    DepthTracker.updateIndirectRoots$default(muxDeferredNode.depthTracker, null, CollectionsKt___CollectionsKt.toSet(nodeConnection2.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, 5);
                                }
                            } else {
                                i3 = i4;
                            }
                            i4 = i3;
                        }
                        i2 = i4;
                        int size2 = arrayList.size();
                        int i9 = 0;
                        while (i9 < size2) {
                            Object obj3 = arrayList.get(i9);
                            i9++;
                            Pair pair = (Pair) obj3;
                            Object objComponent1 = pair.component1();
                            EventsImpl eventsImpl = (EventsImpl) pair.component2();
                            MutableMapK mutableMapK2 = muxDeferredNode.switchedIn;
                            if (mutableMapK2 == null) {
                                mutableMapK2 = null;
                            }
                            MuxNode.BranchNode branchNode2 = (MuxNode.BranchNode) mutableMapK2.remove(objComponent1);
                            if (branchNode2 != null) {
                                NodeConnection nodeConnection3 = branchNode2.upstream;
                                if (nodeConnection3 == null) {
                                    nodeConnection3 = null;
                                }
                                arrayList3.add(nodeConnection3);
                                nodeConnection3.schedulerUpstream.removeDownstream(branchNode2.schedulable);
                                if (nodeConnection3.schedulerUpstream.getDepthTracker().snapshotIsDirect) {
                                    muxDeferredNode.depthTracker.removeDirectUpstream(nodeConnection3.schedulerUpstream.getDepthTracker().snapshotDirectDepth);
                                } else {
                                    muxDeferredNode.depthTracker.removeIndirectUpstream(nodeConnection3.schedulerUpstream.getDepthTracker().snapshotIndirectDepth);
                                    DepthTracker.updateIndirectRoots$default(muxDeferredNode.depthTracker, null, CollectionsKt___CollectionsKt.toSet(nodeConnection3.schedulerUpstream.getDepthTracker()._snapshotIndirectRoots), null, 5);
                                }
                            }
                            MuxNode.BranchNode branchNode3 = new MuxNode.BranchNode(objComponent1);
                            ActivationResult activationResultActivate = eventsImpl.activate(evalScopeImpl3, branchNode3.schedulable);
                            if (activationResultActivate != null) {
                                branchNode3.upstream = activationResultActivate.connection;
                                MutableMapK mutableMapK3 = muxDeferredNode.switchedIn;
                                if (mutableMapK3 == null) {
                                    mutableMapK3 = null;
                                }
                                mutableMapK3.put(objComponent1, branchNode3);
                                NodeConnection nodeConnection4 = branchNode3.upstream;
                                if (nodeConnection4 == null) {
                                    nodeConnection4 = null;
                                }
                                DepthTracker depthTracker = nodeConnection4.schedulerUpstream.getDepthTracker();
                                if (depthTracker.snapshotIsDirect) {
                                    muxDeferredNode.depthTracker.addDirectUpstream(depthTracker.snapshotDirectDepth, null);
                                } else {
                                    muxDeferredNode.depthTracker.addIndirectUpstream(depthTracker.snapshotIndirectDepth, null);
                                    DepthTracker.updateIndirectRoots$default(muxDeferredNode.depthTracker, CollectionsKt___CollectionsKt.toSet(depthTracker._snapshotIndirectRoots), null, muxDeferredNode, 2);
                                }
                            }
                        }
                        int size3 = arrayList3.size();
                        int i10 = 0;
                        while (i10 < size3) {
                            Object obj4 = arrayList3.get(i10);
                            i10++;
                            ((NodeConnection) obj4).schedulerUpstream.scheduleDeactivationIfNeeded(evalScopeImpl3);
                        }
                        SchedulerImpl compactor = evalScopeImpl3.$$delegate_0.getCompactor();
                        DepthTracker depthTracker2 = muxDeferredNode.depthTracker;
                        if (depthTracker2.isDirty()) {
                            depthTracker2.schedule(compactor, muxDeferredNode);
                        }
                    }
                    i4 = i2;
                }
                Unit unit2 = Unit.INSTANCE;
                deferScopeKt$deferScope$scope$13.drainDeferrals();
                network2.scheduler.drainCompact$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                network2.compactor.drainCompact$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                while (!network2.deactivations.isEmpty()) {
                    ((PushNode) network2.deactivations.removeLast()).deactivateIfNeeded();
                }
                while (!network2.outputDeactivations.isEmpty()) {
                    Output output = (Output) network2.outputDeactivations.removeFirst();
                    NodeConnection nodeConnection5 = output.upstream;
                    if (nodeConnection5 != null) {
                        nodeConnection5.schedulerUpstream.removeDownstreamAndDeactivateIfNeeded(output.schedulable);
                    }
                }
                if (!network2.deactivations.isEmpty()) {
                    throw new IllegalStateException("unexpected lingering deactivations");
                }
                if (network2.outputDeactivations.isEmpty()) {
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("unexpected lingering output deactivations");
            }
            i = i7;
            network = network2;
            schedulerImpl = network.scheduler;
            schedulerImpl.getClass();
            Ref$IntRef ref$IntRef2 = new Ref$IntRef();
            while (!schedulerImpl.scheduledQ.isEmpty()) {
                Pair pair2 = (Pair) schedulerImpl.scheduledQ.peek();
                if (pair2 == null) {
                    throw new IllegalStateException("Unexpected empty scheduler");
                }
                new SchedulerImpl$drain$1(((Number) pair2.getFirst()).intValue(), schedulerImpl, ref$IntRef2).invoke(LogIndent.m2587boximpl(), new Function2() { // from class: com.android.systemui.kairos.internal.SchedulerImpl$drainEval$1$1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj5, Object obj6) {
                        int i11 = ((LogIndent) obj5).currentLogIndent;
                        MuxNode muxNode = (MuxNode) obj6;
                        Network network3 = this.$network;
                        DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$14 = new DeferScopeKt$deferScope$scope$1();
                        EvalScopeImpl evalScopeImpl4 = new EvalScopeImpl(network3, deferScopeKt$deferScope$scope$14);
                        muxNode.markedForEvaluation = false;
                        muxNode.visit(evalScopeImpl4);
                        Unit unit3 = Unit.INSTANCE;
                        deferScopeKt$deferScope$scope$14.drainDeferrals();
                        return Unit.INSTANCE;
                    }
                });
                network.compactor.drainCompact$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
            }
            deferScopeKt$deferScope$scope$1 = new DeferScopeKt$deferScope$scope$1();
            EvalScopeImpl evalScopeImpl4 = new EvalScopeImpl(network, deferScopeKt$deferScope$scope$1);
            anonymousClass1.L$0 = network;
            anonymousClass1.L$1 = deferScopeKt$deferScope$scope$1;
            anonymousClass1.I$0 = i;
            anonymousClass1.label = 1;
            objEvalOutputs = network.evalOutputs(evalScopeImpl4, anonymousClass1);
            if (objEvalOutputs != coroutineSingletons) {
                return coroutineSingletons;
            }
            Network network3 = network;
            i7 = i;
            obj = objEvalOutputs;
            network2 = network3;
            boolean zBooleanValue2 = ((Boolean) obj).booleanValue();
            deferScopeKt$deferScope$scope$1.drainDeferrals();
            if (zBooleanValue2) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.kairos.internal.EvalScope] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x006b -> B:24:0x006d). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object evalOutputs(EvalScopeImpl evalScopeImpl, ContinuationImpl continuationImpl) {
        C08831 c08831;
        EvalScopeImpl evalScopeImpl2;
        if (continuationImpl instanceof C08831) {
            c08831 = (C08831) continuationImpl;
            int i = c08831.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08831.label = i - Integer.MIN_VALUE;
            } else {
                c08831 = new C08831(continuationImpl);
            }
        }
        Object obj = c08831.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08831.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            evalScopeImpl2 = evalScopeImpl;
            if (this.outputsByDispatcher.isEmpty()) {
                return Boolean.FALSE;
            }
            if (!this.outputsByDispatcher.isEmpty()) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Ref$BooleanRef ref$BooleanRef = (Ref$BooleanRef) c08831.L$2;
            ?? r6 = (EvalScope) c08831.L$1;
            Network network = (Network) c08831.L$0;
            ResultKt.throwOnFailure(obj);
            EvalScopeImpl evalScopeImpl3 = r6;
            if (!ref$BooleanRef.element) {
                network.outputsByDispatcher.clear();
            }
            this = network;
            evalScopeImpl2 = evalScopeImpl3;
            if (!this.outputsByDispatcher.isEmpty()) {
                Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                AnonymousClass2 anonymousClass2 = this.new AnonymousClass2(ref$BooleanRef2, evalScopeImpl2, null);
                c08831.L$0 = this;
                c08831.L$1 = evalScopeImpl2;
                c08831.L$2 = ref$BooleanRef2;
                c08831.label = 1;
                if (CoroutineScopeKt.coroutineScope(anonymousClass2, c08831) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                network = this;
                ref$BooleanRef = ref$BooleanRef2;
                evalScopeImpl3 = evalScopeImpl2;
                if (!ref$BooleanRef.element) {
                }
                this = network;
                evalScopeImpl2 = evalScopeImpl3;
                if (!this.outputsByDispatcher.isEmpty()) {
                    return Boolean.TRUE;
                }
            }
        }
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getCompactor() {
        return this.compactor;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final long getEpoch() {
        return this.epoch;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Object getNetworkId() {
        return this.networkId;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final SchedulerImpl getScheduler() {
        return this.scheduler;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final TransactionStore getTransactionStore() {
        return this.transactionStore;
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x019e, code lost:
    
        r0 = kotlin.Unit.INSTANCE;
        r1.drainDeferrals();
        r2.L$0 = r5;
        r2.L$1 = r13;
        r2.L$2 = r4;
        r2.L$3 = r6;
        r2.L$4 = null;
        r2.L$5 = null;
        r2.L$6 = null;
        r2.J$0 = r14;
        r2.J$1 = r11;
        r2.label = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01bd, code lost:
    
        if (r5.doTransaction(r2) != r3) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01c0, code lost:
    
        r0 = r5;
        r5 = r4;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x019e A[EDGE_INSN: B:111:0x019e->B:64:0x019e BREAK  A[LOOP:1: B:52:0x0167->B:57:0x0191], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011c A[PHI: r0 r4 r5
      0x011c: PHI (r0v16 kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator) = 
      (r0v17 kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator)
      (r0v27 kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator)
     binds: [B:42:0x0118, B:31:0x00af] A[DONT_GENERATE, DONT_INLINE]
      0x011c: PHI (r4v8 java.util.List) = (r4v10 java.util.List), (r4v17 java.util.List) binds: [B:42:0x0118, B:31:0x00af] A[DONT_GENERATE, DONT_INLINE]
      0x011c: PHI (r5v5 com.android.systemui.kairos.internal.Network) = (r5v6 com.android.systemui.kairos.internal.Network), (r5v14 com.android.systemui.kairos.internal.Network) binds: [B:42:0x0118, B:31:0x00af] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x016d A[Catch: all -> 0x019a, Exception -> 0x019c, TryCatch #7 {Exception -> 0x019c, all -> 0x019a, blocks: (B:51:0x0151, B:52:0x0167, B:54:0x016d, B:64:0x019e), top: B:107:0x0151 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x023d A[Catch: all -> 0x0247, LOOP:3: B:99:0x0234->B:89:0x023d, LOOP_END, TryCatch #3 {all -> 0x0247, blocks: (B:87:0x0234, B:89:0x023d, B:92:0x024a), top: B:99:0x0234 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0257  */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x01c0 -> B:18:0x004f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x024f -> B:41:0x010b). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object runInputScheduler(ContinuationImpl continuationImpl) throws Throwable {
        C08841 c08841;
        Mutex mutex;
        Throwable th;
        List list;
        ?? r4;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator;
        List list2;
        Exception e;
        long j;
        long j2;
        EvalScope evalScopeImpl;
        Network network;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator2;
        DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$1;
        Iterator it;
        BufferedChannel.BufferedChannelIterator bufferedChannelIterator3;
        ScheduledAction scheduledAction;
        Network network2 = this;
        if (continuationImpl instanceof C08841) {
            c08841 = (C08841) continuationImpl;
            int i = c08841.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08841.label = i - Integer.MIN_VALUE;
            } else {
                c08841 = network2.new C08841(continuationImpl);
            }
        }
        Object objHasNext = c08841.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08841.label;
        int i3 = 1;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(objHasNext);
                ArrayList arrayList = new ArrayList();
                BufferedChannel bufferedChannel = network2.inputScheduleChan;
                bufferedChannel.getClass();
                bufferedChannelIterator = bufferedChannel.new BufferedChannelIterator();
                list2 = arrayList;
                c08841.L$0 = network2;
                c08841.L$1 = list2;
                c08841.L$2 = bufferedChannelIterator;
                c08841.L$3 = null;
                c08841.label = i3;
                objHasNext = bufferedChannelIterator.hasNext(c08841);
                if (objHasNext != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 == 1) {
                bufferedChannelIterator3 = (BufferedChannel.BufferedChannelIterator) c08841.L$2;
                list2 = (List) c08841.L$1;
                network = (Network) c08841.L$0;
                ResultKt.throwOnFailure(objHasNext);
                if (((Boolean) objHasNext).booleanValue()) {
                }
            } else {
                if (i2 != 2) {
                    if (i2 == 3) {
                        Mutex mutex2 = (Mutex) c08841.L$3;
                        bufferedChannelIterator2 = (BufferedChannel.BufferedChannelIterator) c08841.L$2;
                        List list3 = (List) c08841.L$1;
                        Network network3 = (Network) c08841.L$0;
                        ResultKt.throwOnFailure(objHasNext);
                        list = list3;
                        network = network3;
                        mutex = mutex2;
                        long j3 = network.epoch;
                        TimeSource$Monotonic.INSTANCE.getClass();
                        MonotonicTimeSource.INSTANCE.getClass();
                        long j4 = MonotonicTimeSource.read();
                        DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$12 = new DeferScopeKt$deferScope$scope$1();
                        evalScopeImpl = new EvalScopeImpl(network, deferScopeKt$deferScope$scope$12);
                        j = j3;
                        j2 = j4;
                        deferScopeKt$deferScope$scope$1 = deferScopeKt$deferScope$scope$12;
                        it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                            }
                        }
                        return coroutineSingletons;
                    }
                    if (i2 == 4) {
                        j2 = c08841.J$1;
                        long j5 = c08841.J$0;
                        it = (Iterator) c08841.L$6;
                        EvalScope evalScope = (EvalScope) c08841.L$5;
                        DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$13 = (DeferScopeKt$deferScope$scope$1) c08841.L$4;
                        Mutex mutex3 = (Mutex) c08841.L$3;
                        BufferedChannel.BufferedChannelIterator bufferedChannelIterator4 = (BufferedChannel.BufferedChannelIterator) c08841.L$2;
                        List list4 = (List) c08841.L$1;
                        Network network4 = (Network) c08841.L$0;
                        try {
                            ResultKt.throwOnFailure(objHasNext);
                            evalScopeImpl = evalScope;
                            bufferedChannelIterator2 = bufferedChannelIterator4;
                            mutex = mutex3;
                            network = network4;
                            deferScopeKt$deferScope$scope$1 = deferScopeKt$deferScope$scope$13;
                            j = j5;
                            list = list4;
                            while (true) {
                                if (!it.hasNext()) {
                                }
                            }
                            return coroutineSingletons;
                        } catch (Exception e2) {
                            e = e2;
                            list = list4;
                        } catch (Throwable th2) {
                            th = th2;
                            r4 = mutex3;
                            list = list4;
                            while (!list.isEmpty()) {
                                ((ScheduledAction) list.removeLast()).completed();
                            }
                            throw th;
                        }
                    } else {
                        if (i2 != 5) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        j2 = c08841.J$1;
                        Mutex mutex4 = (Mutex) c08841.L$3;
                        BufferedChannel.BufferedChannelIterator bufferedChannelIterator5 = (BufferedChannel.BufferedChannelIterator) c08841.L$2;
                        list = (List) c08841.L$1;
                        Network network5 = (Network) c08841.L$0;
                        try {
                            ResultKt.throwOnFailure(objHasNext);
                            bufferedChannelIterator = bufferedChannelIterator5;
                            mutex = mutex4;
                            network2 = network5;
                            list2 = list;
                            while (!list2.isEmpty()) {
                                try {
                                    ((ScheduledAction) list2.removeLast()).completed();
                                } catch (Throwable th3) {
                                    th = th3;
                                    mutex.unlock(null);
                                    throw th;
                                }
                            }
                            MonotonicTimeSource.INSTANCE.getClass();
                            Duration.m3466toStringimpl(((j2 - 1) | 1) == Long.MAX_VALUE ? Duration.m3467unaryMinusUwyO8pc(LongSaturatedMathKt.infinityOfSign(j2)) : LongSaturatedMathKt.saturatingFiniteDiff(MonotonicTimeSource.read(), j2, DurationUnit.NANOSECONDS));
                            Unit unit = Unit.INSTANCE;
                            mutex.unlock(null);
                            i3 = 1;
                            c08841.L$0 = network2;
                            c08841.L$1 = list2;
                            c08841.L$2 = bufferedChannelIterator;
                            c08841.L$3 = null;
                            c08841.label = i3;
                            objHasNext = bufferedChannelIterator.hasNext(c08841);
                            if (objHasNext != coroutineSingletons) {
                                BufferedChannel.BufferedChannelIterator bufferedChannelIterator6 = bufferedChannelIterator;
                                network = network2;
                                bufferedChannelIterator3 = bufferedChannelIterator6;
                                if (((Boolean) objHasNext).booleanValue()) {
                                    return Unit.INSTANCE;
                                }
                                list2.add((ScheduledAction) bufferedChannelIterator3.next());
                                c08841.L$0 = network;
                                c08841.L$1 = list2;
                                c08841.L$2 = bufferedChannelIterator3;
                                c08841.label = 2;
                                if (YieldKt.yield(c08841) != coroutineSingletons) {
                                    scheduledAction = (ScheduledAction) ChannelResult.m3479getOrNullimpl(network.inputScheduleChan.mo3475tryReceivePtdJZtk());
                                    if (scheduledAction != null) {
                                        mutex = network.transactionMutex;
                                        c08841.L$0 = network;
                                        c08841.L$1 = list2;
                                        c08841.L$2 = bufferedChannelIterator3;
                                        c08841.L$3 = mutex;
                                        c08841.label = 3;
                                        if (mutex.lock(c08841) != coroutineSingletons) {
                                            try {
                                                list = list2;
                                                bufferedChannelIterator2 = bufferedChannelIterator3;
                                                DeferScopeKt$deferScope$scope$1 deferScopeKt$deferScope$scope$122 = new DeferScopeKt$deferScope$scope$1();
                                                evalScopeImpl = new EvalScopeImpl(network, deferScopeKt$deferScope$scope$122);
                                                j = j3;
                                                j2 = j4;
                                                deferScopeKt$deferScope$scope$1 = deferScopeKt$deferScope$scope$122;
                                                it = list.iterator();
                                                while (true) {
                                                    if (!it.hasNext()) {
                                                        break;
                                                    }
                                                    ScheduledAction scheduledAction2 = (ScheduledAction) it.next();
                                                    c08841.L$0 = network;
                                                    c08841.L$1 = list;
                                                    c08841.L$2 = bufferedChannelIterator2;
                                                    c08841.L$3 = mutex;
                                                    c08841.L$4 = deferScopeKt$deferScope$scope$1;
                                                    c08841.L$5 = evalScopeImpl;
                                                    c08841.L$6 = it;
                                                    c08841.J$0 = j;
                                                    c08841.J$1 = j2;
                                                    c08841.label = 4;
                                                    if (scheduledAction2.started(evalScopeImpl, c08841) == coroutineSingletons) {
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e3) {
                                                e = e3;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                r4 = mutex;
                                                while (!list.isEmpty()) {
                                                    try {
                                                    } catch (Throwable th5) {
                                                        th = th5;
                                                        mutex = r4;
                                                        mutex.unlock(null);
                                                        throw th;
                                                    }
                                                }
                                                throw th;
                                            }
                                            long j32 = network.epoch;
                                            TimeSource$Monotonic.INSTANCE.getClass();
                                            MonotonicTimeSource.INSTANCE.getClass();
                                            long j42 = MonotonicTimeSource.read();
                                        }
                                    } else {
                                        list2.add(scheduledAction);
                                        c08841.L$0 = network;
                                        c08841.L$1 = list2;
                                        c08841.L$2 = bufferedChannelIterator3;
                                        c08841.label = 2;
                                        if (YieldKt.yield(c08841) != coroutineSingletons) {
                                        }
                                    }
                                }
                            }
                            return coroutineSingletons;
                        } catch (Exception e4) {
                            e = e4;
                        }
                    }
                    while (!list.isEmpty()) {
                        ScheduledAction scheduledAction3 = (ScheduledAction) list.removeLast();
                        scheduledAction3.getClass();
                        Maybe.Companion.getClass();
                        scheduledAction3.result = Maybe.Companion.absent;
                        CompletableDeferred completableDeferred = scheduledAction3.onResult;
                        if (completableDeferred != null) {
                            ((CompletableDeferredImpl) completableDeferred).completeExceptionally(e);
                        }
                    }
                    throw e;
                }
                bufferedChannelIterator3 = (BufferedChannel.BufferedChannelIterator) c08841.L$2;
                list2 = (List) c08841.L$1;
                network = (Network) c08841.L$0;
                ResultKt.throwOnFailure(objHasNext);
                scheduledAction = (ScheduledAction) ChannelResult.m3479getOrNullimpl(network.inputScheduleChan.mo3475tryReceivePtdJZtk());
                if (scheduledAction != null) {
                }
            }
        } catch (Throwable th6) {
            th = th6;
            r4 = i2;
        }
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void schedule(StateSource stateSource) {
        this.stateWrites.addLast(stateSource);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(Output output) {
        this.outputDeactivations.addLast(output);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleMuxMover(MuxDeferredNode muxDeferredNode) {
        this.muxMovers.addLast(muxDeferredNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleOutput(Output output) {
        CoroutineContext.Element element = (ContinuationInterceptor) output.context.get(ContinuationInterceptor.Key);
        if (element == null) {
            element = Dispatchers.Unconfined;
        }
        HashMap map = this.outputsByDispatcher;
        final Network$$ExternalSyntheticLambda0 network$$ExternalSyntheticLambda0 = new Network$$ExternalSyntheticLambda0(2);
        ((ArrayDeque) map.computeIfAbsent(element, new Function() { // from class: com.android.systemui.kairos.internal.NetworkKt$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return network$$ExternalSyntheticLambda0.mo781invoke(obj);
            }
        })).addLast(output);
    }

    public final CompletableDeferredImpl transaction(String str, Function2 function2) {
        CoroutineScope coroutineScope = this.coroutineScope;
        CompletableDeferredImpl completableDeferredImpl = new CompletableDeferredImpl(JobKt.getJob(coroutineScope.getCoroutineContext()));
        if (!CoroutineScopeKt.isActive(coroutineScope)) {
            completableDeferredImpl.cancel(null);
            return completableDeferredImpl;
        }
        final StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(coroutineScope, null, null, new Network$transaction$1$job$1(this, str, completableDeferredImpl, function2, null), 3);
        completableDeferredImpl.invokeOnCompletion(new Function1() { // from class: com.android.systemui.kairos.internal.Network$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                standaloneCoroutineLaunch$default.cancel(null);
                return Unit.INSTANCE;
            }
        });
        return completableDeferredImpl;
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final void scheduleDeactivation(PushNode pushNode) {
        this.deactivations.addLast(pushNode);
    }

    @Override // com.android.systemui.kairos.internal.NetworkScope
    public final Network getNetwork() {
        return this;
    }
}
