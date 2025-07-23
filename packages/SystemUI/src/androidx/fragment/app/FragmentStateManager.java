package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.collection.SparseArrayCompat;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.SpecialEffectsController;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.fragment.app.strictmode.WrongFragmentContainerViolation;
import androidx.fragment.app.strictmode.WrongNestedHierarchyViolation;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManagerImpl;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FragmentStateManager {
    public final FragmentLifecycleCallbacksDispatcher mDispatcher;
    public final Fragment mFragment;
    public final FragmentStore mFragmentStore;
    public boolean mMovingToState = false;
    public int mFragmentManagerState = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.fragment.app.FragmentStateManager$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$State;

        static {
            int[] iArr = new int[Lifecycle.State.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$State = iArr;
            try {
                iArr[Lifecycle.State.RESUMED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.CREATED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.INITIALIZED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, Fragment fragment) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
    }

    public final void activityCreated() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "moveto ACTIVITY_CREATED: " + fragment);
        }
        Bundle bundle = fragment.mSavedFragmentState;
        Bundle bundle2 = bundle != null ? bundle.getBundle("savedInstanceState") : null;
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mState = 3;
        fragment.mCalled = false;
        fragment.onActivityCreated(bundle2);
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onActivityCreated()");
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "moveto RESTORE_VIEW_STATE: " + fragment);
        }
        if (fragment.mView != null) {
            Bundle bundle3 = fragment.mSavedFragmentState;
            Bundle bundle4 = bundle3 != null ? bundle3.getBundle("savedInstanceState") : null;
            SparseArray<Parcelable> sparseArray = fragment.mSavedViewState;
            if (sparseArray != null) {
                fragment.mView.restoreHierarchyState(sparseArray);
                fragment.mSavedViewState = null;
            }
            fragment.mCalled = false;
            fragment.onViewStateRestored(bundle4);
            if (!fragment.mCalled) {
                throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onViewStateRestored()");
            }
            if (fragment.mView != null) {
                fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            }
        }
        fragment.mSavedFragmentState = null;
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(4);
        this.mDispatcher.dispatchOnFragmentActivityCreated(false);
    }

    public final void addViewToContainer() {
        Fragment fragment;
        View view;
        View view2;
        int i = -1;
        Fragment fragment2 = this.mFragment;
        View view3 = fragment2.mContainer;
        while (true) {
            fragment = null;
            if (view3 == null) {
                break;
            }
            Object tag = view3.getTag(R.id.fragment_container_view_tag);
            Fragment fragment3 = tag instanceof Fragment ? (Fragment) tag : null;
            if (fragment3 != null) {
                fragment = fragment3;
                break;
            } else {
                Object parent = view3.getParent();
                view3 = parent instanceof View ? (View) parent : null;
            }
        }
        Fragment fragment4 = fragment2.mParentFragment;
        if (fragment != null && !fragment.equals(fragment4)) {
            int i2 = fragment2.mContainerId;
            FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
            WrongNestedHierarchyViolation wrongNestedHierarchyViolation = new WrongNestedHierarchyViolation(fragment2, fragment, i2);
            FragmentStrictMode.INSTANCE.getClass();
            FragmentStrictMode.logIfDebuggingEnabled(wrongNestedHierarchyViolation);
            FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(fragment2);
            if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_WRONG_NESTED_HIERARCHY) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, fragment2.getClass(), WrongNestedHierarchyViolation.class)) {
                FragmentStrictMode.handlePolicyViolation(nearestPolicy, wrongNestedHierarchyViolation);
            }
        }
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.getClass();
        ViewGroup viewGroup = fragment2.mContainer;
        if (viewGroup != null) {
            int indexOf = fragmentStore.mAdded.indexOf(fragment2);
            int i3 = indexOf - 1;
            while (true) {
                if (i3 < 0) {
                    while (true) {
                        indexOf++;
                        if (indexOf >= fragmentStore.mAdded.size()) {
                            break;
                        }
                        Fragment fragment5 = (Fragment) fragmentStore.mAdded.get(indexOf);
                        if (fragment5.mContainer == viewGroup && (view = fragment5.mView) != null) {
                            i = viewGroup.indexOfChild(view);
                            break;
                        }
                    }
                } else {
                    Fragment fragment6 = (Fragment) fragmentStore.mAdded.get(i3);
                    if (fragment6.mContainer == viewGroup && (view2 = fragment6.mView) != null) {
                        i = viewGroup.indexOfChild(view2) + 1;
                        break;
                    }
                    i3--;
                }
            }
        }
        fragment2.mContainer.addView(fragment2.mView, i);
    }

    public final void attach() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "moveto ATTACHED: " + fragment);
        }
        Fragment fragment2 = fragment.mTarget;
        FragmentStateManager fragmentStateManager = null;
        FragmentStore fragmentStore = this.mFragmentStore;
        if (fragment2 != null) {
            FragmentStateManager fragmentStateManager2 = (FragmentStateManager) fragmentStore.mActive.get(fragment2.mWho);
            if (fragmentStateManager2 == null) {
                throw new IllegalStateException("Fragment " + fragment + " declared target fragment " + fragment.mTarget + " that does not belong to this FragmentManager!");
            }
            fragment.mTargetWho = fragment.mTarget.mWho;
            fragment.mTarget = null;
            fragmentStateManager = fragmentStateManager2;
        } else {
            String str = fragment.mTargetWho;
            if (str != null && (fragmentStateManager = (FragmentStateManager) fragmentStore.mActive.get(str)) == null) {
                StringBuilder sb = new StringBuilder("Fragment ");
                sb.append(fragment);
                sb.append(" declared target fragment ");
                throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(sb, fragment.mTargetWho, " that does not belong to this FragmentManager!"));
            }
        }
        if (fragmentStateManager != null) {
            fragmentStateManager.moveToExpectedState();
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        fragment.mHost = fragmentManager.mHost;
        fragment.mParentFragment = fragmentManager.mParent;
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentPreAttached(false);
        ArrayList arrayList = fragment.mOnPreAttachedListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Fragment.OnPreAttachedListener) obj).onPreAttached();
        }
        fragment.mOnPreAttachedListeners.clear();
        fragment.mChildFragmentManager.attachController(fragment.mHost, fragment.createFragmentContainer(), fragment);
        fragment.mState = 0;
        fragment.mCalled = false;
        fragment.onAttach(fragment.mHost.context);
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onAttach()");
        }
        Iterator it = fragment.mFragmentManager.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            ((FragmentOnAttachListener) it.next()).onAttachFragment$1();
        }
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(0);
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentAttached(fragment, false);
    }

    public final int computeExpectedState() {
        Fragment fragment = this.mFragment;
        if (fragment.mFragmentManager == null) {
            return fragment.mState;
        }
        int i = this.mFragmentManagerState;
        int i2 = AnonymousClass2.$SwitchMap$androidx$lifecycle$Lifecycle$State[fragment.mMaxState.ordinal()];
        if (i2 != 1) {
            i = i2 != 2 ? i2 != 3 ? i2 != 4 ? Math.min(i, -1) : Math.min(i, 0) : Math.min(i, 1) : Math.min(i, 5);
        }
        if (fragment.mFromLayout) {
            if (fragment.mInLayout) {
                i = Math.max(this.mFragmentManagerState, 2);
                View view = fragment.mView;
                if (view != null && view.getParent() == null) {
                    i = Math.min(i, 2);
                }
            } else {
                i = this.mFragmentManagerState < 4 ? Math.min(i, fragment.mState) : Math.min(i, 1);
            }
        }
        if (fragment.mInDynamicContainer && fragment.mContainer == null) {
            i = Math.min(i, 4);
        }
        if (!fragment.mAdded) {
            i = Math.min(i, 1);
        }
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            SpecialEffectsController orCreateController = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager());
            orCreateController.getClass();
            SpecialEffectsController.Operation findPendingOperation = orCreateController.findPendingOperation(fragment);
            SpecialEffectsController.Operation.LifecycleImpact lifecycleImpact = findPendingOperation != null ? findPendingOperation.lifecycleImpact : null;
            SpecialEffectsController.Operation findRunningOperation = orCreateController.findRunningOperation(fragment);
            r2 = findRunningOperation != null ? findRunningOperation.lifecycleImpact : null;
            int i3 = lifecycleImpact == null ? -1 : SpecialEffectsController.WhenMappings.$EnumSwitchMapping$0[lifecycleImpact.ordinal()];
            if (i3 != -1 && i3 != 1) {
                r2 = lifecycleImpact;
            }
        }
        if (r2 == SpecialEffectsController.Operation.LifecycleImpact.ADDING) {
            i = Math.min(i, 6);
        } else if (r2 == SpecialEffectsController.Operation.LifecycleImpact.REMOVING) {
            i = Math.max(i, 3);
        } else if (fragment.mRemoving) {
            i = fragment.isInBackStack() ? Math.min(i, 1) : Math.min(i, -1);
        }
        if (fragment.mDeferStart && fragment.mState < 5) {
            i = Math.min(i, 4);
        }
        if (fragment.mTransitioning) {
            i = Math.max(i, 3);
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(fragment);
        }
        return i;
    }

    public final void create() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        final Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "moveto CREATED: " + fragment);
        }
        Bundle bundle = fragment.mSavedFragmentState;
        Bundle bundle2 = bundle != null ? bundle.getBundle("savedInstanceState") : null;
        if (fragment.mIsCreated) {
            fragment.mState = 1;
            fragment.restoreChildFragmentState();
            return;
        }
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentPreCreated(false);
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mState = 1;
        fragment.mCalled = false;
        fragment.mLifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: androidx.fragment.app.Fragment.6
            public AnonymousClass6() {
            }

            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                View view;
                if (event != Lifecycle.Event.ON_STOP || (view = Fragment.this.mView) == null) {
                    return;
                }
                view.cancelPendingInputEvents();
            }
        });
        fragment.onCreate(bundle2);
        fragment.mIsCreated = true;
        if (fragment.mCalled) {
            fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentCreated(false);
        } else {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onCreate()");
        }
    }

    public final void createView() {
        String str;
        Fragment fragment = this.mFragment;
        if (fragment.mFromLayout) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "moveto CREATE_VIEW: " + fragment);
        }
        Bundle bundle = fragment.mSavedFragmentState;
        Bundle bundle2 = bundle != null ? bundle.getBundle("savedInstanceState") : null;
        LayoutInflater onGetLayoutInflater = fragment.onGetLayoutInflater(bundle2);
        fragment.mLayoutInflater = onGetLayoutInflater;
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup == null) {
            int i = fragment.mContainerId;
            if (i == 0) {
                viewGroup = null;
            } else {
                if (i == -1) {
                    throw new IllegalArgumentException("Cannot create fragment " + fragment + " for a container view with no id");
                }
                viewGroup = (ViewGroup) fragment.mFragmentManager.mContainer.onFindViewById(i);
                if (viewGroup == null) {
                    if (!fragment.mRestored && !fragment.mInDynamicContainer) {
                        try {
                            str = fragment.getResources().getResourceName(fragment.mContainerId);
                        } catch (Resources.NotFoundException unused) {
                            str = "unknown";
                        }
                        throw new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(fragment.mContainerId) + " (" + str + ") for fragment " + fragment);
                    }
                } else if (!(viewGroup instanceof FragmentContainerView)) {
                    FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
                    WrongFragmentContainerViolation wrongFragmentContainerViolation = new WrongFragmentContainerViolation(fragment, viewGroup);
                    FragmentStrictMode.INSTANCE.getClass();
                    FragmentStrictMode.logIfDebuggingEnabled(wrongFragmentContainerViolation);
                    FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(fragment);
                    if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_WRONG_FRAGMENT_CONTAINER) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, fragment.getClass(), WrongFragmentContainerViolation.class)) {
                        FragmentStrictMode.handlePolicyViolation(nearestPolicy, wrongFragmentContainerViolation);
                    }
                }
            }
        }
        fragment.mContainer = viewGroup;
        fragment.performCreateView(onGetLayoutInflater, viewGroup, bundle2);
        if (fragment.mView != null) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "moveto VIEW_CREATED: " + fragment);
            }
            fragment.mView.setSaveFromParentEnabled(false);
            fragment.mView.setTag(R.id.fragment_container_view_tag, fragment);
            if (viewGroup != null) {
                addViewToContainer();
            }
            if (fragment.mHidden) {
                fragment.mView.setVisibility(8);
            }
            if (fragment.mView.isAttachedToWindow()) {
                View view = fragment.mView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api20Impl.requestApplyInsets(view);
            } else {
                final View view2 = fragment.mView;
                view2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this) { // from class: androidx.fragment.app.FragmentStateManager.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view3) {
                        view2.removeOnAttachStateChangeListener(this);
                        View view4 = view2;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api20Impl.requestApplyInsets(view4);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view3) {
                    }
                });
            }
            Bundle bundle3 = fragment.mSavedFragmentState;
            fragment.onViewCreated(fragment.mView, bundle3 != null ? bundle3.getBundle("savedInstanceState") : null);
            fragment.mChildFragmentManager.dispatchStateChange(2);
            this.mDispatcher.dispatchOnFragmentViewCreated(false);
            int visibility = fragment.mView.getVisibility();
            fragment.ensureAnimationInfo().mPostOnViewCreatedAlpha = fragment.mView.getAlpha();
            if (fragment.mContainer != null && visibility == 0) {
                View findFocus = fragment.mView.findFocus();
                if (findFocus != null) {
                    fragment.ensureAnimationInfo().mFocusedView = findFocus;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFocus.toString();
                        Objects.toString(fragment);
                    }
                }
                fragment.mView.setAlpha(0.0f);
            }
        }
        fragment.mState = 2;
    }

    public final void destroy() {
        Fragment findActiveFragment;
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "movefrom CREATED: " + fragment);
        }
        boolean z = true;
        int i = 0;
        boolean z2 = fragment.mRemoving && !fragment.isInBackStack();
        FragmentStore fragmentStore = this.mFragmentStore;
        if (z2) {
            fragmentStore.setSavedState(null, fragment.mWho);
        }
        if (!z2) {
            FragmentManagerViewModel fragmentManagerViewModel = fragmentStore.mNonConfig;
            if (!((fragmentManagerViewModel.mRetainedFragments.containsKey(fragment.mWho) && fragmentManagerViewModel.mStateAutomaticallySaved) ? fragmentManagerViewModel.mHasBeenCleared : true)) {
                String str = fragment.mTargetWho;
                if (str != null && (findActiveFragment = fragmentStore.findActiveFragment(str)) != null && findActiveFragment.mRetainInstance) {
                    fragment.mTarget = findActiveFragment;
                }
                fragment.mState = 0;
                return;
            }
        }
        FragmentHostCallback fragmentHostCallback = fragment.mHost;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            z = fragmentStore.mNonConfig.mHasBeenCleared;
        } else {
            Context context = fragmentHostCallback.context;
            if (context instanceof Activity) {
                z = true ^ ((Activity) context).isChangingConfigurations();
            }
        }
        if (z2 || z) {
            fragmentStore.mNonConfig.clearNonConfigState(fragment, false);
        }
        fragment.mChildFragmentManager.dispatchDestroy();
        fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        fragment.mState = 0;
        fragment.mCalled = false;
        fragment.mIsCreated = false;
        fragment.onDestroy();
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onDestroy()");
        }
        this.mDispatcher.dispatchOnFragmentDestroyed(false);
        ArrayList arrayList = (ArrayList) fragmentStore.getActiveFragmentStateManagers();
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FragmentStateManager fragmentStateManager = (FragmentStateManager) obj;
            if (fragmentStateManager != null) {
                String str2 = fragment.mWho;
                Fragment fragment2 = fragmentStateManager.mFragment;
                if (str2.equals(fragment2.mTargetWho)) {
                    fragment2.mTarget = fragment;
                    fragment2.mTargetWho = null;
                }
            }
        }
        String str3 = fragment.mTargetWho;
        if (str3 != null) {
            fragment.mTarget = fragmentStore.findActiveFragment(str3);
        }
        fragmentStore.makeInactive(this);
    }

    public final void destroyFragmentView() {
        View view;
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "movefrom CREATE_VIEW: " + fragment);
        }
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null && (view = fragment.mView) != null) {
            viewGroup.removeView(view);
        }
        fragment.mChildFragmentManager.dispatchStateChange(1);
        if (fragment.mView != null) {
            FragmentViewLifecycleOwner fragmentViewLifecycleOwner = fragment.mViewLifecycleOwner;
            fragmentViewLifecycleOwner.initialize();
            if (fragmentViewLifecycleOwner.mLifecycleRegistry.state.isAtLeast(Lifecycle.State.CREATED)) {
                fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            }
        }
        fragment.mState = 1;
        fragment.mCalled = false;
        fragment.onDestroyView();
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onDestroyView()");
        }
        SparseArrayCompat sparseArrayCompat = new LoaderManagerImpl(fragment, fragment.getViewModelStore()).mLoaderViewModel.mLoaders;
        int size = sparseArrayCompat.size();
        for (int i = 0; i < size; i++) {
            ((LoaderManagerImpl.LoaderInfo) sparseArrayCompat.valueAt(i)).getClass();
        }
        fragment.mPerformedCreateView = false;
        this.mDispatcher.dispatchOnFragmentViewDestroyed(false);
        fragment.mContainer = null;
        fragment.mView = null;
        fragment.mFragmentTransitionHelper = null;
        Log.e("FragmentManager", fragment + " View is null");
        fragment.mViewLifecycleOwner = null;
        fragment.mViewLifecycleOwnerLiveData.setValue(null);
        fragment.mInLayout = false;
    }

    public final void detach() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "movefrom ATTACHED: " + fragment);
        }
        fragment.mState = -1;
        fragment.mCalled = false;
        fragment.onDetach();
        fragment.mLayoutInflater = null;
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onDetach()");
        }
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        if (!fragmentManagerImpl.mDestroyed) {
            fragmentManagerImpl.dispatchDestroy();
            fragment.mChildFragmentManager = new FragmentManagerImpl();
        }
        this.mDispatcher.dispatchOnFragmentDetached(false);
        fragment.mState = -1;
        fragment.mHost = null;
        fragment.mParentFragment = null;
        fragment.mFragmentManager = null;
        if (!fragment.mRemoving || fragment.isInBackStack()) {
            FragmentManagerViewModel fragmentManagerViewModel = this.mFragmentStore.mNonConfig;
            if (!((fragmentManagerViewModel.mRetainedFragments.containsKey(fragment.mWho) && fragmentManagerViewModel.mStateAutomaticallySaved) ? fragmentManagerViewModel.mHasBeenCleared : true)) {
                return;
            }
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "initState called for fragment: " + fragment);
        }
        fragment.initState();
    }

    public final void ensureInflatedView() {
        Fragment fragment = this.mFragment;
        if (fragment.mFromLayout && fragment.mInLayout && !fragment.mPerformedCreateView) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "moveto CREATE_VIEW: " + fragment);
            }
            Bundle bundle = fragment.mSavedFragmentState;
            Bundle bundle2 = bundle != null ? bundle.getBundle("savedInstanceState") : null;
            LayoutInflater onGetLayoutInflater = fragment.onGetLayoutInflater(bundle2);
            fragment.mLayoutInflater = onGetLayoutInflater;
            fragment.performCreateView(onGetLayoutInflater, null, bundle2);
            View view = fragment.mView;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                fragment.mView.setTag(R.id.fragment_container_view_tag, fragment);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                Bundle bundle3 = fragment.mSavedFragmentState;
                fragment.onViewCreated(fragment.mView, bundle3 != null ? bundle3.getBundle("savedInstanceState") : null);
                fragment.mChildFragmentManager.dispatchStateChange(2);
                this.mDispatcher.dispatchOnFragmentViewCreated(false);
                fragment.mState = 2;
            }
        }
    }

    public final void moveToExpectedState() {
        ViewGroup viewGroup;
        ViewGroup viewGroup2;
        ViewGroup viewGroup3;
        boolean z = this.mMovingToState;
        Fragment fragment = this.mFragment;
        if (z) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Objects.toString(fragment);
                return;
            }
            return;
        }
        try {
            this.mMovingToState = true;
            boolean z2 = false;
            while (true) {
                int computeExpectedState = computeExpectedState();
                int i = fragment.mState;
                FragmentStore fragmentStore = this.mFragmentStore;
                if (computeExpectedState == i) {
                    if (!z2 && i == -1 && fragment.mRemoving && !fragment.isInBackStack()) {
                        if (FragmentManager.isLoggingEnabled(3)) {
                            Log.d("FragmentManager", "Cleaning up state of never attached fragment: " + fragment);
                        }
                        fragmentStore.mNonConfig.clearNonConfigState(fragment, true);
                        fragmentStore.makeInactive(this);
                        if (FragmentManager.isLoggingEnabled(3)) {
                            Log.d("FragmentManager", "initState called for fragment: " + fragment);
                        }
                        fragment.initState();
                    }
                    if (fragment.mHiddenChanged) {
                        if (fragment.mView != null && (viewGroup = fragment.mContainer) != null) {
                            SpecialEffectsController orCreateController = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager());
                            if (fragment.mHidden) {
                                orCreateController.getClass();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(fragment);
                                }
                                orCreateController.enqueue(SpecialEffectsController.Operation.State.GONE, SpecialEffectsController.Operation.LifecycleImpact.NONE, this);
                            } else {
                                orCreateController.getClass();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(fragment);
                                }
                                orCreateController.enqueue(SpecialEffectsController.Operation.State.VISIBLE, SpecialEffectsController.Operation.LifecycleImpact.NONE, this);
                            }
                        }
                        FragmentManager fragmentManager = fragment.mFragmentManager;
                        if (fragmentManager != null && fragment.mAdded && FragmentManager.isMenuAvailable(fragment)) {
                            fragmentManager.mNeedMenuInvalidate = true;
                        }
                        fragment.mHiddenChanged = false;
                        fragment.mChildFragmentManager.dispatchOnHiddenChanged();
                    }
                    this.mMovingToState = false;
                    return;
                }
                if (computeExpectedState <= i) {
                    switch (i - 1) {
                        case -1:
                            detach();
                            break;
                        case 0:
                            destroy();
                            break;
                        case 1:
                            destroyFragmentView();
                            fragment.mState = 1;
                            break;
                        case 2:
                            fragment.mInLayout = false;
                            fragment.mState = 2;
                            break;
                        case 3:
                            if (FragmentManager.isLoggingEnabled(3)) {
                                Log.d("FragmentManager", "movefrom ACTIVITY_CREATED: " + fragment);
                            }
                            if (fragment.mView != null && fragment.mSavedViewState == null) {
                                saveViewState();
                            }
                            if (fragment.mView != null && (viewGroup2 = fragment.mContainer) != null) {
                                SpecialEffectsController orCreateController2 = SpecialEffectsController.getOrCreateController(viewGroup2, fragment.getParentFragmentManager());
                                orCreateController2.getClass();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(fragment);
                                }
                                orCreateController2.enqueue(SpecialEffectsController.Operation.State.REMOVED, SpecialEffectsController.Operation.LifecycleImpact.REMOVING, this);
                            }
                            fragment.mState = 3;
                            break;
                        case 4:
                            stop();
                            break;
                        case 5:
                            fragment.mState = 5;
                            break;
                        case 6:
                            pause();
                            break;
                    }
                } else {
                    switch (i + 1) {
                        case 0:
                            attach();
                            break;
                        case 1:
                            create();
                            break;
                        case 2:
                            ensureInflatedView();
                            createView();
                            break;
                        case 3:
                            activityCreated();
                            break;
                        case 4:
                            if (fragment.mView != null && (viewGroup3 = fragment.mContainer) != null) {
                                SpecialEffectsController orCreateController3 = SpecialEffectsController.getOrCreateController(viewGroup3, fragment.getParentFragmentManager());
                                int visibility = fragment.mView.getVisibility();
                                SpecialEffectsController.Operation.State.Companion.getClass();
                                SpecialEffectsController.Operation.State from = SpecialEffectsController.Operation.State.Companion.from(visibility);
                                orCreateController3.getClass();
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Objects.toString(fragment);
                                }
                                orCreateController3.enqueue(from, SpecialEffectsController.Operation.LifecycleImpact.ADDING, this);
                            }
                            fragment.mState = 4;
                            break;
                        case 5:
                            start();
                            break;
                        case 6:
                            fragment.mState = 6;
                            break;
                        case 7:
                            resume();
                            break;
                    }
                }
                z2 = true;
            }
        } catch (Throwable th) {
            this.mMovingToState = false;
            throw th;
        }
    }

    public final void pause() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "movefrom RESUMED: " + fragment);
        }
        fragment.mChildFragmentManager.dispatchStateChange(5);
        if (fragment.mView != null) {
            fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        }
        fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        fragment.mState = 6;
        fragment.mCalled = true;
        this.mDispatcher.dispatchOnFragmentPaused(false);
    }

    public final void restoreState(ClassLoader classLoader) {
        Fragment fragment = this.mFragment;
        Bundle bundle = fragment.mSavedFragmentState;
        if (bundle == null) {
            return;
        }
        bundle.setClassLoader(classLoader);
        if (fragment.mSavedFragmentState.getBundle("savedInstanceState") == null) {
            fragment.mSavedFragmentState.putBundle("savedInstanceState", new Bundle());
        }
        try {
            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray("viewState");
            fragment.mSavedViewRegistryState = fragment.mSavedFragmentState.getBundle("viewRegistryState");
            FragmentState fragmentState = (FragmentState) fragment.mSavedFragmentState.getParcelable("state");
            if (fragmentState != null) {
                fragment.mTargetWho = fragmentState.mTargetWho;
                fragment.mTargetRequestCode = fragmentState.mTargetRequestCode;
                fragment.mUserVisibleHint = fragmentState.mUserVisibleHint;
            }
            if (fragment.mUserVisibleHint) {
                return;
            }
            fragment.mDeferStart = true;
        } catch (BadParcelableException e) {
            throw new IllegalStateException("Failed to restore view hierarchy state for fragment " + fragment, e);
        }
    }

    public final void resume() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "moveto RESUMED: " + fragment);
        }
        Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
        View view = animationInfo == null ? null : animationInfo.mFocusedView;
        if (view != null) {
            if (view != fragment.mView) {
                for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
                    if (parent != fragment.mView) {
                    }
                }
            }
            view.requestFocus();
            if (FragmentManager.isLoggingEnabled(2)) {
                view.toString();
                Objects.toString(fragment);
                Objects.toString(fragment.mView.findFocus());
            }
        }
        fragment.ensureAnimationInfo().mFocusedView = null;
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mChildFragmentManager.execPendingActions(true);
        fragment.mState = 7;
        fragment.mCalled = false;
        fragment.onResume();
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onResume()");
        }
        LifecycleRegistry lifecycleRegistry = fragment.mLifecycleRegistry;
        Lifecycle.Event event = Lifecycle.Event.ON_RESUME;
        lifecycleRegistry.handleLifecycleEvent(event);
        if (fragment.mView != null) {
            fragment.mViewLifecycleOwner.handleLifecycleEvent(event);
        }
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(7);
        this.mDispatcher.dispatchOnFragmentResumed(false);
        this.mFragmentStore.setSavedState(null, fragment.mWho);
        fragment.mSavedFragmentState = null;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
    }

    public final void saveViewState() {
        Fragment fragment = this.mFragment;
        if (fragment.mView == null) {
            return;
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(fragment);
            Objects.toString(fragment.mView);
        }
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        fragment.mView.saveHierarchyState(sparseArray);
        if (sparseArray.size() > 0) {
            fragment.mSavedViewState = sparseArray;
        }
        Bundle bundle = new Bundle();
        fragment.mViewLifecycleOwner.mSavedStateRegistryController.performSave(bundle);
        if (bundle.isEmpty()) {
            return;
        }
        fragment.mSavedViewRegistryState = bundle;
    }

    public final void start() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "moveto STARTED: " + fragment);
        }
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mChildFragmentManager.execPendingActions(true);
        fragment.mState = 5;
        fragment.mCalled = false;
        fragment.onStart();
        if (!fragment.mCalled) {
            throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onStart()");
        }
        LifecycleRegistry lifecycleRegistry = fragment.mLifecycleRegistry;
        Lifecycle.Event event = Lifecycle.Event.ON_START;
        lifecycleRegistry.handleLifecycleEvent(event);
        if (fragment.mView != null) {
            fragment.mViewLifecycleOwner.handleLifecycleEvent(event);
        }
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(5);
        this.mDispatcher.dispatchOnFragmentStarted(false);
    }

    public final void stop() {
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "movefrom STARTED: " + fragment);
        }
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        fragmentManagerImpl.mStopped = true;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = true;
        fragmentManagerImpl.dispatchStateChange(4);
        if (fragment.mView != null) {
            fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
        fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        fragment.mState = 4;
        fragment.mCalled = false;
        fragment.onStop();
        if (fragment.mCalled) {
            this.mDispatcher.dispatchOnFragmentStopped(false);
            return;
        }
        throw new SuperNotCalledException("Fragment " + fragment + " did not call through to super.onStop()");
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, ClassLoader classLoader, FragmentFactory fragmentFactory, Bundle bundle) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        FragmentState fragmentState = (FragmentState) bundle.getParcelable("state");
        Fragment instantiate = fragmentFactory.instantiate(classLoader, fragmentState.mClassName);
        instantiate.mWho = fragmentState.mWho;
        instantiate.mFromLayout = fragmentState.mFromLayout;
        instantiate.mInDynamicContainer = fragmentState.mInDynamicContainer;
        instantiate.mRestored = true;
        instantiate.mFragmentId = fragmentState.mFragmentId;
        instantiate.mContainerId = fragmentState.mContainerId;
        instantiate.mTag = fragmentState.mTag;
        instantiate.mRetainInstance = fragmentState.mRetainInstance;
        instantiate.mRemoving = fragmentState.mRemoving;
        instantiate.mDetached = fragmentState.mDetached;
        instantiate.mHidden = fragmentState.mHidden;
        instantiate.mMaxState = Lifecycle.State.values()[fragmentState.mMaxLifecycleState];
        instantiate.mTargetWho = fragmentState.mTargetWho;
        instantiate.mTargetRequestCode = fragmentState.mTargetRequestCode;
        instantiate.mUserVisibleHint = fragmentState.mUserVisibleHint;
        this.mFragment = instantiate;
        instantiate.mSavedFragmentState = bundle;
        Bundle bundle2 = bundle.getBundle("arguments");
        if (bundle2 != null) {
            bundle2.setClassLoader(classLoader);
        }
        instantiate.setArguments(bundle2);
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(instantiate);
        }
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, Fragment fragment, Bundle bundle) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
        fragment.mBackStackNesting = 0;
        fragment.mInLayout = false;
        fragment.mAdded = false;
        Fragment fragment2 = fragment.mTarget;
        fragment.mTargetWho = fragment2 != null ? fragment2.mWho : null;
        fragment.mTarget = null;
        fragment.mSavedFragmentState = bundle;
        fragment.mArguments = bundle.getBundle("arguments");
    }
}
