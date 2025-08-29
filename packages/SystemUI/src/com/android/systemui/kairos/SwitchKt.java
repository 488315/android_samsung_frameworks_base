package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.ActivationResult;
import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.EventsImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.InitKt$constInit$1;
import com.android.systemui.kairos.internal.MapNode;
import com.android.systemui.kairos.internal.MuxDeferredKt;
import com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$patches$1;
import com.android.systemui.kairos.internal.MuxLifecycle;
import com.android.systemui.kairos.internal.NodeConnection;
import com.android.systemui.kairos.internal.Schedulable;
import com.android.systemui.kairos.internal.StateImpl;
import com.android.systemui.kairos.internal.store.MapK;
import com.android.systemui.kairos.internal.store.Single;
import com.android.systemui.kairos.internal.store.SingletonMapK;
import com.android.systemui.kairos.util.Maybe;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public abstract class SwitchKt {
    public static final EventsInit switchEvents(final State state) {
        final SwitchKt$$ExternalSyntheticLambda3 switchKt$$ExternalSyntheticLambda3 = new SwitchKt$$ExternalSyntheticLambda3(0);
        final EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.SwitchKt$switchEvents$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = ((StateImpl) state.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).changes.activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, switchKt$$ExternalSyntheticLambda3), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        };
        final MuxDeferredKt$switchDeferredImplSingle$patches$1 muxDeferredKt$switchDeferredImplSingle$patches$1 = new Function3() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$patches$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                Maybe.Companion.getClass();
                return new Single(Maybe.Present.m2588boximpl((EventsImpl) obj2)).getEntries();
            }
        };
        final EventsImpl eventsImpl2 = new EventsImpl() { // from class: com.android.systemui.kairos.SwitchKt$switchEvents$$inlined$switchDeferredImplSingle$default$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = eventsImpl.activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, muxDeferredKt$switchDeferredImplSingle$patches$1), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        };
        final MuxLifecycle muxLifecycleSwitchDeferredImpl = MuxDeferredKt.switchDeferredImpl(new Function1() { // from class: com.android.systemui.kairos.SwitchKt$switchEvents$$inlined$switchDeferredImplSingle$default$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                EvalScope evalScope = (EvalScope) obj;
                return new Single((EventsImpl) EventsKt.getInit((Events) ((StateImpl) state.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst()).connect(evalScope)).getEntries();
            }
        }, new Function1() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$switchDeferredImpl$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return eventsImpl2;
            }
        }, new SingletonMapK.Factory());
        final String str = null;
        final Function3 function3 = new Function3() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$2
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                Object pushEvent = ((PullNode) MapsKt__MapsKt.getValue(Unit.INSTANCE, (Single) ((MapK) obj2))).getPushEvent((EvalScope) obj);
                if (str != null) {
                    Objects.toString(pushEvent);
                }
                return pushEvent;
            }
        };
        return new EventsInit(new Init(null, new InitKt$constInit$1(new EventsImpl() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activationResultActivate = muxLifecycleSwitchDeferredImpl.activate(evalScope, schedulable);
                if (activationResultActivate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activationResultActivate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, function3), nodeConnection.schedulerUpstream), activationResultActivate.needsEval);
            }
        })));
    }
}
