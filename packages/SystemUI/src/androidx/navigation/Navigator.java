package androidx.navigation;

import androidx.navigation.NavController;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda1;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes.dex */
public abstract class Navigator {
    public NavController.NavControllerNavigatorState _state;
    public boolean isAttached;

    public interface Extras {
    }

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
        FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filterNot(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: androidx.navigation.Navigator.navigate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
                NavDestination navDestination = navBackStackEntry.destination;
                if (navDestination == null) {
                    navDestination = null;
                }
                if (navDestination != null) {
                    Navigator navigator = Navigator.this;
                    navBackStackEntry.getArguments();
                    NavDestination navDestinationNavigate = navigator.navigate(navDestination);
                    if (navDestinationNavigate != null) {
                        return navDestinationNavigate.equals(navDestination) ? navBackStackEntry : Navigator.this.getState().createBackStackEntry(navDestinationNavigate, navDestinationNavigate.addInDefaultArgs(navBackStackEntry.getArguments()));
                    }
                }
                return null;
            }
        }), new SequencesKt___SequencesKt$$ExternalSyntheticLambda1()).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            getState().push((NavBackStackEntry) anonymousClass1.next());
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
