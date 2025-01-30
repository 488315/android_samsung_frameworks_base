package androidx.fragment.app;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SpecialEffectsController {
    public final ViewGroup mContainer;
    public final ArrayList mPendingOperations = new ArrayList();
    public final ArrayList mRunningOperations = new ArrayList();
    public boolean mOperationDirectionIsPop = false;
    public boolean mIsContainerPostponed = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.SpecialEffectsController$3 */
    public abstract /* synthetic */ class AbstractC02563 {

        /* renamed from: $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact */
        public static final /* synthetic */ int[] f172xb9e640f0;

        /* renamed from: $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State */
        public static final /* synthetic */ int[] f173xe493b431;

        static {
            int[] iArr = new int[Operation.LifecycleImpact.values().length];
            f172xb9e640f0 = iArr;
            try {
                iArr[Operation.LifecycleImpact.ADDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f172xb9e640f0[Operation.LifecycleImpact.REMOVING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f172xb9e640f0[Operation.LifecycleImpact.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[Operation.State.values().length];
            f173xe493b431 = iArr2;
            try {
                iArr2[Operation.State.REMOVED.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f173xe493b431[Operation.State.VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f173xe493b431[Operation.State.GONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f173xe493b431[Operation.State.INVISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FragmentStateManagerOperation extends Operation {
        public final FragmentStateManager mFragmentStateManager;

        public FragmentStateManagerOperation(Operation.State state, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager, CancellationSignal cancellationSignal) {
            super(state, lifecycleImpact, fragmentStateManager.mFragment, cancellationSignal);
            this.mFragmentStateManager = fragmentStateManager;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public final void complete() {
            super.complete();
            this.mFragmentStateManager.moveToExpectedState();
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public final void onStart() {
            Operation.LifecycleImpact lifecycleImpact = this.mLifecycleImpact;
            Operation.LifecycleImpact lifecycleImpact2 = Operation.LifecycleImpact.ADDING;
            FragmentStateManager fragmentStateManager = this.mFragmentStateManager;
            if (lifecycleImpact != lifecycleImpact2) {
                if (lifecycleImpact == Operation.LifecycleImpact.REMOVING) {
                    Fragment fragment = fragmentStateManager.mFragment;
                    View requireView = fragment.requireView();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(requireView.findFocus());
                        requireView.toString();
                        fragment.toString();
                    }
                    requireView.clearFocus();
                    return;
                }
                return;
            }
            Fragment fragment2 = fragmentStateManager.mFragment;
            View findFocus = fragment2.mView.findFocus();
            if (findFocus != null) {
                fragment2.ensureAnimationInfo().mFocusedView = findFocus;
                if (FragmentManager.isLoggingEnabled(2)) {
                    findFocus.toString();
                    fragment2.toString();
                }
            }
            View requireView2 = this.mFragment.requireView();
            if (requireView2.getParent() == null) {
                fragmentStateManager.addViewToContainer();
                requireView2.setAlpha(0.0f);
            }
            if (requireView2.getAlpha() == 0.0f && requireView2.getVisibility() == 0) {
                requireView2.setVisibility(4);
            }
            Fragment.AnimationInfo animationInfo = fragment2.mAnimationInfo;
            requireView2.setAlpha(animationInfo == null ? 1.0f : animationInfo.mPostOnViewCreatedAlpha);
        }
    }

    public SpecialEffectsController(ViewGroup viewGroup) {
        this.mContainer = viewGroup;
    }

    public static SpecialEffectsController getOrCreateController(ViewGroup viewGroup, FragmentManager.C02344 c02344) {
        Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
        if (tag instanceof SpecialEffectsController) {
            return (SpecialEffectsController) tag;
        }
        c02344.getClass();
        DefaultSpecialEffectsController defaultSpecialEffectsController = new DefaultSpecialEffectsController(viewGroup);
        viewGroup.setTag(R.id.special_effects_controller_view_tag, defaultSpecialEffectsController);
        return defaultSpecialEffectsController;
    }

    public final void enqueue(Operation.State state, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager) {
        synchronized (this.mPendingOperations) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            Operation findPendingOperation = findPendingOperation(fragmentStateManager.mFragment);
            if (findPendingOperation != null) {
                findPendingOperation.mergeWith(state, lifecycleImpact);
                return;
            }
            final FragmentStateManagerOperation fragmentStateManagerOperation = new FragmentStateManagerOperation(state, lifecycleImpact, fragmentStateManager, cancellationSignal);
            this.mPendingOperations.add(fragmentStateManagerOperation);
            ((ArrayList) fragmentStateManagerOperation.mCompletionListeners).add(new Runnable() { // from class: androidx.fragment.app.SpecialEffectsController.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (SpecialEffectsController.this.mPendingOperations.contains(fragmentStateManagerOperation)) {
                        FragmentStateManagerOperation fragmentStateManagerOperation2 = fragmentStateManagerOperation;
                        fragmentStateManagerOperation2.mFinalState.applyState(fragmentStateManagerOperation2.mFragment.mView);
                    }
                }
            });
            ((ArrayList) fragmentStateManagerOperation.mCompletionListeners).add(new Runnable() { // from class: androidx.fragment.app.SpecialEffectsController.2
                @Override // java.lang.Runnable
                public final void run() {
                    SpecialEffectsController.this.mPendingOperations.remove(fragmentStateManagerOperation);
                    SpecialEffectsController.this.mRunningOperations.remove(fragmentStateManagerOperation);
                }
            });
        }
    }

    public abstract void executeOperations(List list, boolean z);

    public final void executePendingOperations() {
        if (this.mIsContainerPostponed) {
            return;
        }
        ViewGroup viewGroup = this.mContainer;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!ViewCompat.Api19Impl.isAttachedToWindow(viewGroup)) {
            forceCompleteAllOperations();
            this.mOperationDirectionIsPop = false;
            return;
        }
        synchronized (this.mPendingOperations) {
            if (!this.mPendingOperations.isEmpty()) {
                ArrayList arrayList = new ArrayList(this.mRunningOperations);
                this.mRunningOperations.clear();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Operation operation = (Operation) it.next();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(operation);
                    }
                    operation.cancel();
                    if (!operation.mIsComplete) {
                        this.mRunningOperations.add(operation);
                    }
                }
                updateFinalState();
                ArrayList arrayList2 = new ArrayList(this.mPendingOperations);
                this.mPendingOperations.clear();
                this.mRunningOperations.addAll(arrayList2);
                FragmentManager.isLoggingEnabled(2);
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    ((Operation) it2.next()).onStart();
                }
                executeOperations(arrayList2, this.mOperationDirectionIsPop);
                this.mOperationDirectionIsPop = false;
                FragmentManager.isLoggingEnabled(2);
            }
        }
    }

    public final Operation findPendingOperation(Fragment fragment) {
        Iterator it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            Operation operation = (Operation) it.next();
            if (operation.mFragment.equals(fragment) && !operation.mIsCanceled) {
                return operation;
            }
        }
        return null;
    }

    public final void forceCompleteAllOperations() {
        ViewGroup viewGroup = this.mContainer;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean isAttachedToWindow = ViewCompat.Api19Impl.isAttachedToWindow(viewGroup);
        synchronized (this.mPendingOperations) {
            updateFinalState();
            Iterator it = this.mPendingOperations.iterator();
            while (it.hasNext()) {
                ((Operation) it.next()).onStart();
            }
            Iterator it2 = new ArrayList(this.mRunningOperations).iterator();
            while (it2.hasNext()) {
                Operation operation = (Operation) it2.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    if (!isAttachedToWindow) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Container ");
                        sb.append(this.mContainer);
                        sb.append(" is not attached to window. ");
                    }
                    Objects.toString(operation);
                }
                operation.cancel();
            }
            Iterator it3 = new ArrayList(this.mPendingOperations).iterator();
            while (it3.hasNext()) {
                Operation operation2 = (Operation) it3.next();
                if (FragmentManager.isLoggingEnabled(2)) {
                    if (!isAttachedToWindow) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Container ");
                        sb2.append(this.mContainer);
                        sb2.append(" is not attached to window. ");
                    }
                    Objects.toString(operation2);
                }
                operation2.cancel();
            }
        }
    }

    public final void markPostponedState() {
        synchronized (this.mPendingOperations) {
            updateFinalState();
            this.mIsContainerPostponed = false;
            int size = this.mPendingOperations.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                Operation operation = (Operation) this.mPendingOperations.get(size);
                Operation.State from = Operation.State.from(operation.mFragment.mView);
                Operation.State state = operation.mFinalState;
                Operation.State state2 = Operation.State.VISIBLE;
                if (state == state2 && from != state2) {
                    Fragment.AnimationInfo animationInfo = operation.mFragment.mAnimationInfo;
                    this.mIsContainerPostponed = false;
                    break;
                }
            }
        }
    }

    public final void updateFinalState() {
        Iterator it = this.mPendingOperations.iterator();
        while (it.hasNext()) {
            Operation operation = (Operation) it.next();
            if (operation.mLifecycleImpact == Operation.LifecycleImpact.ADDING) {
                operation.mergeWith(Operation.State.from(operation.mFragment.requireView().getVisibility()), Operation.LifecycleImpact.NONE);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Operation {
        public State mFinalState;
        public final Fragment mFragment;
        public LifecycleImpact mLifecycleImpact;
        public final List mCompletionListeners = new ArrayList();
        public final HashSet mSpecialEffectsSignals = new HashSet();
        public boolean mIsCanceled = false;
        public boolean mIsComplete = false;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING
        }

        public Operation(State state, LifecycleImpact lifecycleImpact, Fragment fragment, CancellationSignal cancellationSignal) {
            this.mFinalState = state;
            this.mLifecycleImpact = lifecycleImpact;
            this.mFragment = fragment;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.SpecialEffectsController.Operation.1
                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    Operation.this.cancel();
                }
            });
        }

        public final void cancel() {
            if (this.mIsCanceled) {
                return;
            }
            this.mIsCanceled = true;
            HashSet hashSet = this.mSpecialEffectsSignals;
            if (hashSet.isEmpty()) {
                complete();
                return;
            }
            Iterator it = new ArrayList(hashSet).iterator();
            while (it.hasNext()) {
                ((CancellationSignal) it.next()).cancel();
            }
        }

        public void complete() {
            if (this.mIsComplete) {
                return;
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                toString();
            }
            this.mIsComplete = true;
            Iterator it = this.mCompletionListeners.iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }

        public final void mergeWith(State state, LifecycleImpact lifecycleImpact) {
            int i = AbstractC02563.f172xb9e640f0[lifecycleImpact.ordinal()];
            Fragment fragment = this.mFragment;
            if (i == 1) {
                if (this.mFinalState == State.REMOVED) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(fragment);
                        Objects.toString(this.mLifecycleImpact);
                    }
                    this.mFinalState = State.VISIBLE;
                    this.mLifecycleImpact = LifecycleImpact.ADDING;
                    return;
                }
                return;
            }
            if (i == 2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(fragment);
                    Objects.toString(this.mFinalState);
                    Objects.toString(this.mLifecycleImpact);
                }
                this.mFinalState = State.REMOVED;
                this.mLifecycleImpact = LifecycleImpact.REMOVING;
                return;
            }
            if (i == 3 && this.mFinalState != State.REMOVED) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(fragment);
                    Objects.toString(this.mFinalState);
                    Objects.toString(state);
                }
                this.mFinalState = state;
            }
        }

        public final String toString() {
            return "Operation {" + Integer.toHexString(System.identityHashCode(this)) + "} {mFinalState = " + this.mFinalState + "} {mLifecycleImpact = " + this.mLifecycleImpact + "} {mFragment = " + this.mFragment + "}";
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            public static State from(View view) {
                return (view.getAlpha() == 0.0f && view.getVisibility() == 0) ? INVISIBLE : from(view.getVisibility());
            }

            public final void applyState(View view) {
                int i = AbstractC02563.f173xe493b431[ordinal()];
                if (i == 1) {
                    ViewGroup viewGroup = (ViewGroup) view.getParent();
                    if (viewGroup != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            view.toString();
                            viewGroup.toString();
                        }
                        viewGroup.removeView(view);
                        return;
                    }
                    return;
                }
                if (i == 2) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(view);
                    }
                    view.setVisibility(0);
                } else if (i == 3) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(view);
                    }
                    view.setVisibility(8);
                } else {
                    if (i != 4) {
                        return;
                    }
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(view);
                    }
                    view.setVisibility(4);
                }
            }

            public static State from(int i) {
                if (i == 0) {
                    return VISIBLE;
                }
                if (i == 4) {
                    return INVISIBLE;
                }
                if (i == 8) {
                    return GONE;
                }
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown visibility ", i));
            }
        }

        public void onStart() {
        }
    }
}
