package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.NavDestination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.TakeWhileSequence;
import kotlin.sequences.TakeWhileSequence$iterator$1;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class NavController {
    public static final boolean deepLinkSaveState;
    public final StateFlowImpl _currentBackStack;
    public final SharedFlowImpl _currentBackStackEntryFlow;
    public NavGraph _graph;
    public final NavigatorProvider _navigatorProvider;
    public final StateFlowImpl _visibleEntries;
    public final Activity activity;
    public Lambda addToBackStackHandler;
    public final ArrayDeque backQueue;
    public final List backStackEntriesToDispatch;
    public final Map backStackMap;
    public final Map backStackStates;
    public Parcelable[] backStackToRestore;
    public final Map childToParentEntries;
    public final Context context;
    public final ReadonlySharedFlow currentBackStackEntryFlow;
    public boolean deepLinkHandled;
    public int dispatchReentrantCount;
    public final boolean enableOnBackPressedCallback;
    public final Map entrySavedState;
    public Lifecycle.State hostLifecycleState;
    public final NavController$$ExternalSyntheticLambda0 lifecycleObserver;
    public LifecycleOwner lifecycleOwner;
    public final Map navigatorState;
    public Bundle navigatorStateToRestore;
    public final NavController$onBackPressedCallback$1 onBackPressedCallback;
    public final CopyOnWriteArrayList onDestinationChangedListeners;
    public final Map parentToChildCount;
    public Function1 popFromBackStackHandler;
    public NavControllerViewModel viewModel;
    public final ReadonlyStateFlow visibleEntries;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NavControllerNavigatorState extends NavigatorState {
        public final Navigator navigator;

        public NavControllerNavigatorState(Navigator navigator) {
            this.navigator = navigator;
        }

        public final void addInternal(NavBackStackEntry navBackStackEntry) {
            super.push(navBackStackEntry);
        }

        @Override // androidx.navigation.NavigatorState
        public final NavBackStackEntry createBackStackEntry(NavDestination navDestination, Bundle bundle) {
            NavBackStackEntry.Companion companion = NavBackStackEntry.Companion;
            NavController navController = NavController.this;
            return NavBackStackEntry.Companion.create$default(companion, navController.context, navDestination, bundle, navController.getHostLifecycleState$navigation_runtime_release(), navController.viewModel);
        }

        @Override // androidx.navigation.NavigatorState
        public final void markTransitionComplete(NavBackStackEntry navBackStackEntry) {
            NavControllerViewModel navControllerViewModel;
            ViewModelStore viewModelStore;
            NavController navController = NavController.this;
            boolean areEqual = Intrinsics.areEqual(((LinkedHashMap) navController.entrySavedState).get(navBackStackEntry), Boolean.TRUE);
            super.markTransitionComplete(navBackStackEntry);
            navController.entrySavedState.remove(navBackStackEntry);
            ArrayDeque arrayDeque = navController.backQueue;
            boolean contains = arrayDeque.contains(navBackStackEntry);
            StateFlowImpl stateFlowImpl = navController._visibleEntries;
            if (contains) {
                if (this.isNavigating) {
                    return;
                }
                navController.updateBackStackLifecycle$navigation_runtime_release();
                navController._currentBackStack.updateState(null, new ArrayList(arrayDeque));
                stateFlowImpl.updateState(null, navController.populateVisibleEntries$navigation_runtime_release());
                return;
            }
            navController.unlinkChildFromParent$navigation_runtime_release(navBackStackEntry);
            if (navBackStackEntry._lifecycle.state.isAtLeast(Lifecycle.State.CREATED)) {
                navBackStackEntry.setMaxLifecycle(Lifecycle.State.DESTROYED);
            }
            String str = navBackStackEntry.id;
            if (arrayDeque == null || !arrayDeque.isEmpty()) {
                Iterator it = arrayDeque.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((NavBackStackEntry) it.next()).id, str)) {
                        break;
                    }
                }
            }
            if (!areEqual && (navControllerViewModel = navController.viewModel) != null && (viewModelStore = (ViewModelStore) navControllerViewModel.viewModelStores.remove(str)) != null) {
                viewModelStore.clear();
            }
            navController.updateBackStackLifecycle$navigation_runtime_release();
            stateFlowImpl.updateState(null, navController.populateVisibleEntries$navigation_runtime_release());
        }

        @Override // androidx.navigation.NavigatorState
        public final void pop(final NavBackStackEntry navBackStackEntry, final boolean z) {
            NavController navController = NavController.this;
            Navigator navigator = navController._navigatorProvider.getNavigator(navBackStackEntry.destination.navigatorName);
            navController.entrySavedState.put(navBackStackEntry, Boolean.valueOf(z));
            if (!navigator.equals(this.navigator)) {
                Object obj = ((LinkedHashMap) navController.navigatorState).get(navigator);
                obj.getClass();
                ((NavControllerNavigatorState) obj).pop(navBackStackEntry, z);
                return;
            }
            Function1 function1 = navController.popFromBackStackHandler;
            if (function1 != null) {
                ((NavController$executePopOperations$1) function1).mo779invoke(navBackStackEntry);
                super.pop(navBackStackEntry, z);
                return;
            }
            Function0 function0 = new Function0() { // from class: androidx.navigation.NavController$NavControllerNavigatorState$pop$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    super/*androidx.navigation.NavigatorState*/.pop(navBackStackEntry, z);
                    return Unit.INSTANCE;
                }
            };
            ArrayDeque arrayDeque = navController.backQueue;
            int indexOf = arrayDeque.indexOf(navBackStackEntry);
            if (indexOf < 0) {
                Log.i("NavController", "Ignoring pop of " + navBackStackEntry + " as it was not found on the current back stack");
                return;
            }
            int i = indexOf + 1;
            if (i != arrayDeque.size) {
                navController.popBackStackInternal(((NavBackStackEntry) arrayDeque.get(i)).destination.id, true, false);
            }
            NavController.popEntryFromBackStack$default(navController, navBackStackEntry);
            function0.invoke();
            navController.updateOnBackPressedCallbackEnabled();
            navController.dispatchOnDestinationChanged();
        }

        @Override // androidx.navigation.NavigatorState
        public final void prepareForTransition(NavBackStackEntry navBackStackEntry) {
            super.prepareForTransition(navBackStackEntry);
            if (!NavController.this.backQueue.contains(navBackStackEntry)) {
                throw new IllegalStateException("Cannot transition entry that is not in the back stack");
            }
            navBackStackEntry.setMaxLifecycle(Lifecycle.State.STARTED);
        }

        /* JADX WARN: Type inference failed for: r0v3, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
        @Override // androidx.navigation.NavigatorState
        public final void push(NavBackStackEntry navBackStackEntry) {
            NavController navController = NavController.this;
            Navigator navigator = navController._navigatorProvider.getNavigator(navBackStackEntry.destination.navigatorName);
            if (!navigator.equals(this.navigator)) {
                Object obj = ((LinkedHashMap) navController.navigatorState).get(navigator);
                if (obj == null) {
                    throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("NavigatorBackStack for "), navBackStackEntry.destination.navigatorName, " should already be created").toString());
                }
                ((NavControllerNavigatorState) obj).push(navBackStackEntry);
                return;
            }
            ?? r0 = navController.addToBackStackHandler;
            if (r0 != 0) {
                r0.mo779invoke(navBackStackEntry);
                super.push(navBackStackEntry);
            } else {
                Log.i("NavController", "Ignoring add of destination " + navBackStackEntry.destination + " outside of the call to navigate(). ");
            }
        }
    }

    static {
        new Companion(null);
        deepLinkSaveState = true;
    }

    /* JADX WARN: Type inference failed for: r4v13, types: [androidx.navigation.NavController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v14, types: [androidx.navigation.NavController$onBackPressedCallback$1] */
    public NavController(Context context) {
        Object obj;
        this.context = context;
        Iterator it = SequencesKt__SequencesKt.generateSequence(context, new Function1() { // from class: androidx.navigation.NavController$activity$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                Context context2 = (Context) obj2;
                if (context2 instanceof ContextWrapper) {
                    return ((ContextWrapper) context2).getBaseContext();
                }
                return null;
            }
        }).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((Context) obj) instanceof Activity) {
                    break;
                }
            }
        }
        this.activity = (Activity) obj;
        this.backQueue = new ArrayDeque();
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(emptyList);
        this._currentBackStack = MutableStateFlow;
        FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(emptyList);
        this._visibleEntries = MutableStateFlow2;
        this.visibleEntries = FlowKt.asStateFlow(MutableStateFlow2);
        this.childToParentEntries = new LinkedHashMap();
        this.parentToChildCount = new LinkedHashMap();
        this.backStackMap = new LinkedHashMap();
        this.backStackStates = new LinkedHashMap();
        this.onDestinationChangedListeners = new CopyOnWriteArrayList();
        this.hostLifecycleState = Lifecycle.State.INITIALIZED;
        this.lifecycleObserver = new LifecycleEventObserver() { // from class: androidx.navigation.NavController$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                boolean z = NavController.deepLinkSaveState;
                Lifecycle.State targetState = event.getTargetState();
                NavController navController = NavController.this;
                navController.hostLifecycleState = targetState;
                if (navController._graph != null) {
                    Iterator it2 = navController.backQueue.iterator();
                    while (it2.hasNext()) {
                        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) it2.next();
                        navBackStackEntry.getClass();
                        navBackStackEntry.hostLifecycleState = event.getTargetState();
                        navBackStackEntry.updateState();
                    }
                }
            }
        };
        this.onBackPressedCallback = new OnBackPressedCallback() { // from class: androidx.navigation.NavController$onBackPressedCallback$1
            {
                super(false);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackPressed() {
                NavController.this.popBackStack();
            }
        };
        this.enableOnBackPressedCallback = true;
        NavigatorProvider navigatorProvider = new NavigatorProvider();
        this._navigatorProvider = navigatorProvider;
        this.navigatorState = new LinkedHashMap();
        this.entrySavedState = new LinkedHashMap();
        navigatorProvider.addNavigator(new NavGraphNavigator(navigatorProvider));
        navigatorProvider.addNavigator(new ActivityNavigator(this.context));
        this.backStackEntriesToDispatch = new ArrayList();
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.navigation.NavController$navInflater$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NavController navController = NavController.this;
                boolean z = NavController.deepLinkSaveState;
                navController.getClass();
                NavController navController2 = NavController.this;
                return new NavInflater(navController2.context, navController2._navigatorProvider);
            }
        });
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, BufferOverflow.DROP_OLDEST, 2);
        this._currentBackStackEntryFlow = MutableSharedFlow$default;
        this.currentBackStackEntryFlow = FlowKt.asSharedFlow(MutableSharedFlow$default);
    }

    public static NavDestination findDestinationComprehensive(NavDestination navDestination, int i, boolean z) {
        NavGraph navGraph;
        if (navDestination.id == i) {
            return navDestination;
        }
        if (navDestination instanceof NavGraph) {
            navGraph = (NavGraph) navDestination;
        } else {
            navGraph = navDestination.parent;
            navGraph.getClass();
        }
        return navGraph.findNodeComprehensive(i, navGraph, z);
    }

    public static void navigate$default(NavController navController, String str, NavOptions navOptions, int i) {
        if ((i & 2) != 0) {
            navOptions = null;
        }
        navController.getClass();
        NavDeepLinkRequest.Builder.Companion companion = NavDeepLinkRequest.Builder.Companion;
        NavDestination.Companion.getClass();
        Uri parse = Uri.parse(str != null ? "android-app://androidx.navigation/".concat(str) : "");
        companion.getClass();
        new NavDeepLinkRequest.Builder(null).uri = parse;
        NavDeepLinkRequest navDeepLinkRequest = new NavDeepLinkRequest(parse, null, null);
        NavGraph navGraph = navController._graph;
        if (navGraph == null) {
            throw new IllegalArgumentException(("Cannot navigate to " + navDeepLinkRequest + ". Navigation graph has not been set for NavController " + navController + '.').toString());
        }
        NavDestination.DeepLinkMatch matchDeepLink = navGraph.matchDeepLink(navDeepLinkRequest);
        if (matchDeepLink == null) {
            throw new IllegalArgumentException("Navigation destination that matches request " + navDeepLinkRequest + " cannot be found in the navigation graph " + navController._graph);
        }
        Bundle addInDefaultArgs = matchDeepLink.destination.addInDefaultArgs(matchDeepLink.matchingArgs);
        if (addInDefaultArgs == null) {
            addInDefaultArgs = new Bundle();
        }
        NavDestination navDestination = matchDeepLink.destination;
        Intent intent = new Intent();
        intent.setDataAndType(navDeepLinkRequest.uri, navDeepLinkRequest.mimeType);
        intent.setAction(navDeepLinkRequest.action);
        addInDefaultArgs.putParcelable("android-support-nav:controller:deepLinkIntent", intent);
        navController.navigate(navDestination, addInDefaultArgs, navOptions);
    }

    public static /* synthetic */ void popEntryFromBackStack$default(NavController navController, NavBackStackEntry navBackStackEntry) {
        navController.popEntryFromBackStack(navBackStackEntry, false, new ArrayDeque());
    }

    public final void addEntryToBackStack(NavDestination navDestination, Bundle bundle, NavBackStackEntry navBackStackEntry, List list) {
        Bundle bundle2;
        NavGraph navGraph;
        Object obj;
        Object obj2;
        NavDestination navDestination2 = navBackStackEntry.destination;
        boolean z = navDestination2 instanceof FloatingWindow;
        int i = 0;
        ArrayDeque arrayDeque = this.backQueue;
        if (!z) {
            while (!arrayDeque.isEmpty() && (((NavBackStackEntry) arrayDeque.last()).destination instanceof FloatingWindow) && popBackStackInternal(((NavBackStackEntry) arrayDeque.last()).destination.id, true, false)) {
            }
        }
        ArrayDeque arrayDeque2 = new ArrayDeque();
        Object obj3 = null;
        if (navDestination instanceof NavGraph) {
            NavDestination navDestination3 = navDestination2;
            while (true) {
                navDestination3.getClass();
                NavGraph navGraph2 = navDestination3.parent;
                if (navGraph2 != null) {
                    ListIterator listIterator = list.listIterator(list.size());
                    while (true) {
                        if (!listIterator.hasPrevious()) {
                            obj2 = null;
                            break;
                        } else {
                            obj2 = listIterator.previous();
                            if (Intrinsics.areEqual(((NavBackStackEntry) obj2).destination, navGraph2)) {
                                break;
                            }
                        }
                    }
                    NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) obj2;
                    if (navBackStackEntry2 == null) {
                        bundle2 = bundle;
                        navBackStackEntry2 = NavBackStackEntry.Companion.create$default(NavBackStackEntry.Companion, this.context, navGraph2, bundle2, getHostLifecycleState$navigation_runtime_release(), this.viewModel);
                    } else {
                        bundle2 = bundle;
                    }
                    arrayDeque2.addFirst(navBackStackEntry2);
                    if (!arrayDeque.isEmpty() && ((NavBackStackEntry) arrayDeque.last()).destination == navGraph2) {
                        popEntryFromBackStack$default(this, (NavBackStackEntry) arrayDeque.last());
                    }
                } else {
                    bundle2 = bundle;
                }
                if (navGraph2 == null || navGraph2 == navDestination) {
                    break;
                } else {
                    navDestination3 = navGraph2;
                }
            }
        } else {
            bundle2 = bundle;
        }
        NavDestination navDestination4 = arrayDeque2.isEmpty() ? navDestination2 : ((NavBackStackEntry) arrayDeque2.first()).destination;
        while (navDestination4 != null && findDestination(navDestination4.id) != navDestination4) {
            NavGraph navGraph3 = navDestination4.parent;
            if (navGraph3 != null) {
                Bundle bundle3 = (bundle2 == null || !bundle2.isEmpty()) ? bundle2 : null;
                ListIterator listIterator2 = list.listIterator(list.size());
                while (true) {
                    if (!listIterator2.hasPrevious()) {
                        obj = null;
                        break;
                    } else {
                        obj = listIterator2.previous();
                        if (Intrinsics.areEqual(((NavBackStackEntry) obj).destination, navGraph3)) {
                            break;
                        }
                    }
                }
                NavBackStackEntry navBackStackEntry3 = (NavBackStackEntry) obj;
                if (navBackStackEntry3 == null) {
                    navGraph = navGraph3;
                    navBackStackEntry3 = NavBackStackEntry.Companion.create$default(NavBackStackEntry.Companion, this.context, navGraph, navGraph3.addInDefaultArgs(bundle3), getHostLifecycleState$navigation_runtime_release(), this.viewModel);
                } else {
                    navGraph = navGraph3;
                }
                arrayDeque2.addFirst(navBackStackEntry3);
            } else {
                navGraph = navGraph3;
            }
            navDestination4 = navGraph;
        }
        if (!arrayDeque2.isEmpty()) {
            navDestination2 = ((NavBackStackEntry) arrayDeque2.first()).destination;
        }
        while (!arrayDeque.isEmpty() && (((NavBackStackEntry) arrayDeque.last()).destination instanceof NavGraph) && ((NavGraph) ((NavBackStackEntry) arrayDeque.last()).destination).nodes.get(navDestination2.id) == null) {
            popEntryFromBackStack$default(this, (NavBackStackEntry) arrayDeque.last());
        }
        NavBackStackEntry navBackStackEntry4 = (NavBackStackEntry) arrayDeque.firstOrNull();
        if (navBackStackEntry4 == null) {
            navBackStackEntry4 = (NavBackStackEntry) arrayDeque2.firstOrNull();
        }
        if (!Intrinsics.areEqual(navBackStackEntry4 != null ? navBackStackEntry4.destination : null, this._graph)) {
            ListIterator listIterator3 = list.listIterator(list.size());
            while (true) {
                if (!listIterator3.hasPrevious()) {
                    break;
                }
                Object previous = listIterator3.previous();
                NavDestination navDestination5 = ((NavBackStackEntry) previous).destination;
                NavGraph navGraph4 = this._graph;
                navGraph4.getClass();
                if (Intrinsics.areEqual(navDestination5, navGraph4)) {
                    obj3 = previous;
                    break;
                }
            }
            NavBackStackEntry navBackStackEntry5 = (NavBackStackEntry) obj3;
            if (navBackStackEntry5 == null) {
                NavBackStackEntry.Companion companion = NavBackStackEntry.Companion;
                Context context = this.context;
                NavGraph navGraph5 = this._graph;
                navGraph5.getClass();
                NavGraph navGraph6 = this._graph;
                navGraph6.getClass();
                navBackStackEntry5 = NavBackStackEntry.Companion.create$default(companion, context, navGraph5, navGraph6.addInDefaultArgs(bundle2), getHostLifecycleState$navigation_runtime_release(), this.viewModel);
            }
            arrayDeque2.addFirst(navBackStackEntry5);
        }
        Iterator it = arrayDeque2.iterator();
        while (it.hasNext()) {
            NavBackStackEntry navBackStackEntry6 = (NavBackStackEntry) it.next();
            Object obj4 = ((LinkedHashMap) this.navigatorState).get(this._navigatorProvider.getNavigator(navBackStackEntry6.destination.navigatorName));
            if (obj4 == null) {
                throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("NavigatorBackStack for "), navDestination.navigatorName, " should already be created").toString());
            }
            ((NavControllerNavigatorState) obj4).addInternal(navBackStackEntry6);
        }
        arrayDeque.addAll(arrayDeque2);
        arrayDeque.addLast(navBackStackEntry);
        ArrayList arrayList = (ArrayList) CollectionsKt___CollectionsKt.plus(arrayDeque2, navBackStackEntry);
        int size = arrayList.size();
        while (i < size) {
            Object obj5 = arrayList.get(i);
            i++;
            NavBackStackEntry navBackStackEntry7 = (NavBackStackEntry) obj5;
            NavGraph navGraph7 = navBackStackEntry7.destination.parent;
            if (navGraph7 != null) {
                linkChildToParent(navBackStackEntry7, getBackStackEntry(navGraph7.id));
            }
        }
    }

    public final boolean dispatchOnDestinationChanged() {
        ArrayDeque arrayDeque;
        while (true) {
            arrayDeque = this.backQueue;
            if (arrayDeque.isEmpty() || !(((NavBackStackEntry) arrayDeque.last()).destination instanceof NavGraph)) {
                break;
            }
            popEntryFromBackStack$default(this, (NavBackStackEntry) arrayDeque.last());
        }
        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) arrayDeque.lastOrNull();
        if (navBackStackEntry != null) {
            ((ArrayList) this.backStackEntriesToDispatch).add(navBackStackEntry);
        }
        this.dispatchReentrantCount++;
        updateBackStackLifecycle$navigation_runtime_release();
        int i = this.dispatchReentrantCount - 1;
        this.dispatchReentrantCount = i;
        if (i == 0) {
            ArrayList arrayList = new ArrayList(this.backStackEntriesToDispatch);
            ((ArrayList) this.backStackEntriesToDispatch).clear();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) obj;
                Iterator it = this.onDestinationChangedListeners.iterator();
                if (it.hasNext()) {
                    if (it.next() != null) {
                        throw new ClassCastException();
                    }
                    NavDestination navDestination = navBackStackEntry2.destination;
                    navBackStackEntry2.getArguments();
                    throw null;
                }
                this._currentBackStackEntryFlow.tryEmit(navBackStackEntry2);
            }
            this._currentBackStack.updateState(null, new ArrayList(arrayDeque));
            this._visibleEntries.updateState(null, populateVisibleEntries$navigation_runtime_release());
        }
        return navBackStackEntry != null;
    }

    public final boolean executePopOperations(List list, NavDestination navDestination, boolean z, boolean z2) {
        final NavController navController;
        boolean z3;
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ArrayDeque arrayDeque = new ArrayDeque();
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                navController = this;
                z3 = z2;
                break;
            }
            int i2 = i + 1;
            Navigator navigator = (Navigator) arrayList.get(i);
            Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) this.backQueue.last();
            navController = this;
            z3 = z2;
            navController.popFromBackStackHandler = new NavController$executePopOperations$1(ref$BooleanRef2, ref$BooleanRef, navController, z3, arrayDeque);
            navigator.popBackStack(navBackStackEntry, z3);
            navController.popFromBackStackHandler = null;
            if (!ref$BooleanRef2.element) {
                break;
            }
            this = navController;
            z2 = z3;
            i = i2;
        }
        if (z3) {
            if (!z) {
                TakeWhileSequence$iterator$1 takeWhileSequence$iterator$1 = new TakeWhileSequence$iterator$1(new TakeWhileSequence(SequencesKt__SequencesKt.generateSequence(navDestination, new Function1() { // from class: androidx.navigation.NavController$executePopOperations$2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        NavDestination navDestination2 = (NavDestination) obj;
                        NavGraph navGraph = navDestination2.parent;
                        if (navGraph == null || navGraph.startDestId != navDestination2.id) {
                            return null;
                        }
                        return navGraph;
                    }
                }), new Function1() { // from class: androidx.navigation.NavController$executePopOperations$3
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        return Boolean.valueOf(!NavController.this.backStackMap.containsKey(Integer.valueOf(((NavDestination) obj).id)));
                    }
                }));
                while (takeWhileSequence$iterator$1.hasNext()) {
                    NavDestination navDestination2 = (NavDestination) takeWhileSequence$iterator$1.next();
                    Map map = navController.backStackMap;
                    Integer valueOf = Integer.valueOf(navDestination2.id);
                    NavBackStackEntryState navBackStackEntryState = (NavBackStackEntryState) arrayDeque.firstOrNull();
                    map.put(valueOf, navBackStackEntryState != null ? navBackStackEntryState.id : null);
                }
            }
            if (!arrayDeque.isEmpty()) {
                NavBackStackEntryState navBackStackEntryState2 = (NavBackStackEntryState) arrayDeque.first();
                TakeWhileSequence$iterator$1 takeWhileSequence$iterator$12 = new TakeWhileSequence$iterator$1(new TakeWhileSequence(SequencesKt__SequencesKt.generateSequence(navController.findDestination(navBackStackEntryState2.destinationId), new Function1() { // from class: androidx.navigation.NavController$executePopOperations$5
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        NavDestination navDestination3 = (NavDestination) obj;
                        NavGraph navGraph = navDestination3.parent;
                        if (navGraph == null || navGraph.startDestId != navDestination3.id) {
                            return null;
                        }
                        return navGraph;
                    }
                }), new Function1() { // from class: androidx.navigation.NavController$executePopOperations$6
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        return Boolean.valueOf(!NavController.this.backStackMap.containsKey(Integer.valueOf(((NavDestination) obj).id)));
                    }
                }));
                while (takeWhileSequence$iterator$12.hasNext()) {
                    navController.backStackMap.put(Integer.valueOf(((NavDestination) takeWhileSequence$iterator$12.next()).id), navBackStackEntryState2.id);
                }
                if (((LinkedHashMap) navController.backStackMap).values().contains(navBackStackEntryState2.id)) {
                    navController.backStackStates.put(navBackStackEntryState2.id, arrayDeque);
                }
            }
        }
        navController.updateOnBackPressedCallbackEnabled();
        return ref$BooleanRef.element;
    }

    public final NavDestination findDestination(int i) {
        NavDestination navDestination;
        NavGraph navGraph = this._graph;
        if (navGraph == null) {
            return null;
        }
        if (navGraph.id == i) {
            return navGraph;
        }
        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) this.backQueue.lastOrNull();
        if (navBackStackEntry == null || (navDestination = navBackStackEntry.destination) == null) {
            navDestination = this._graph;
            navDestination.getClass();
        }
        return findDestinationComprehensive(navDestination, i, false);
    }

    public final NavBackStackEntry getBackStackEntry(int i) {
        Object obj;
        ArrayDeque arrayDeque = this.backQueue;
        ListIterator listIterator = arrayDeque.listIterator(arrayDeque.getSize());
        while (true) {
            if (!listIterator.hasPrevious()) {
                obj = null;
                break;
            }
            obj = listIterator.previous();
            if (((NavBackStackEntry) obj).destination.id == i) {
                break;
            }
        }
        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
        if (navBackStackEntry != null) {
            return navBackStackEntry;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "No destination with ID ", " is on the NavController's back stack. The current destination is ");
        NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) arrayDeque.lastOrNull();
        m.append(navBackStackEntry2 != null ? navBackStackEntry2.destination : null);
        throw new IllegalArgumentException(m.toString().toString());
    }

    public final Lifecycle.State getHostLifecycleState$navigation_runtime_release() {
        return this.lifecycleOwner == null ? Lifecycle.State.CREATED : this.hostLifecycleState;
    }

    public final void linkChildToParent(NavBackStackEntry navBackStackEntry, NavBackStackEntry navBackStackEntry2) {
        this.childToParentEntries.put(navBackStackEntry, navBackStackEntry2);
        if (((LinkedHashMap) this.parentToChildCount).get(navBackStackEntry2) == null) {
            this.parentToChildCount.put(navBackStackEntry2, new AtomicInteger(0));
        }
        Object obj = ((LinkedHashMap) this.parentToChildCount).get(navBackStackEntry2);
        obj.getClass();
        ((AtomicInteger) obj).incrementAndGet();
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0172 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0278 A[LOOP:1: B:20:0x0272->B:22:0x0278, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x017a A[LOOP:3: B:53:0x0173->B:55:0x017a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x023c  */
    /* JADX WARN: Type inference failed for: r5v1, types: [androidx.navigation.NavController$navigate$5, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void navigate(final androidx.navigation.NavDestination r18, android.os.Bundle r19, androidx.navigation.NavOptions r20) {
        /*
            Method dump skipped, instructions count: 659
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.navigate(androidx.navigation.NavDestination, android.os.Bundle, androidx.navigation.NavOptions):void");
    }

    public final boolean popBackStack() {
        ArrayDeque arrayDeque = this.backQueue;
        if (!arrayDeque.isEmpty()) {
            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) arrayDeque.lastOrNull();
            NavDestination navDestination = navBackStackEntry != null ? navBackStackEntry.destination : null;
            navDestination.getClass();
            if (popBackStackInternal(navDestination.id, true, false) && dispatchOnDestinationChanged()) {
                return true;
            }
        }
        return false;
    }

    public final boolean popBackStackInternal(int i, boolean z, boolean z2) {
        NavDestination navDestination;
        ArrayDeque arrayDeque = this.backQueue;
        if (arrayDeque.isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = CollectionsKt___CollectionsKt.reversed(arrayDeque).iterator();
        while (true) {
            if (!it.hasNext()) {
                navDestination = null;
                break;
            }
            navDestination = ((NavBackStackEntry) it.next()).destination;
            Navigator navigator = this._navigatorProvider.getNavigator(navDestination.navigatorName);
            if (z || navDestination.id != i) {
                arrayList.add(navigator);
            }
            if (navDestination.id == i) {
                break;
            }
        }
        if (navDestination != null) {
            return executePopOperations(arrayList, navDestination, z, z2);
        }
        NavDestination.Companion companion = NavDestination.Companion;
        Context context = this.context;
        companion.getClass();
        Log.i("NavController", "Ignoring popBackStack to destination " + NavDestination.Companion.getDisplayName(i, context) + " as it was not found on the current back stack");
        return false;
    }

    public final void popEntryFromBackStack(NavBackStackEntry navBackStackEntry, boolean z, ArrayDeque arrayDeque) {
        NavControllerViewModel navControllerViewModel;
        ViewModelStore viewModelStore;
        ReadonlyStateFlow readonlyStateFlow;
        Set set;
        ArrayDeque arrayDeque2 = this.backQueue;
        NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) arrayDeque2.last();
        if (!Intrinsics.areEqual(navBackStackEntry2, navBackStackEntry)) {
            throw new IllegalStateException(("Attempted to pop " + navBackStackEntry.destination + ", which is not the top of the back stack (" + navBackStackEntry2.destination + ')').toString());
        }
        arrayDeque2.removeLast();
        NavControllerNavigatorState navControllerNavigatorState = (NavControllerNavigatorState) ((LinkedHashMap) this.navigatorState).get(this._navigatorProvider.getNavigator(navBackStackEntry2.destination.navigatorName));
        boolean z2 = true;
        if ((navControllerNavigatorState == null || (readonlyStateFlow = navControllerNavigatorState.transitionsInProgress) == null || (set = (Set) readonlyStateFlow.$$delegate_0.getValue()) == null || !set.contains(navBackStackEntry2)) && !this.parentToChildCount.containsKey(navBackStackEntry2)) {
            z2 = false;
        }
        Lifecycle.State state = navBackStackEntry2._lifecycle.state;
        Lifecycle.State state2 = Lifecycle.State.CREATED;
        if (state.isAtLeast(state2)) {
            if (z) {
                navBackStackEntry2.setMaxLifecycle(state2);
                arrayDeque.addFirst(new NavBackStackEntryState(navBackStackEntry2));
            }
            if (z2) {
                navBackStackEntry2.setMaxLifecycle(state2);
            } else {
                navBackStackEntry2.setMaxLifecycle(Lifecycle.State.DESTROYED);
                unlinkChildFromParent$navigation_runtime_release(navBackStackEntry2);
            }
        }
        if (z || z2 || (navControllerViewModel = this.viewModel) == null || (viewModelStore = (ViewModelStore) navControllerViewModel.viewModelStores.remove(navBackStackEntry2.id)) == null) {
            return;
        }
        viewModelStore.clear();
    }

    public final List populateVisibleEntries$navigation_runtime_release() {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((LinkedHashMap) this.navigatorState).values().iterator();
        while (it.hasNext()) {
            Iterable iterable = (Iterable) ((NavControllerNavigatorState) it.next()).transitionsInProgress.$$delegate_0.getValue();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : iterable) {
                NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
                if (!arrayList.contains(navBackStackEntry) && !navBackStackEntry.maxLifecycle.isAtLeast(Lifecycle.State.STARTED)) {
                    arrayList2.add(obj);
                }
            }
            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, arrayList);
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = this.backQueue.iterator();
        while (it2.hasNext()) {
            Object next = it2.next();
            NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) next;
            if (!arrayList.contains(navBackStackEntry2) && navBackStackEntry2.maxLifecycle.isAtLeast(Lifecycle.State.STARTED)) {
                arrayList3.add(next);
            }
        }
        CollectionsKt__MutableCollectionsKt.addAll(arrayList3, arrayList);
        ArrayList arrayList4 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            if (!(((NavBackStackEntry) obj2).destination instanceof NavGraph)) {
                arrayList4.add(obj2);
            }
        }
        return arrayList4;
    }

    /* JADX WARN: Type inference failed for: r0v13, types: [androidx.navigation.NavController$executeRestoreState$3, kotlin.jvm.internal.Lambda] */
    public final boolean restoreStateInternal(int i, NavOptions navOptions, final Bundle bundle) {
        NavDestination navDestination;
        NavBackStackEntry navBackStackEntry;
        NavDestination navDestination2;
        Bundle bundle2;
        int i2 = 0;
        if (!this.backStackMap.containsKey(Integer.valueOf(i))) {
            return false;
        }
        final String str = (String) ((LinkedHashMap) this.backStackMap).get(Integer.valueOf(i));
        CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(((LinkedHashMap) this.backStackMap).values(), new Function1() { // from class: androidx.navigation.NavController$restoreStateInternal$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Boolean.valueOf(Intrinsics.areEqual((String) obj, str));
            }
        }, true);
        ArrayDeque arrayDeque = (ArrayDeque) TypeIntrinsics.asMutableMap(this.backStackStates).remove(str);
        final ArrayList arrayList = new ArrayList();
        NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) this.backQueue.lastOrNull();
        if ((navBackStackEntry2 == null || (navDestination = navBackStackEntry2.destination) == null) && (navDestination = this._graph) == null) {
            throw new IllegalStateException("You must call setGraph() before calling getGraph()");
        }
        if (arrayDeque != null) {
            Iterator it = arrayDeque.iterator();
            while (it.hasNext()) {
                NavBackStackEntryState navBackStackEntryState = (NavBackStackEntryState) it.next();
                NavDestination findDestinationComprehensive = findDestinationComprehensive(navDestination, navBackStackEntryState.destinationId, true);
                if (findDestinationComprehensive == null) {
                    NavDestination.Companion companion = NavDestination.Companion;
                    Context context = this.context;
                    int i3 = navBackStackEntryState.destinationId;
                    companion.getClass();
                    throw new IllegalStateException(("Restore State failed: destination " + NavDestination.Companion.getDisplayName(i3, context) + " cannot be found from the current destination " + navDestination).toString());
                }
                Context context2 = this.context;
                Lifecycle.State hostLifecycleState$navigation_runtime_release = getHostLifecycleState$navigation_runtime_release();
                NavControllerViewModel navControllerViewModel = this.viewModel;
                Bundle bundle3 = navBackStackEntryState.args;
                if (bundle3 != null) {
                    bundle3.setClassLoader(context2.getClassLoader());
                    bundle2 = bundle3;
                } else {
                    bundle2 = null;
                }
                NavBackStackEntry.Companion companion2 = NavBackStackEntry.Companion;
                String str2 = navBackStackEntryState.id;
                Bundle bundle4 = navBackStackEntryState.savedState;
                companion2.getClass();
                arrayList.add(new NavBackStackEntry(context2, findDestinationComprehensive, bundle2, hostLifecycleState$navigation_runtime_release, navControllerViewModel, str2, bundle4, null));
                navDestination = findDestinationComprehensive;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            if (!(((NavBackStackEntry) obj).destination instanceof NavGraph)) {
                arrayList3.add(obj);
            }
        }
        int size2 = arrayList3.size();
        int i5 = 0;
        while (i5 < size2) {
            Object obj2 = arrayList3.get(i5);
            i5++;
            NavBackStackEntry navBackStackEntry3 = (NavBackStackEntry) obj2;
            List list = (List) CollectionsKt___CollectionsKt.lastOrNull(arrayList2);
            if (Intrinsics.areEqual((list == null || (navBackStackEntry = (NavBackStackEntry) CollectionsKt___CollectionsKt.last(list)) == null || (navDestination2 = navBackStackEntry.destination) == null) ? null : navDestination2.navigatorName, navBackStackEntry3.destination.navigatorName)) {
                list.add(navBackStackEntry3);
            } else {
                arrayList2.add(CollectionsKt__CollectionsKt.mutableListOf(navBackStackEntry3));
            }
        }
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        int size3 = arrayList2.size();
        while (i2 < size3) {
            int i6 = i2 + 1;
            List list2 = (List) arrayList2.get(i2);
            Navigator navigator = this._navigatorProvider.getNavigator(((NavBackStackEntry) CollectionsKt___CollectionsKt.first(list2)).destination.navigatorName);
            final Ref$IntRef ref$IntRef = new Ref$IntRef();
            final Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
            this.addToBackStackHandler = new Function1() { // from class: androidx.navigation.NavController$executeRestoreState$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj3) {
                    List<NavBackStackEntry> list3;
                    NavBackStackEntry navBackStackEntry4 = (NavBackStackEntry) obj3;
                    Ref$BooleanRef.this.element = true;
                    int indexOf = arrayList.indexOf(navBackStackEntry4);
                    if (indexOf != -1) {
                        int i7 = indexOf + 1;
                        list3 = arrayList.subList(ref$IntRef.element, i7);
                        ref$IntRef.element = i7;
                    } else {
                        list3 = EmptyList.INSTANCE;
                    }
                    NavController navController = this;
                    NavDestination navDestination3 = navBackStackEntry4.destination;
                    Bundle bundle5 = bundle;
                    boolean z = NavController.deepLinkSaveState;
                    navController.addEntryToBackStack(navDestination3, bundle5, navBackStackEntry4, list3);
                    return Unit.INSTANCE;
                }
            };
            navigator.navigate(list2, navOptions);
            this.addToBackStackHandler = null;
            ref$BooleanRef = ref$BooleanRef2;
            i2 = i6;
        }
        return ref$BooleanRef.element;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02ce A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setGraph(androidx.navigation.NavGraph r22) {
        /*
            Method dump skipped, instructions count: 1254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.setGraph(androidx.navigation.NavGraph):void");
    }

    public final void unlinkChildFromParent$navigation_runtime_release(NavBackStackEntry navBackStackEntry) {
        NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) this.childToParentEntries.remove(navBackStackEntry);
        if (navBackStackEntry2 == null) {
            return;
        }
        AtomicInteger atomicInteger = (AtomicInteger) ((LinkedHashMap) this.parentToChildCount).get(navBackStackEntry2);
        Integer valueOf = atomicInteger != null ? Integer.valueOf(atomicInteger.decrementAndGet()) : null;
        if (valueOf != null && valueOf.intValue() == 0) {
            NavControllerNavigatorState navControllerNavigatorState = (NavControllerNavigatorState) ((LinkedHashMap) this.navigatorState).get(this._navigatorProvider.getNavigator(navBackStackEntry2.destination.navigatorName));
            if (navControllerNavigatorState != null) {
                navControllerNavigatorState.markTransitionComplete(navBackStackEntry2);
            }
            this.parentToChildCount.remove(navBackStackEntry2);
        }
    }

    public final void updateBackStackLifecycle$navigation_runtime_release() {
        AtomicInteger atomicInteger;
        ReadonlyStateFlow readonlyStateFlow;
        Set set;
        ArrayList arrayList = new ArrayList(this.backQueue);
        if (arrayList.isEmpty()) {
            return;
        }
        NavDestination navDestination = ((NavBackStackEntry) CollectionsKt___CollectionsKt.last(arrayList)).destination;
        ArrayList arrayList2 = new ArrayList();
        if (navDestination instanceof FloatingWindow) {
            Iterator it = CollectionsKt___CollectionsKt.reversed(arrayList).iterator();
            while (it.hasNext()) {
                NavDestination navDestination2 = ((NavBackStackEntry) it.next()).destination;
                arrayList2.add(navDestination2);
                if (!(navDestination2 instanceof FloatingWindow) && !(navDestination2 instanceof NavGraph)) {
                    break;
                }
            }
        }
        HashMap hashMap = new HashMap();
        Iterator it2 = CollectionsKt___CollectionsKt.reversed(arrayList).iterator();
        while (true) {
            int i = 0;
            if (!it2.hasNext()) {
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
                    Lifecycle.State state = (Lifecycle.State) hashMap.get(navBackStackEntry);
                    if (state != null) {
                        navBackStackEntry.setMaxLifecycle(state);
                    } else {
                        navBackStackEntry.updateState();
                    }
                }
                return;
            }
            NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) it2.next();
            Lifecycle.State state2 = navBackStackEntry2.maxLifecycle;
            NavDestination navDestination3 = navBackStackEntry2.destination;
            if (navDestination != null && navDestination3.id == navDestination.id) {
                Lifecycle.State state3 = Lifecycle.State.RESUMED;
                if (state2 != state3) {
                    NavControllerNavigatorState navControllerNavigatorState = (NavControllerNavigatorState) ((LinkedHashMap) this.navigatorState).get(this._navigatorProvider.getNavigator(navDestination3.navigatorName));
                    if (Intrinsics.areEqual((navControllerNavigatorState == null || (readonlyStateFlow = navControllerNavigatorState.transitionsInProgress) == null || (set = (Set) readonlyStateFlow.$$delegate_0.getValue()) == null) ? null : Boolean.valueOf(set.contains(navBackStackEntry2)), Boolean.TRUE) || ((atomicInteger = (AtomicInteger) ((LinkedHashMap) this.parentToChildCount).get(navBackStackEntry2)) != null && atomicInteger.get() == 0)) {
                        hashMap.put(navBackStackEntry2, Lifecycle.State.STARTED);
                    } else {
                        hashMap.put(navBackStackEntry2, state3);
                    }
                }
                NavDestination navDestination4 = (NavDestination) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList2);
                if (navDestination4 != null && navDestination4.id == navDestination3.id) {
                    if (arrayList2.isEmpty()) {
                        throw new NoSuchElementException("List is empty.");
                    }
                    arrayList2.remove(0);
                }
                navDestination = navDestination.parent;
            } else if (arrayList2.isEmpty() || navDestination3.id != ((NavDestination) CollectionsKt___CollectionsKt.first((List) arrayList2)).id) {
                navBackStackEntry2.setMaxLifecycle(Lifecycle.State.CREATED);
            } else {
                if (arrayList2.isEmpty()) {
                    throw new NoSuchElementException("List is empty.");
                }
                NavDestination navDestination5 = (NavDestination) arrayList2.remove(0);
                if (state2 == Lifecycle.State.RESUMED) {
                    navBackStackEntry2.setMaxLifecycle(Lifecycle.State.STARTED);
                } else {
                    Lifecycle.State state4 = Lifecycle.State.STARTED;
                    if (state2 != state4) {
                        hashMap.put(navBackStackEntry2, state4);
                    }
                }
                NavGraph navGraph = navDestination5.parent;
                if (navGraph != null && !arrayList2.contains(navGraph)) {
                    arrayList2.add(navGraph);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.FunctionReferenceImpl] */
    public final void updateOnBackPressedCallbackEnabled() {
        int i;
        boolean z = false;
        if (this.enableOnBackPressedCallback) {
            ArrayDeque arrayDeque = this.backQueue;
            if (arrayDeque == null || !arrayDeque.isEmpty()) {
                Iterator it = arrayDeque.iterator();
                i = 0;
                while (it.hasNext()) {
                    if (!(((NavBackStackEntry) it.next()).destination instanceof NavGraph) && (i = i + 1) < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        throw null;
                    }
                }
            } else {
                i = 0;
            }
            if (i > 1) {
                z = true;
            }
        }
        NavController$onBackPressedCallback$1 navController$onBackPressedCallback$1 = this.onBackPressedCallback;
        navController$onBackPressedCallback$1.isEnabled = z;
        ?? r4 = navController$onBackPressedCallback$1.enabledChangedCallback;
        if (r4 != 0) {
            r4.invoke();
        }
    }

    public final boolean popBackStackInternal(String str, boolean z, boolean z2) {
        Object obj;
        ArrayDeque arrayDeque = this.backQueue;
        if (arrayDeque.isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        ListIterator listIterator = arrayDeque.listIterator(arrayDeque.getSize());
        while (true) {
            if (!listIterator.hasPrevious()) {
                obj = null;
                break;
            }
            obj = listIterator.previous();
            NavBackStackEntry navBackStackEntry = (NavBackStackEntry) obj;
            NavDestination navDestination = navBackStackEntry.destination;
            Bundle arguments = navBackStackEntry.getArguments();
            boolean z3 = true;
            if (!Intrinsics.areEqual(navDestination.route, str)) {
                NavDestination.DeepLinkMatch matchDeepLink = navDestination.matchDeepLink(str);
                if (navDestination.equals(matchDeepLink != null ? matchDeepLink.destination : null)) {
                    if (arguments != null) {
                        Bundle bundle = matchDeepLink.matchingArgs;
                        if (bundle != null) {
                            for (String str2 : bundle.keySet()) {
                                if (arguments.containsKey(str2)) {
                                    NavArgument navArgument = (NavArgument) ((LinkedHashMap) matchDeepLink.destination._arguments).get(str2);
                                    NavType navType = navArgument != null ? navArgument.type : null;
                                    Object obj2 = navType != null ? navType.get(matchDeepLink.matchingArgs, str2) : null;
                                    Object obj3 = navType != null ? navType.get(arguments, str2) : null;
                                    if (navType == null || navType.valueEquals(obj2, obj3)) {
                                    }
                                }
                            }
                        }
                    } else {
                        matchDeepLink.getClass();
                    }
                }
                z3 = false;
                break;
            }
            if (z || !z3) {
                arrayList.add(this._navigatorProvider.getNavigator(navBackStackEntry.destination.navigatorName));
            }
            if (z3) {
                break;
            }
        }
        NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) obj;
        NavDestination navDestination2 = navBackStackEntry2 != null ? navBackStackEntry2.destination : null;
        if (navDestination2 == null) {
            Log.i("NavController", "Ignoring popBackStack to route " + str + " as it was not found on the current back stack");
            return false;
        }
        return executePopOperations(arrayList, navDestination2, z, z2);
    }
}
