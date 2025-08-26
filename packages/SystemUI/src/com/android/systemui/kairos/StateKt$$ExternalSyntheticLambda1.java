package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1;
import com.android.systemui.kairos.internal.DemuxKt;
import com.android.systemui.kairos.internal.DerivedFlatten;
import com.android.systemui.kairos.internal.DerivedMapCheap;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1;
import com.android.systemui.kairos.internal.FilterNodeKt$filterPresentImpl$1;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.MuxLifecycle;
import com.android.systemui.kairos.internal.MuxLifecycleState;
import com.android.systemui.kairos.internal.MuxPromptActivator;
import com.android.systemui.kairos.internal.MuxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1;
import com.android.systemui.kairos.internal.MuxPromptKt$switchPromptImplSingle$2;
import com.android.systemui.kairos.internal.NoScope;
import com.android.systemui.kairos.internal.PullNodesKt;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.StateImplKt;
import com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda0;
import com.android.systemui.kairos.internal.StateImplKt$$ExternalSyntheticLambda1;
import com.android.systemui.kairos.internal.StateImplKt$calm$$inlined$filterImpl$1;
import com.android.systemui.kairos.internal.StateImplKt$calm$$inlined$filterImpl$2;
import com.android.systemui.kairos.internal.store.Single;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class StateKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ State f$2;
    public final /* synthetic */ Function2 f$3;

    public /* synthetic */ StateKt$$ExternalSyntheticLambda1(State state, Function2 function2, int i) {
        this.$r8$classId = i;
        this.f$2 = state;
        this.f$3 = function2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                StateKt$$ExternalSyntheticLambda0 stateKt$$ExternalSyntheticLambda0 = new StateKt$$ExternalSyntheticLambda0(this.f$2, 1);
                final Function2 function2 = this.f$3;
                final int i = 0;
                return StateImplKt.mapStateImpl(stateKt$$ExternalSyntheticLambda0, "map", "map", new Function2() { // from class: com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        EvalScope evalScope = (EvalScope) obj2;
                        switch (i) {
                            case 0:
                                return function2.invoke(NoScope.INSTANCE, obj3);
                            default:
                                return (StateImpl) ((State) function2.invoke(NoScope.INSTANCE, obj3)).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope);
                        }
                    }
                });
            case 1:
                StateKt$$ExternalSyntheticLambda0 stateKt$$ExternalSyntheticLambda02 = new StateKt$$ExternalSyntheticLambda0(this.f$2, 2);
                final Function2 function22 = this.f$3;
                final int i2 = 1;
                final StateImpl stateImplMapStateImpl = StateImplKt.mapStateImpl(stateKt$$ExternalSyntheticLambda02, null, "flatMap", new Function2() { // from class: com.android.systemui.kairos.StateKt$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        EvalScope evalScope = (EvalScope) obj2;
                        switch (i2) {
                            case 0:
                                return function22.invoke(NoScope.INSTANCE, obj3);
                            default:
                                return (StateImpl) ((State) function22.invoke(NoScope.INSTANCE, obj3)).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope);
                        }
                    }
                });
                final Function1 function1 = new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$flatMapStateImpl$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        return stateImplMapStateImpl;
                    }
                };
                final StateImplKt$$ExternalSyntheticLambda0 stateImplKt$$ExternalSyntheticLambda0 = new StateImplKt$$ExternalSyntheticLambda0(0);
                final StateImplKt$$ExternalSyntheticLambda1 stateImplKt$$ExternalSyntheticLambda1 = new StateImplKt$$ExternalSyntheticLambda1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$$inlined$mapImpl$1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = ((StateImpl) function1.mo781invoke(evalScope)).changes.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, stateImplKt$$ExternalSyntheticLambda0), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                }, 0);
                final EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$$inlined$mapImpl$2
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = ((StateImpl) function1.mo781invoke(evalScope)).changes.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, stateImplKt$$ExternalSyntheticLambda1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                };
                MuxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1 muxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1 = new MuxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1(MuxPromptKt$switchPromptImplSingle$2.INSTANCE, new MuxLifecycle(new MuxLifecycleState.Inactive(new MuxPromptActivator(null, new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$$inlined$switchPromptImplSingle$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        EvalScope evalScope = (EvalScope) obj2;
                        return new Single(((StateImpl) ((StateImpl) function1.mo781invoke(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst()).changes).getEntries();
                    }
                }, new SingletonMapK.Factory(), new Function1() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$$inlined$switchPromptImplSingle$2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        final MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1 muxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1 = MuxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1.INSTANCE;
                        final EventsImpl eventsImpl2 = eventsImpl;
                        return new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$flattenStateImpl$$inlined$switchPromptImplSingle$2.1
                            @Override // com.android.systemui.kairos.internal.EventsImpl
                            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                                ActivationResult activationResultActivate = eventsImpl2.activate(evalScope, schedulable);
                                if (activationResultActivate == null) {
                                    return null;
                                }
                                NodeConnection nodeConnection = activationResultActivate.connection;
                                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxPromptKt$switchPromptImplSingle$switchPromptImpl$2$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                            }
                        };
                    }
                }))));
                DerivedFlatten derivedFlatten = new DerivedFlatten(function1);
                return new StateImpl("flatMap", "flatMap", PullNodesKt.cached(new DemuxImpl$eventsForKey$$inlined$EventsImplCheap$1(DemuxKt.DemuxImpl(1, new FilterNodeKt$filterImpl$$inlined$filterPresentImpl$1(FilterNodeKt$filterPresentImpl$1.INSTANCE, PullNodesKt.cached(new StateImplKt$calm$$inlined$filterImpl$2(new StateImplKt$calm$$inlined$filterImpl$1(derivedFlatten), muxPromptKt$switchPromptImplSingle$$inlined$mapImpl$1))), new SingletonMapK.Factory()), Unit.INSTANCE)), derivedFlatten);
            default:
                final Init init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos = this.f$2.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos();
                StateKt$$ExternalSyntheticLambda8 stateKt$$ExternalSyntheticLambda8 = new StateKt$$ExternalSyntheticLambda8((BuildScope$DefaultImpls$$ExternalSyntheticLambda7) this.f$3, 1);
                final StateImplKt$$ExternalSyntheticLambda1 stateImplKt$$ExternalSyntheticLambda12 = new StateImplKt$$ExternalSyntheticLambda1(stateKt$$ExternalSyntheticLambda8, 1);
                return new StateImpl("map", "map", new EventsImpl() { // from class: com.android.systemui.kairos.internal.StateImplKt$mapStateImplCheap$$inlined$mapImpl$1
                    @Override // com.android.systemui.kairos.internal.EventsImpl
                    public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                        ActivationResult activationResultActivate = ((StateImpl) init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos.connect(evalScope)).changes.activate(evalScope, schedulable);
                        if (activationResultActivate == null) {
                            return null;
                        }
                        NodeConnection nodeConnection = activationResultActivate.connection;
                        return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, stateImplKt$$ExternalSyntheticLambda12), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
                    }
                }, new DerivedMapCheap(init$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos, stateKt$$ExternalSyntheticLambda8));
        }
    }
}
