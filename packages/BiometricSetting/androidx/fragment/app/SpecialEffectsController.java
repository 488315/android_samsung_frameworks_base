package androidx.fragment.app;

import android.util.Log;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import androidx.core.os.CancellationSignal;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpecialEffectsController.kt */
/* loaded from: classes.dex */
public abstract class SpecialEffectsController {
    private final ViewGroup container;
    private boolean isContainerPostponed;
    private boolean operationDirectionIsPop;
    private final List<Operation> pendingOperations;
    private final List<Operation> runningOperations;

    /* compiled from: SpecialEffectsController.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Operation.LifecycleImpact.values().length];
            try {
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SpecialEffectsController(ViewGroup container) {
        Intrinsics.checkNotNullParameter(container, "container");
        this.container = container;
        this.pendingOperations = new ArrayList();
        this.runningOperations = new ArrayList();
    }

    private final Operation findPendingOperation(Fragment fragment) {
        Object obj;
        Iterator it = ((ArrayList) this.pendingOperations).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            Operation operation = (Operation) obj;
            if (Intrinsics.areEqual(operation.getFragment(), fragment) && !operation.isCanceled()) {
                break;
            }
        }
        return (Operation) obj;
    }

    private final void updateFinalState() {
        Operation.State state;
        Iterator it = ((ArrayList) this.pendingOperations).iterator();
        while (it.hasNext()) {
            Operation operation = (Operation) it.next();
            if (operation.getLifecycleImpact() == Operation.LifecycleImpact.ADDING) {
                int visibility = operation.getFragment().requireView().getVisibility();
                if (visibility == 0) {
                    state = Operation.State.VISIBLE;
                } else if (visibility == 4) {
                    state = Operation.State.INVISIBLE;
                } else {
                    if (visibility != 8) {
                        throw new IllegalArgumentException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unknown visibility ", visibility));
                    }
                    state = Operation.State.GONE;
                }
                operation.mergeWith(state, Operation.LifecycleImpact.NONE);
            }
        }
    }

    public abstract void executeOperations(List<Operation> list, boolean z);

    public final void executePendingOperations() {
        if (this.isContainerPostponed) {
            return;
        }
        if (!ViewCompat.isAttachedToWindow(this.container)) {
            forceCompleteAllOperations();
            this.operationDirectionIsPop = false;
            return;
        }
        synchronized (this.pendingOperations) {
            if (!this.pendingOperations.isEmpty()) {
                List mutableList = CollectionsKt.toMutableList(this.runningOperations);
                ((ArrayList) this.runningOperations).clear();
                Iterator it = ((ArrayList) mutableList).iterator();
                while (it.hasNext()) {
                    Operation operation = (Operation) it.next();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + operation);
                    }
                    operation.cancel();
                    if (!operation.isComplete()) {
                        ((ArrayList) this.runningOperations).add(operation);
                    }
                }
                updateFinalState();
                List<Operation> mutableList2 = CollectionsKt.toMutableList(this.pendingOperations);
                ((ArrayList) this.pendingOperations).clear();
                ((ArrayList) this.runningOperations).addAll(mutableList2);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: Executing pending operations");
                }
                Iterator it2 = ((ArrayList) mutableList2).iterator();
                while (it2.hasNext()) {
                    ((Operation) it2.next()).onStart();
                }
                executeOperations(mutableList2, this.operationDirectionIsPop);
                this.operationDirectionIsPop = false;
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "SpecialEffectsController: Finished executing pending operations");
                }
            }
        }
    }

    public final void forceCompleteAllOperations() {
        String str;
        String str2;
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Forcing all operations to complete");
        }
        boolean isAttachedToWindow = ViewCompat.isAttachedToWindow(this.container);
        synchronized (this.pendingOperations) {
            updateFinalState();
            Iterator it = ((ArrayList) this.pendingOperations).iterator();
            while (it.hasNext()) {
                ((Operation) it.next()).onStart();
            }
            Iterator it2 = ((ArrayList) CollectionsKt.toMutableList(this.runningOperations)).iterator();
            while (it2.hasNext()) {
                Operation operation = (Operation) it2.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    if (isAttachedToWindow) {
                        str2 = "";
                    } else {
                        str2 = "Container " + this.container + " is not attached to window. ";
                    }
                    Log.v("FragmentManager", "SpecialEffectsController: " + str2 + "Cancelling running operation " + operation);
                }
                operation.cancel();
            }
            Iterator it3 = ((ArrayList) CollectionsKt.toMutableList(this.pendingOperations)).iterator();
            while (it3.hasNext()) {
                Operation operation2 = (Operation) it3.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    if (isAttachedToWindow) {
                        str = "";
                    } else {
                        str = "Container " + this.container + " is not attached to window. ";
                    }
                    Log.v("FragmentManager", "SpecialEffectsController: " + str + "Cancelling pending operation " + operation2);
                }
                operation2.cancel();
            }
        }
    }

    public final void forcePostponedExecutePendingOperations() {
        if (this.isContainerPostponed) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "SpecialEffectsController: Forcing postponed operations");
            }
            this.isContainerPostponed = false;
            executePendingOperations();
        }
    }

    public final Operation.LifecycleImpact getAwaitingCompletionLifecycleImpact(FragmentStateManager fragmentStateManager) {
        Object obj;
        Intrinsics.checkNotNullParameter(fragmentStateManager, "fragmentStateManager");
        Fragment fragment = fragmentStateManager.getFragment();
        Intrinsics.checkNotNullExpressionValue(fragment, "fragmentStateManager.fragment");
        Operation findPendingOperation = findPendingOperation(fragment);
        Operation.LifecycleImpact lifecycleImpact = findPendingOperation != null ? findPendingOperation.getLifecycleImpact() : null;
        Iterator it = ((ArrayList) this.runningOperations).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            Operation operation = (Operation) obj;
            if (Intrinsics.areEqual(operation.getFragment(), fragment) && !operation.isCanceled()) {
                break;
            }
        }
        Operation operation2 = (Operation) obj;
        Operation.LifecycleImpact lifecycleImpact2 = operation2 != null ? operation2.getLifecycleImpact() : null;
        int i = lifecycleImpact == null ? -1 : WhenMappings.$EnumSwitchMapping$0[lifecycleImpact.ordinal()];
        return (i == -1 || i == 1) ? lifecycleImpact2 : lifecycleImpact;
    }

    public final ViewGroup getContainer() {
        return this.container;
    }

    public final void markPostponedState() {
        synchronized (this.pendingOperations) {
            updateFinalState();
            ArrayList arrayList = (ArrayList) this.pendingOperations;
            ListIterator listIterator = arrayList.listIterator(arrayList.size());
            if (listIterator.hasPrevious()) {
                ((Operation) listIterator.previous()).getFragment().getClass();
                Intrinsics.checkNotNullExpressionValue(null, "operation.fragment.mView");
                throw null;
            }
            this.isContainerPostponed = false;
        }
    }

    public final void updateOperationDirection(boolean z) {
        this.operationDirectionIsPop = z;
    }

    /* compiled from: SpecialEffectsController.kt */
    public static class Operation {
        private final List<Runnable> completionListeners;
        private State finalState;
        private final Fragment fragment;
        private boolean isCanceled;
        private boolean isComplete;
        private LifecycleImpact lifecycleImpact;
        private final Set<CancellationSignal> specialEffectsSignals;

        /* compiled from: SpecialEffectsController.kt */
        public enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING;

            LifecycleImpact() {
            }
        }

        /* compiled from: SpecialEffectsController.kt */
        public enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            State() {
            }
        }

        public final void addCompletionListener(Runnable runnable) {
            ((ArrayList) this.completionListeners).add(runnable);
        }

        public final void cancel() {
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            Set<CancellationSignal> set = this.specialEffectsSignals;
            if (set.isEmpty()) {
                complete();
                return;
            }
            Iterator it = CollectionsKt.toMutableSet(set).iterator();
            while (it.hasNext()) {
                ((CancellationSignal) it.next()).cancel();
            }
        }

        public void complete() {
            if (this.isComplete) {
                return;
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
            }
            this.isComplete = true;
            Iterator<T> it = this.completionListeners.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }

        public final void completeSpecialEffect(CancellationSignal signal) {
            Intrinsics.checkNotNullParameter(signal, "signal");
            Set<CancellationSignal> set = this.specialEffectsSignals;
            if (set.remove(signal) && set.isEmpty()) {
                complete();
            }
        }

        public final State getFinalState() {
            return this.finalState;
        }

        public final Fragment getFragment() {
            return this.fragment;
        }

        public final LifecycleImpact getLifecycleImpact() {
            return this.lifecycleImpact;
        }

        public final boolean isCanceled() {
            return this.isCanceled;
        }

        public final boolean isComplete() {
            return this.isComplete;
        }

        public final void markStartedSpecialEffect(CancellationSignal cancellationSignal) {
            onStart();
            this.specialEffectsSignals.add(cancellationSignal);
        }

        public final void mergeWith(State state, LifecycleImpact lifecycleImpact) {
            int ordinal = lifecycleImpact.ordinal();
            State state2 = State.REMOVED;
            Fragment fragment = this.fragment;
            if (ordinal == 0) {
                if (this.finalState != state2) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + fragment + " mFinalState = " + this.finalState + " -> " + state + '.');
                    }
                    this.finalState = state;
                    return;
                }
                return;
            }
            if (ordinal == 1) {
                if (this.finalState == state2) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + fragment + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.lifecycleImpact + " to ADDING.");
                    }
                    this.finalState = State.VISIBLE;
                    this.lifecycleImpact = LifecycleImpact.ADDING;
                    return;
                }
                return;
            }
            if (ordinal != 2) {
                return;
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "SpecialEffectsController: For fragment " + fragment + " mFinalState = " + this.finalState + " -> REMOVED. mLifecycleImpact  = " + this.lifecycleImpact + " to REMOVING.");
            }
            this.finalState = state2;
            this.lifecycleImpact = LifecycleImpact.REMOVING;
        }

        public final String toString() {
            return "Operation {" + Integer.toHexString(System.identityHashCode(this)) + "} {finalState = " + this.finalState + " lifecycleImpact = " + this.lifecycleImpact + " fragment = " + this.fragment + '}';
        }

        public void onStart() {
        }
    }
}
