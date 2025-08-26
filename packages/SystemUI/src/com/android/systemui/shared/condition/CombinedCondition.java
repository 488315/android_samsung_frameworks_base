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
                Iterator it = this.f$0.conditions.iterator();
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
            Boolean boolValueOf = Boolean.valueOf(((Condition) obj).isOverridingCondition);
            Object arrayList = linkedHashMap.get(boolValueOf);
            if (arrayList == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(boolValueOf, arrayList);
            }
            ((List) arrayList).add(obj);
        }
        Object objCollect = FlowKt.transformLatest(FlowKt.distinctUntilChanged(FlowKt.callbackFlow(new CombinedCondition$lazilyEvaluate$1((Collection) linkedHashMap.getOrDefault(Boolean.TRUE, EmptyList.INSTANCE), true, this, null))), new CombinedCondition$start$$inlined$flatMapLatest$1(null, this, linkedHashMap)).collect(new FlowCollector() { // from class: com.android.systemui.shared.condition.CombinedCondition.start.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj2, Continuation continuation2) {
                Boolean bool = (Boolean) obj2;
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
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
    }
}
