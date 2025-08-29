package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1;
import com.android.systemui.kairos.internal.DemuxKt;
import com.android.systemui.kairos.internal.DerivedMap;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.FilterNodeKt$filterPresentImpl$1;
import com.android.systemui.kairos.internal.IncrementalImpl;
import com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$changes$2;
import com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$patches$2;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.PullNodesKt$cached$$inlined$EventsImplCheap$1;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import com.android.systemui.kairos.internal.store.StoreEntry;
import com.android.systemui.kairos.util.Maybe;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public final /* synthetic */ class IncrementalKt$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ State f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ IncrementalKt$$ExternalSyntheticLambda2(State state, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = state;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        NetworkScope networkScope = (NetworkScope) obj;
        switch (this.$r8$classId) {
            case 0:
                StateImpl stateImpl = (StateImpl) ((StateInit) this.f$0).init.connect(networkScope);
                return new IncrementalImpl(stateImpl.name, stateImpl.operatorName, stateImpl.changes, (EventsImpl) EventsKt.getInit((EventsInit) this.f$1).connect(networkScope), stateImpl.store);
            default:
                final IncrementalKt$$ExternalSyntheticLambda4 incrementalKt$$ExternalSyntheticLambda4 = new IncrementalKt$$ExternalSyntheticLambda4((Incremental) this.f$0, 1);
                final IncrementalKt$$ExternalSyntheticLambda6 incrementalKt$$ExternalSyntheticLambda6 = new IncrementalKt$$ExternalSyntheticLambda6((Function2) this.f$1);
                final DerivedMap derivedMap = new DerivedMap(incrementalKt$$ExternalSyntheticLambda4, new Function2() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        EvalScope evalScope = (EvalScope) obj2;
                        Map map = (Map) obj3;
                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                        for (Map.Entry entry : map.entrySet()) {
                            linkedHashMap.put(entry.getKey(), incrementalKt$$ExternalSyntheticLambda6.invoke(evalScope, entry));
                        }
                        return linkedHashMap;
                    }
                });
                final Function3 function3 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        Object objM2588boximpl;
                        EvalScope evalScope = (EvalScope) obj2;
                        Map map = (Map) obj3;
                        ((Integer) obj4).getClass();
                        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
                        for (Map.Entry entry : map.entrySet()) {
                            Object key = entry.getKey();
                            Object key2 = entry.getKey();
                            Maybe maybe = (Maybe) entry.getValue();
                            if (maybe instanceof Maybe.Present) {
                                Maybe.Companion companion = Maybe.Companion;
                                Object objInvoke = incrementalKt$$ExternalSyntheticLambda6.invoke(evalScope, new StoreEntry(key2, ((Maybe.Present) maybe).value));
                                companion.getClass();
                                objM2588boximpl = Maybe.Present.m2588boximpl(objInvoke);
                            } else {
                                if (!(maybe instanceof Maybe.Absent)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                objM2588boximpl = Maybe.Absent.INSTANCE;
                            }
                            linkedHashMap.put(key, objM2588boximpl);
                        }
                        return linkedHashMap;
                    }
                };
                final PullNodesKt$cached$$inlined$EventsImplCheap$1 pullNodesKt$cached$$inlined$EventsImplCheap$1Cached = PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$mapValuesImpl$$inlined$mapImpl$1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = ((IncrementalImpl) incrementalKt$$ExternalSyntheticLambda4.mo781invoke(evalScope)).patches.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, function3), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                });
                final Function3 function32 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$maybeUpdate$2
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        EvalScope evalScope = (EvalScope) obj2;
                        ((Number) obj4).intValue();
                        StateDerived stateDerived = derivedMap;
                        Pair pairAccess$applyPatchCalm = IncrementalImplKt.access$applyPatchCalm((Map) stateDerived.getCurrentWithEpoch(evalScope).component1(), (Map) obj3);
                        if (pairAccess$applyPatchCalm != null) {
                            Map map = (Map) pairAccess$applyPatchCalm.component2();
                            long epoch = evalScope.getEpoch();
                            stateDerived.cache = map;
                            long j = epoch + 1;
                            stateDerived.validatedEpoch = j;
                            stateDerived.invalidatedEpoch = j;
                        } else {
                            pairAccess$applyPatchCalm = null;
                        }
                        if (pairAccess$applyPatchCalm == null) {
                            return Maybe.Absent.INSTANCE;
                        }
                        Maybe.Companion.getClass();
                        return Maybe.Present.m2588boximpl(pairAccess$applyPatchCalm);
                    }
                };
                final PullNodesKt$cached$$inlined$EventsImplCheap$1 pullNodesKt$cached$$inlined$EventsImplCheap$1Cached2 = PullNodesKt.cached(new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$$inlined$mapImpl$1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = pullNodesKt$cached$$inlined$EventsImplCheap$1Cached.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, function32), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                });
                final FilterNodeKt$filterPresentImpl$1 filterNodeKt$filterPresentImpl$1 = FilterNodeKt$filterPresentImpl$1.INSTANCE;
                final DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1 demuxImpl$eventsForKey$$inlined$EventsImplCheap$1 = new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxKt.DemuxImpl(1, new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$$inlined$filterPresentImpl$1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = pullNodesKt$cached$$inlined$EventsImplCheap$1Cached2.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, filterNodeKt$filterPresentImpl$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                }, new SingletonMapK.Factory()), Unit.INSTANCE);
                final IncrementalImplKt$calmUpdates$patches$2 incrementalImplKt$calmUpdates$patches$2 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$patches$2
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        ((Number) obj4).intValue();
                        return (Map) ((Pair) obj3).component1();
                    }
                };
                EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$$inlined$mapImpl$2
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = demuxImpl$eventsForKey$$inlined$EventsImplCheap$1.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, incrementalImplKt$calmUpdates$patches$2), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                };
                final IncrementalImplKt$calmUpdates$changes$2 incrementalImplKt$calmUpdates$changes$2 = new Function3() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$changes$2
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        ((Number) obj4).intValue();
                        return (Map) ((Pair) obj3).component2();
                    }
                };
                Pair pair = new Pair(eventsImpl, new EventsImpl() { // from class: com.android.systemui.kairos.internal.IncrementalImplKt$calmUpdates$$inlined$mapImpl$3
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = demuxImpl$eventsForKey$$inlined$EventsImplCheap$1.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, incrementalImplKt$calmUpdates$changes$2), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                });
                return new IncrementalImpl("mapValues", "mapValues", (EventsImpl) pair.component2(), (EventsImpl) pair.component1(), derivedMap);
        }
    }
}
