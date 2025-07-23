package androidx.navigation.compose;

import androidx.activity.BackEventCompat;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.State;
import androidx.navigation.NavBackStackEntry;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class NavHostKt$NavHost$25$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ComposeNavigator $composeNavigator;
    final /* synthetic */ State<List<NavBackStackEntry>> $currentBackStack$delegate;
    final /* synthetic */ MutableState<Boolean> $inPredictiveBack$delegate;
    final /* synthetic */ MutableFloatState $progress$delegate;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NavHostKt$NavHost$25$1(ComposeNavigator composeNavigator, MutableFloatState mutableFloatState, State<? extends List<NavBackStackEntry>> state, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$composeNavigator = composeNavigator;
        this.$progress$delegate = mutableFloatState;
        this.$currentBackStack$delegate = state;
        this.$inPredictiveBack$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NavHostKt$NavHost$25$1 navHostKt$NavHost$25$1 = new NavHostKt$NavHost$25$1(this.$composeNavigator, this.$progress$delegate, this.$currentBackStack$delegate, this.$inPredictiveBack$delegate, continuation);
        navHostKt$NavHost$25$1.L$0 = obj;
        return navHostKt$NavHost$25$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NavHostKt$NavHost$25$1) create((Flow) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        NavBackStackEntry navBackStackEntry;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flow = (Flow) this.L$0;
                ((SnapshotMutableFloatStateImpl) this.$progress$delegate).setFloatValue(0.0f);
                NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) CollectionsKt___CollectionsKt.lastOrNull((List) this.$currentBackStack$delegate.getValue());
                ComposeNavigator composeNavigator = this.$composeNavigator;
                navBackStackEntry2.getClass();
                composeNavigator.getState().prepareForTransition(navBackStackEntry2);
                this.$composeNavigator.getState().prepareForTransition((NavBackStackEntry) ((List) this.$currentBackStack$delegate.getValue()).get(((List) this.$currentBackStack$delegate.getValue()).size() - 2));
                final MutableState<Boolean> mutableState = this.$inPredictiveBack$delegate;
                final MutableFloatState mutableFloatState = this.$progress$delegate;
                FlowCollector flowCollector = new FlowCollector() { // from class: androidx.navigation.compose.NavHostKt$NavHost$25$1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        mutableState.setValue(Boolean.TRUE);
                        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(((BackEventCompat) obj2).progress);
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = navBackStackEntry2;
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                navBackStackEntry = navBackStackEntry2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                navBackStackEntry = (NavBackStackEntry) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            this.$inPredictiveBack$delegate.setValue(Boolean.FALSE);
            this.$composeNavigator.popBackStack(navBackStackEntry, false);
        } catch (CancellationException unused) {
            this.$inPredictiveBack$delegate.setValue(Boolean.FALSE);
        }
        return Unit.INSTANCE;
    }
}
