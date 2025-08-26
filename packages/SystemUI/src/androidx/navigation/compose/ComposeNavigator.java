package androidx.navigation.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorState;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

@Navigator.Name("composable")
/* loaded from: classes.dex */
public final class ComposeNavigator extends Navigator {
    public final MutableState isPop = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // androidx.navigation.Navigator
    public final NavDestination createDestination() {
        ComposableSingletons$ComposeNavigatorKt.INSTANCE.getClass();
        return new Destination(this, (Function4) ComposableSingletons$ComposeNavigatorKt.f23lambda1);
    }

    @Override // androidx.navigation.Navigator
    public final void navigate(List list, NavOptions navOptions) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) it.next();
            NavigatorState state = getState();
            StateFlowImpl stateFlowImpl = state._transitionsInProgress;
            Iterable iterable = (Iterable) stateFlowImpl.getValue();
            boolean z = iterable instanceof Collection;
            ReadonlyStateFlow readonlyStateFlow = state.backStack;
            if (!z || !((Collection) iterable).isEmpty()) {
                Iterator it2 = iterable.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (((NavBackStackEntry) it2.next()) == navBackStackEntry) {
                        Iterable iterable2 = (Iterable) readonlyStateFlow.$$delegate_0.getValue();
                        if (!(iterable2 instanceof Collection) || !((Collection) iterable2).isEmpty()) {
                            Iterator it3 = iterable2.iterator();
                            while (it3.hasNext()) {
                                if (((NavBackStackEntry) it3.next()) == navBackStackEntry) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) CollectionsKt___CollectionsKt.lastOrNull((List) readonlyStateFlow.$$delegate_0.getValue());
            if (navBackStackEntry2 != null) {
                stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), navBackStackEntry2));
            }
            stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), navBackStackEntry));
            state.push(navBackStackEntry);
        }
        ((SnapshotMutableStateImpl) this.isPop).setValue(Boolean.FALSE);
    }

    @Override // androidx.navigation.Navigator
    public final void popBackStack(NavBackStackEntry navBackStackEntry, boolean z) {
        getState().popWithTransition(navBackStackEntry, z);
        ((SnapshotMutableStateImpl) this.isPop).setValue(Boolean.TRUE);
    }

    public final class Destination extends NavDestination {
        public final Function4 content;

        public Destination(ComposeNavigator composeNavigator, Function4 function4) {
            super(composeNavigator);
            this.content = function4;
        }

        public Destination(ComposeNavigator composeNavigator, final Function3 function3) {
            this(composeNavigator, (Function4) new ComposableLambdaImpl(1587956030, true, new Function4() { // from class: androidx.navigation.compose.ComposeNavigator.Destination.1
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj2;
                    Composer composer = (Composer) obj3;
                    int iIntValue = ((Number) obj4).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.navigation.compose.ComposeNavigator.Destination.<init>.<anonymous> (ComposeNavigator.kt:107)");
                    }
                    function3.invoke(navBackStackEntry, composer, Integer.valueOf((iIntValue >> 3) & 14));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }));
        }
    }
}
