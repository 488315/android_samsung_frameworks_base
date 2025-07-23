package androidx.navigation.compose;

import androidx.compose.ui.window.DialogProperties;
import androidx.navigation.FloatingWindow;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Navigator.Name("dialog")
/* loaded from: classes.dex */
public final class DialogNavigator extends Navigator {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ComposableSingletons$DialogNavigatorKt.INSTANCE.getClass();
        return new Destination(this, null, ComposableSingletons$DialogNavigatorKt.f24lambda1, 2, null);
    }

    @Override // androidx.navigation.Navigator
    public final void navigate(List list, NavOptions navOptions) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            getState().push((NavBackStackEntry) it.next());
        }
    }

    @Override // androidx.navigation.Navigator
    public final void popBackStack(NavBackStackEntry navBackStackEntry, boolean z) {
        getState().popWithTransition(navBackStackEntry, z);
        int indexOf = CollectionsKt___CollectionsKt.indexOf((Iterable) getState().transitionsInProgress.$$delegate_0.getValue(), navBackStackEntry);
        int i = 0;
        for (Object obj : (Iterable) getState().transitionsInProgress.$$delegate_0.getValue()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) obj;
            if (i > indexOf) {
                getState().markTransitionComplete(navBackStackEntry2);
            }
            i = i2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Destination extends NavDestination implements FloatingWindow {
        public final Function3 content;
        public final DialogProperties dialogProperties;

        public /* synthetic */ Destination(DialogNavigator dialogNavigator, DialogProperties dialogProperties, Function3 function3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(dialogNavigator, (i & 2) != 0 ? new DialogProperties(false, false, false, 7, (DefaultConstructorMarker) null) : dialogProperties, function3);
        }

        public Destination(DialogNavigator dialogNavigator, DialogProperties dialogProperties, Function3 function3) {
            super(dialogNavigator);
            this.dialogProperties = dialogProperties;
            this.content = function3;
        }
    }
}
