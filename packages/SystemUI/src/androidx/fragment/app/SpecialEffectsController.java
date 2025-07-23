package androidx.fragment.app;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.activity.BackEventCompat;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.SpecialEffectsController;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SpecialEffectsController {
    public static final Companion Companion = new Companion(null);
    public final ViewGroup container;
    public boolean isContainerPostponed;
    public boolean operationDirectionIsPop;
    public boolean runningNonSeekableTransition;
    public final List pendingOperations = new ArrayList();
    public final List runningOperations = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FragmentStateManagerOperation extends Operation {
        public final FragmentStateManager fragmentStateManager;

        public FragmentStateManagerOperation(Operation.State state, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager) {
            super(state, lifecycleImpact, fragmentStateManager.mFragment);
            this.fragmentStateManager = fragmentStateManager;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public final void complete$fragment_release() {
            super.complete$fragment_release();
            this.fragment.mTransitioning = false;
            this.fragmentStateManager.moveToExpectedState();
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public final void onStart() {
            if (this.isStarted) {
                return;
            }
            this.isStarted = true;
            Operation.LifecycleImpact lifecycleImpact = this.lifecycleImpact;
            Operation.LifecycleImpact lifecycleImpact2 = Operation.LifecycleImpact.ADDING;
            FragmentStateManager fragmentStateManager = this.fragmentStateManager;
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
            View requireView2 = this.fragment.requireView();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Operation {
        public final List _effects;
        public final List effects;
        public State finalState;
        public final Fragment fragment;
        public boolean isCanceled;
        public boolean isComplete;
        public boolean isSeeking;
        public boolean isStarted;
        public LifecycleImpact lifecycleImpact;
        public final List completionListeners = new ArrayList();
        public boolean isAwaitingContainerChanges = true;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            public static final Companion Companion = new Companion(null);

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class Companion {
                public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                    this();
                }

                public static State asOperationState(View view) {
                    return (view.getAlpha() == 0.0f && view.getVisibility() == 0) ? State.INVISIBLE : from(view.getVisibility());
                }

                public static State from(int i) {
                    if (i == 0) {
                        return State.VISIBLE;
                    }
                    if (i == 4) {
                        return State.INVISIBLE;
                    }
                    if (i == 8) {
                        return State.GONE;
                    }
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown visibility "));
                }

                private Companion() {
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[State.values().length];
                    try {
                        iArr[State.REMOVED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[State.VISIBLE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[State.GONE.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[State.INVISIBLE.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            public final void applyState(View view, ViewGroup viewGroup) {
                int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
                if (i == 1) {
                    ViewParent parent = view.getParent();
                    ViewGroup viewGroup2 = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                    if (viewGroup2 != null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            view.toString();
                            viewGroup2.toString();
                        }
                        viewGroup2.removeView(view);
                        return;
                    }
                    return;
                }
                if (i == 2) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(view);
                    }
                    ViewParent parent2 = view.getParent();
                    if ((parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null) == null) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            view.toString();
                            Objects.toString(viewGroup);
                        }
                        viewGroup.addView(view);
                    }
                    view.setVisibility(0);
                    return;
                }
                if (i == 3) {
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
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[LifecycleImpact.values().length];
                try {
                    iArr[LifecycleImpact.ADDING.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[LifecycleImpact.REMOVING.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[LifecycleImpact.NONE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Operation(State state, LifecycleImpact lifecycleImpact, Fragment fragment) {
            this.finalState = state;
            this.lifecycleImpact = lifecycleImpact;
            this.fragment = fragment;
            ArrayList arrayList = new ArrayList();
            this._effects = arrayList;
            this.effects = arrayList;
        }

        public final void cancel(ViewGroup viewGroup) {
            this.isStarted = false;
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            if (((ArrayList) this._effects).isEmpty()) {
                complete$fragment_release();
                return;
            }
            for (Effect effect : CollectionsKt___CollectionsKt.toList(this.effects)) {
                if (!effect.isCancelled) {
                    effect.onCancel(viewGroup);
                }
                effect.isCancelled = true;
            }
        }

        public void complete$fragment_release() {
            int i = 0;
            this.isStarted = false;
            if (this.isComplete) {
                return;
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                toString();
            }
            this.isComplete = true;
            ArrayList arrayList = (ArrayList) this.completionListeners;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Runnable) obj).run();
            }
        }

        public final void completeEffect(Effect effect) {
            if (((ArrayList) this._effects).remove(effect) && ((ArrayList) this._effects).isEmpty()) {
                complete$fragment_release();
            }
        }

        public final void mergeWith(State state, LifecycleImpact lifecycleImpact) {
            int i = WhenMappings.$EnumSwitchMapping$0[lifecycleImpact.ordinal()];
            Fragment fragment = this.fragment;
            if (i == 1) {
                if (this.finalState == State.REMOVED) {
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Objects.toString(fragment);
                        Objects.toString(this.lifecycleImpact);
                    }
                    this.finalState = State.VISIBLE;
                    this.lifecycleImpact = LifecycleImpact.ADDING;
                    this.isAwaitingContainerChanges = true;
                    return;
                }
                return;
            }
            if (i == 2) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(fragment);
                    Objects.toString(this.finalState);
                    Objects.toString(this.lifecycleImpact);
                }
                this.finalState = State.REMOVED;
                this.lifecycleImpact = LifecycleImpact.REMOVING;
                this.isAwaitingContainerChanges = true;
                return;
            }
            if (i == 3 && this.finalState != State.REMOVED) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(fragment);
                    Objects.toString(this.finalState);
                    Objects.toString(state);
                }
                this.finalState = state;
            }
        }

        public void onStart() {
            this.isStarted = true;
        }

        public final String toString() {
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Operation {", Integer.toHexString(System.identityHashCode(this)), "} {finalState = ");
            m.append(this.finalState);
            m.append(" lifecycleImpact = ");
            m.append(this.lifecycleImpact);
            m.append(" fragment = ");
            m.append(this.fragment);
            m.append('}');
            return m.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Operation.LifecycleImpact.values().length];
            try {
                iArr[Operation.LifecycleImpact.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SpecialEffectsController(ViewGroup viewGroup) {
        this.container = viewGroup;
    }

    public static final SpecialEffectsController getOrCreateController(ViewGroup viewGroup, FragmentManager fragmentManager) {
        Companion.getClass();
        FragmentManager.AnonymousClass4 specialEffectsControllerFactory = fragmentManager.getSpecialEffectsControllerFactory();
        Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
        if (tag instanceof SpecialEffectsController) {
            return (SpecialEffectsController) tag;
        }
        specialEffectsControllerFactory.getClass();
        DefaultSpecialEffectsController defaultSpecialEffectsController = new DefaultSpecialEffectsController(viewGroup);
        viewGroup.setTag(R.id.special_effects_controller_view_tag, defaultSpecialEffectsController);
        return defaultSpecialEffectsController;
    }

    public static boolean isOperationSeekable(List list) {
        boolean z;
        Iterator it = list.iterator();
        loop0: while (true) {
            z = true;
            while (it.hasNext()) {
                Operation operation = (Operation) it.next();
                if (!((ArrayList) operation.effects).isEmpty()) {
                    List list2 = operation.effects;
                    if (list2 == null || !((ArrayList) list2).isEmpty()) {
                        ArrayList arrayList = (ArrayList) list2;
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            if (!((Effect) obj).isSeekingSupported()) {
                                break;
                            }
                        }
                    }
                }
                z = false;
            }
            break loop0;
        }
        if (z) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                CollectionsKt__MutableCollectionsKt.addAll(((Operation) it2.next()).effects, arrayList2);
            }
            if (!arrayList2.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public final void applyContainerChangesToOperation$fragment_release(Operation operation) {
        if (operation.isAwaitingContainerChanges) {
            operation.finalState.applyState(operation.fragment.requireView(), this.container);
            operation.isAwaitingContainerChanges = false;
        }
    }

    public abstract void collectEffects(List list, boolean z);

    public final void commitEffects$fragment_release(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll(((Operation) it.next()).effects, arrayList);
        }
        List list2 = CollectionsKt___CollectionsKt.toList(CollectionsKt___CollectionsKt.toSet(arrayList));
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            ((Effect) list2.get(i)).onCommit(this.container);
        }
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            applyContainerChangesToOperation$fragment_release((Operation) ((ArrayList) list).get(i2));
        }
        List list3 = CollectionsKt___CollectionsKt.toList(list);
        int size3 = list3.size();
        for (int i3 = 0; i3 < size3; i3++) {
            Operation operation = (Operation) list3.get(i3);
            if (((ArrayList) operation.effects).isEmpty()) {
                operation.complete$fragment_release();
            }
        }
    }

    public final void enqueue(Operation.State state, Operation.LifecycleImpact lifecycleImpact, FragmentStateManager fragmentStateManager) {
        synchronized (this.pendingOperations) {
            try {
                Operation findPendingOperation = findPendingOperation(fragmentStateManager.mFragment);
                if (findPendingOperation == null) {
                    Fragment fragment = fragmentStateManager.mFragment;
                    findPendingOperation = fragment.mTransitioning ? findRunningOperation(fragment) : null;
                }
                if (findPendingOperation != null) {
                    findPendingOperation.mergeWith(state, lifecycleImpact);
                    return;
                }
                final FragmentStateManagerOperation fragmentStateManagerOperation = new FragmentStateManagerOperation(state, lifecycleImpact, fragmentStateManager);
                ((ArrayList) this.pendingOperations).add(fragmentStateManagerOperation);
                final int i = 0;
                ((ArrayList) fragmentStateManagerOperation.completionListeners).add(new Runnable(this) { // from class: androidx.fragment.app.SpecialEffectsController$$ExternalSyntheticLambda0
                    public final /* synthetic */ SpecialEffectsController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                SpecialEffectsController specialEffectsController = this.f$0;
                                SpecialEffectsController.FragmentStateManagerOperation fragmentStateManagerOperation2 = fragmentStateManagerOperation;
                                if (((ArrayList) specialEffectsController.pendingOperations).contains(fragmentStateManagerOperation2)) {
                                    fragmentStateManagerOperation2.finalState.applyState(fragmentStateManagerOperation2.fragment.mView, specialEffectsController.container);
                                    break;
                                }
                                break;
                            default:
                                SpecialEffectsController specialEffectsController2 = this.f$0;
                                SpecialEffectsController.FragmentStateManagerOperation fragmentStateManagerOperation3 = fragmentStateManagerOperation;
                                ((ArrayList) specialEffectsController2.pendingOperations).remove(fragmentStateManagerOperation3);
                                ((ArrayList) specialEffectsController2.runningOperations).remove(fragmentStateManagerOperation3);
                                break;
                        }
                    }
                });
                final int i2 = 1;
                ((ArrayList) fragmentStateManagerOperation.completionListeners).add(new Runnable(this) { // from class: androidx.fragment.app.SpecialEffectsController$$ExternalSyntheticLambda0
                    public final /* synthetic */ SpecialEffectsController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                SpecialEffectsController specialEffectsController = this.f$0;
                                SpecialEffectsController.FragmentStateManagerOperation fragmentStateManagerOperation2 = fragmentStateManagerOperation;
                                if (((ArrayList) specialEffectsController.pendingOperations).contains(fragmentStateManagerOperation2)) {
                                    fragmentStateManagerOperation2.finalState.applyState(fragmentStateManagerOperation2.fragment.mView, specialEffectsController.container);
                                    break;
                                }
                                break;
                            default:
                                SpecialEffectsController specialEffectsController2 = this.f$0;
                                SpecialEffectsController.FragmentStateManagerOperation fragmentStateManagerOperation3 = fragmentStateManagerOperation;
                                ((ArrayList) specialEffectsController2.pendingOperations).remove(fragmentStateManagerOperation3);
                                ((ArrayList) specialEffectsController2.runningOperations).remove(fragmentStateManagerOperation3);
                                break;
                        }
                    }
                });
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void executePendingOperations() {
        if (this.isContainerPostponed) {
            return;
        }
        if (!this.container.isAttachedToWindow()) {
            forceCompleteAllOperations();
            this.operationDirectionIsPop = false;
            return;
        }
        synchronized (this.pendingOperations) {
            try {
                ArrayList arrayList = new ArrayList(this.runningOperations);
                ((ArrayList) this.runningOperations).clear();
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Operation operation = (Operation) obj;
                    operation.isSeeking = !((ArrayList) this.pendingOperations).isEmpty() && operation.fragment.mTransitioning;
                }
                int size2 = arrayList.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    Operation operation2 = (Operation) obj2;
                    if (this.runningNonSeekableTransition) {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Objects.toString(operation2);
                        }
                        operation2.complete$fragment_release();
                    } else {
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Objects.toString(operation2);
                        }
                        operation2.cancel(this.container);
                    }
                    this.runningNonSeekableTransition = false;
                    if (!operation2.isComplete) {
                        ((ArrayList) this.runningOperations).add(operation2);
                    }
                }
                if (!((ArrayList) this.pendingOperations).isEmpty()) {
                    updateFinalState();
                    ArrayList arrayList2 = new ArrayList(this.pendingOperations);
                    if (arrayList2.isEmpty()) {
                        return;
                    }
                    ((ArrayList) this.pendingOperations).clear();
                    ((ArrayList) this.runningOperations).addAll(arrayList2);
                    collectEffects(arrayList2, this.operationDirectionIsPop);
                    boolean isOperationSeekable = isOperationSeekable(arrayList2);
                    int size3 = arrayList2.size();
                    boolean z = true;
                    int i3 = 0;
                    while (i3 < size3) {
                        Object obj3 = arrayList2.get(i3);
                        i3++;
                        if (!((Operation) obj3).fragment.mTransitioning) {
                            z = false;
                        }
                    }
                    this.runningNonSeekableTransition = z && !isOperationSeekable;
                    if (!z) {
                        processStart(arrayList2);
                        commitEffects$fragment_release(arrayList2);
                    } else if (isOperationSeekable) {
                        processStart(arrayList2);
                        int size4 = arrayList2.size();
                        for (int i4 = 0; i4 < size4; i4++) {
                            applyContainerChangesToOperation$fragment_release((Operation) arrayList2.get(i4));
                        }
                    }
                    this.operationDirectionIsPop = false;
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Operation findPendingOperation(Fragment fragment) {
        Object obj;
        ArrayList arrayList = (ArrayList) this.pendingOperations;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i);
            i++;
            Operation operation = (Operation) obj;
            if (Intrinsics.areEqual(operation.fragment, fragment) && !operation.isCanceled) {
                break;
            }
        }
        return (Operation) obj;
    }

    public final Operation findRunningOperation(Fragment fragment) {
        Object obj;
        ArrayList arrayList = (ArrayList) this.runningOperations;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i);
            i++;
            Operation operation = (Operation) obj;
            if (Intrinsics.areEqual(operation.fragment, fragment) && !operation.isCanceled) {
                break;
            }
        }
        return (Operation) obj;
    }

    public final void forceCompleteAllOperations() {
        boolean isAttachedToWindow = this.container.isAttachedToWindow();
        synchronized (this.pendingOperations) {
            try {
                updateFinalState();
                processStart(this.pendingOperations);
                ArrayList arrayList = new ArrayList(this.runningOperations);
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((Operation) obj).isSeeking = false;
                }
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    Operation operation = (Operation) obj2;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        if (!isAttachedToWindow) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Container ");
                            sb.append(this.container);
                            sb.append(" is not attached to window. ");
                        }
                        Objects.toString(operation);
                    }
                    operation.cancel(this.container);
                }
                ArrayList arrayList2 = new ArrayList(this.pendingOperations);
                int size3 = arrayList2.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object obj3 = arrayList2.get(i4);
                    i4++;
                    ((Operation) obj3).isSeeking = false;
                }
                int size4 = arrayList2.size();
                while (i < size4) {
                    Object obj4 = arrayList2.get(i);
                    i++;
                    Operation operation2 = (Operation) obj4;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        if (!isAttachedToWindow) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Container ");
                            sb2.append(this.container);
                            sb2.append(" is not attached to window. ");
                        }
                        Objects.toString(operation2);
                    }
                    operation2.cancel(this.container);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void processStart(List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((Operation) ((ArrayList) list).get(i)).onStart();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt__MutableCollectionsKt.addAll(((Operation) it.next()).effects, arrayList);
        }
        List list2 = CollectionsKt___CollectionsKt.toList(CollectionsKt___CollectionsKt.toSet(arrayList));
        int size2 = list2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            Effect effect = (Effect) list2.get(i2);
            ViewGroup viewGroup = this.container;
            if (!effect.isStarted) {
                effect.onStart(viewGroup);
            }
            effect.isStarted = true;
        }
    }

    public final void updateFinalState() {
        ArrayList arrayList = (ArrayList) this.pendingOperations;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Operation operation = (Operation) obj;
            if (operation.lifecycleImpact == Operation.LifecycleImpact.ADDING) {
                View requireView = operation.fragment.requireView();
                Operation.State.Companion companion = Operation.State.Companion;
                int visibility = requireView.getVisibility();
                companion.getClass();
                operation.mergeWith(Operation.State.Companion.from(visibility), Operation.LifecycleImpact.NONE);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Effect {
        public boolean isCancelled;
        public boolean isStarted;

        public boolean isSeekingSupported() {
            return this instanceof DefaultSpecialEffectsController.AnimatorEffect;
        }

        public void onCancel(ViewGroup viewGroup) {
        }

        public void onCommit(ViewGroup viewGroup) {
        }

        public void onProgress(BackEventCompat backEventCompat) {
        }

        public void onStart(ViewGroup viewGroup) {
        }
    }
}
