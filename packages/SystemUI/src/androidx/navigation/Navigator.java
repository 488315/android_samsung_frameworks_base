package androidx.navigation;

import androidx.navigation.NavController;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Navigator {
    public NavController.NavControllerNavigatorState _state;
    public boolean isAttached;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Extras {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Name {
        String value();
    }

    public abstract NavDestination createDestination();

    public final NavigatorState getState() {
        NavController.NavControllerNavigatorState navControllerNavigatorState = this._state;
        if (navControllerNavigatorState != null) {
            return navControllerNavigatorState;
        }
        throw new IllegalStateException("You cannot access the Navigator's state until the Navigator is attached");
    }

    public void navigate(List list, final NavOptions navOptions) {
        final Extras extras = null;
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filterNot(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: androidx.navigation.Navigator$navigate$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
                NavDestination navDestination = navBackStackEntry.destination;
                if (navDestination == null) {
                    navDestination = null;
                }
                if (navDestination != null) {
                    Navigator navigator = Navigator.this;
                    navBackStackEntry.getArguments();
                    NavDestination navigate = navigator.navigate(navDestination);
                    if (navigate != null) {
                        return navigate.equals(navDestination) ? navBackStackEntry : Navigator.this.getState().createBackStackEntry(navigate, navigate.addInDefaultArgs(navBackStackEntry.getArguments()));
                    }
                }
                return null;
            }
        }), new SequencesKt___SequencesKt$$ExternalSyntheticLambda1()));
        while (filteringSequence$iterator$1.hasNext()) {
            getState().push((NavBackStackEntry) filteringSequence$iterator$1.next());
        }
    }

    public boolean popBackStack() {
        return true;
    }

    public void popBackStack(NavBackStackEntry navBackStackEntry, boolean z) {
        List list = (List) getState().backStack.$$delegate_0.getValue();
        if (!list.contains(navBackStackEntry)) {
            throw new IllegalStateException(("popBackStack was called with " + navBackStackEntry + " which does not exist in back stack " + list).toString());
        }
        ListIterator listIterator = list.listIterator(list.size());
        NavBackStackEntry navBackStackEntry2 = null;
        while (popBackStack()) {
            navBackStackEntry2 = (NavBackStackEntry) listIterator.previous();
            if (Intrinsics.areEqual(navBackStackEntry2, navBackStackEntry)) {
                break;
            }
        }
        if (navBackStackEntry2 != null) {
            getState().pop(navBackStackEntry2, z);
        }
    }

    public NavDestination navigate(NavDestination navDestination) {
        return navDestination;
    }
}
