package androidx.navigation;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public abstract class NavigatorState {
    public final StateFlowImpl _backStack;
    public final StateFlowImpl _transitionsInProgress;
    public final ReadonlyStateFlow backStack;
    public final ReentrantLock backStackLock = new ReentrantLock(true);
    public boolean isNavigating;
    public final ReadonlyStateFlow transitionsInProgress;

    public NavigatorState() {
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._backStack = stateFlowImplMutableStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
        this._transitionsInProgress = stateFlowImplMutableStateFlow2;
        this.backStack = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.transitionsInProgress = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
    }

    public abstract NavBackStackEntry createBackStackEntry(NavDestination navDestination, Bundle bundle);

    public void markTransitionComplete(NavBackStackEntry navBackStackEntry) {
        StateFlowImpl stateFlowImpl = this._transitionsInProgress;
        stateFlowImpl.updateState(null, SetsKt___SetsKt.minus((Set) stateFlowImpl.getValue(), navBackStackEntry));
    }

    public void pop(NavBackStackEntry navBackStackEntry, boolean z) {
        ReentrantLock reentrantLock = this.backStackLock;
        reentrantLock.lock();
        try {
            StateFlowImpl stateFlowImpl = this._backStack;
            Iterable iterable = (Iterable) stateFlowImpl.getValue();
            ArrayList arrayList = new ArrayList();
            for (Object obj : iterable) {
                if (Intrinsics.areEqual((NavBackStackEntry) obj, navBackStackEntry)) {
                    break;
                } else {
                    arrayList.add(obj);
                }
            }
            stateFlowImpl.updateState(null, arrayList);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    public void popWithTransition(NavBackStackEntry navBackStackEntry, boolean z) {
        Object objPrevious;
        StateFlowImpl stateFlowImpl = this._transitionsInProgress;
        Iterable iterable = (Iterable) stateFlowImpl.getValue();
        boolean z2 = iterable instanceof Collection;
        ReadonlyStateFlow readonlyStateFlow = this.backStack;
        if (!z2 || !((Collection) iterable).isEmpty()) {
            Iterator it = iterable.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((NavBackStackEntry) it.next()) == navBackStackEntry) {
                    Iterable iterable2 = (Iterable) readonlyStateFlow.$$delegate_0.getValue();
                    if ((iterable2 instanceof Collection) && ((Collection) iterable2).isEmpty()) {
                        return;
                    }
                    Iterator it2 = iterable2.iterator();
                    while (it2.hasNext()) {
                        if (((NavBackStackEntry) it2.next()) == navBackStackEntry) {
                        }
                    }
                    return;
                }
            }
        }
        stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), navBackStackEntry));
        List list = (List) readonlyStateFlow.$$delegate_0.getValue();
        ListIterator listIterator = list.listIterator(list.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            NavBackStackEntry navBackStackEntry2 = (NavBackStackEntry) objPrevious;
            if (!Intrinsics.areEqual(navBackStackEntry2, navBackStackEntry) && ((List) readonlyStateFlow.$$delegate_0.getValue()).lastIndexOf(navBackStackEntry2) < ((List) readonlyStateFlow.$$delegate_0.getValue()).lastIndexOf(navBackStackEntry)) {
                break;
            }
        }
        NavBackStackEntry navBackStackEntry3 = (NavBackStackEntry) objPrevious;
        if (navBackStackEntry3 != null) {
            stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), navBackStackEntry3));
        }
        pop(navBackStackEntry, z);
    }

    public void prepareForTransition(NavBackStackEntry navBackStackEntry) {
        StateFlowImpl stateFlowImpl = this._transitionsInProgress;
        stateFlowImpl.updateState(null, SetsKt___SetsKt.plus((Set) stateFlowImpl.getValue(), navBackStackEntry));
    }

    public void push(NavBackStackEntry navBackStackEntry) {
        ReentrantLock reentrantLock = this.backStackLock;
        reentrantLock.lock();
        try {
            StateFlowImpl stateFlowImpl = this._backStack;
            stateFlowImpl.updateState(null, CollectionsKt___CollectionsKt.plus((Collection) stateFlowImpl.getValue(), navBackStackEntry));
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }
}
