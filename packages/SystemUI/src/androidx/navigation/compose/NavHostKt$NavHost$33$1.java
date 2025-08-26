package androidx.navigation.compose;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import androidx.navigation.NavBackStackEntry;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class NavHostKt$NavHost$33$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComposeNavigator $composeNavigator;
    final /* synthetic */ Transition<NavBackStackEntry> $transition;
    final /* synthetic */ State<List<NavBackStackEntry>> $visibleEntries$delegate;
    final /* synthetic */ Map<String, Float> $zIndices;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NavHostKt$NavHost$33$1(Transition<NavBackStackEntry> transition, Map<String, Float> map, State<? extends List<NavBackStackEntry>> state, ComposeNavigator composeNavigator, Continuation continuation) {
        super(2, continuation);
        this.$transition = transition;
        this.$zIndices = map;
        this.$visibleEntries$delegate = state;
        this.$composeNavigator = composeNavigator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NavHostKt$NavHost$33$1(this.$transition, this.$zIndices, this.$visibleEntries$delegate, this.$composeNavigator, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NavHostKt$NavHost$33$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (Intrinsics.areEqual(this.$transition.transitionState.getCurrentState(), ((SnapshotMutableStateImpl) this.$transition.targetState$delegate).getValue())) {
            List list = (List) this.$visibleEntries$delegate.getValue();
            ComposeNavigator composeNavigator = this.$composeNavigator;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                composeNavigator.getState().markTransitionComplete((NavBackStackEntry) it.next());
            }
            Map<String, Float> map = this.$zIndices;
            Transition<NavBackStackEntry> transition = this.$transition;
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<String, Float> entry : map.entrySet()) {
                if (!Intrinsics.areEqual(entry.getKey(), ((NavBackStackEntry) ((SnapshotMutableStateImpl) transition.targetState$delegate).getValue()).id)) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            Map<String, Float> map2 = this.$zIndices;
            Iterator it2 = linkedHashMap.entrySet().iterator();
            while (it2.hasNext()) {
                map2.remove(((Map.Entry) it2.next()).getKey());
            }
        }
        return Unit.INSTANCE;
    }
}
