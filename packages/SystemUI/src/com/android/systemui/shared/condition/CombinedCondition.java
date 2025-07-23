package com.android.systemui.shared.condition;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CombinedCondition extends Condition {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy _startStrategy$delegate;
    public final Collection conditions;
    public final int operand;
    public final CoroutineScope scope;

    public CombinedCondition(CoroutineScope coroutineScope, Collection<? extends Condition> collection, int i) {
        super(coroutineScope, null, false);
        this.scope = coroutineScope;
        this.conditions = collection;
        this.operand = i;
        this._startStrategy$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shared.condition.CombinedCondition$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Iterator it = CombinedCondition.this.conditions.iterator();
                int i2 = 2;
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    int startStrategy = ((Condition) it.next()).getStartStrategy();
                    if (startStrategy == 0) {
                        i2 = 0;
                        break;
                    }
                    if (startStrategy == 1) {
                        i2 = 1;
                    }
                }
                return Integer.valueOf(i2);
            }
        });
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return ((Number) this._startStrategy$delegate.getValue()).intValue();
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        Collection collection = this.conditions;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : collection) {
            Boolean valueOf = Boolean.valueOf(((Condition) obj).isOverridingCondition);
            Object obj2 = linkedHashMap.get(valueOf);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(valueOf, obj2);
            }
            ((List) obj2).add(obj);
        }
        Object collect = FlowKt.transformLatest(FlowKt.distinctUntilChanged(FlowKt.callbackFlow(new CombinedCondition$lazilyEvaluate$1((Collection) linkedHashMap.getOrDefault(Boolean.TRUE, EmptyList.INSTANCE), true, this, null))), new CombinedCondition$start$$inlined$flatMapLatest$1(null, this, linkedHashMap)).collect(new FlowCollector() { // from class: com.android.systemui.shared.condition.CombinedCondition$start$3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj3, Continuation continuation2) {
                Boolean bool = (Boolean) obj3;
                CombinedCondition combinedCondition = CombinedCondition.this;
                if (bool != null) {
                    combinedCondition.updateCondition(bool.booleanValue());
                } else if (combinedCondition._isConditionMet != null) {
                    String str = combinedCondition.mTag;
                    if (Log.isLoggable(str, 3)) {
                        Log.d(str, "clearing condition");
                    }
                    combinedCondition._isConditionMet = null;
                    combinedCondition.sendUpdate();
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
    }
}
