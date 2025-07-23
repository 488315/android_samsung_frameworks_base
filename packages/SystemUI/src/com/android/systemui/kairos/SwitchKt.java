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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SwitchKt {
    public static final EventsInit switchEvents(final State state) {
        final SwitchKt$$ExternalSyntheticLambda3 switchKt$$ExternalSyntheticLambda3 = new SwitchKt$$ExternalSyntheticLambda3(0);
        final EventsImpl eventsImpl = new EventsImpl() { // from class: com.android.systemui.kairos.SwitchKt$switchEvents$$inlined$mapImpl$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = ((StateImpl) state.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).changes.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        };
        final MuxDeferredKt$switchDeferredImplSingle$patches$1 muxDeferredKt$switchDeferredImplSingle$patches$1 = new Function3() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$patches$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                Maybe.Companion.getClass();
                return new Single(Maybe.Present.m2573boximpl((EventsImpl) obj2)).getEntries();
            }
        };
        final EventsImpl eventsImpl2 = new EventsImpl() { // from class: com.android.systemui.kairos.SwitchKt$switchEvents$$inlined$switchDeferredImplSingle$default$1
            @Override // com.android.systemui.kairos.internal.EventsImpl
            public final ActivationResult activate(EvalScope evalScope, Schedulable schedulable) {
                ActivationResult activate = eventsImpl.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        };
        final MuxLifecycle switchDeferredImpl = MuxDeferredKt.switchDeferredImpl(new Function1() { // from class: com.android.systemui.kairos.SwitchKt$switchEvents$$inlined$switchDeferredImplSingle$default$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                EvalScope evalScope = (EvalScope) obj;
                return new Single((EventsImpl) EventsKt.getInit((Events) ((StateImpl) State.this.getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect(evalScope)).store.getCurrentWithEpoch(evalScope).getFirst()).connect(evalScope)).getEntries();
            }
        }, new Function1() { // from class: com.android.systemui.kairos.internal.MuxDeferredKt$switchDeferredImplSingle$switchDeferredImpl$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return EventsImpl.this;
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
                ActivationResult activate = switchDeferredImpl.activate(evalScope, schedulable);
                if (activate == null) {
                    return null;
                }
                NodeConnection nodeConnection = activate.connection;
                return new ActivationResult(new NodeConnection(new MapNode(nodeConnection.directUpstream, Function3.this), nodeConnection.schedulerUpstream), activate.needsEval);
            }
        })));
    }
}
