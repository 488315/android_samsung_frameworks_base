package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.collection.SparseArrayCompat;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.SpecialEffectsController;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.fragment.app.strictmode.WrongFragmentContainerViolation;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentStateManager {
    public final FragmentLifecycleCallbacksDispatcher mDispatcher;
    public final Fragment mFragment;
    public final FragmentStore mFragmentStore;
    public boolean mMovingToState = false;
    public int mFragmentManagerState = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.FragmentStateManager$2 */
    public abstract /* synthetic */ class AbstractC02452 {
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
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mState = 3;
        fragment.mCalled = false;
        fragment.onActivityCreated(bundle);
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onActivityCreated()"));
        }
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "moveto RESTORE_VIEW_STATE: " + fragment);
        }
        View view = fragment.mView;
        if (view != null) {
            Bundle bundle2 = fragment.mSavedFragmentState;
            SparseArray<Parcelable> sparseArray = fragment.mSavedViewState;
            if (sparseArray != null) {
                view.restoreHierarchyState(sparseArray);
                fragment.mSavedViewState = null;
            }
            if (fragment.mView != null) {
                fragment.mViewLifecycleOwner.mSavedStateRegistryController.performRestore(fragment.mSavedViewRegistryState);
                fragment.mSavedViewRegistryState = null;
            }
            fragment.mCalled = false;
            fragment.onViewStateRestored(bundle2);
            if (!fragment.mCalled) {
                throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onViewStateRestored()"));
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
        View view;
        View view2;
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.getClass();
        Fragment fragment = this.mFragment;
        ViewGroup viewGroup = fragment.mContainer;
        int i = -1;
        if (viewGroup != null) {
            ArrayList arrayList = fragmentStore.mAdded;
            int indexOf = arrayList.indexOf(fragment);
            int i2 = indexOf - 1;
            while (true) {
                if (i2 < 0) {
                    while (true) {
                        indexOf++;
                        if (indexOf >= arrayList.size()) {
                            break;
                        }
                        Fragment fragment2 = (Fragment) arrayList.get(indexOf);
                        if (fragment2.mContainer == viewGroup && (view = fragment2.mView) != null) {
                            i = viewGroup.indexOfChild(view);
                            break;
                        }
                    }
                } else {
                    Fragment fragment3 = (Fragment) arrayList.get(i2);
                    if (fragment3.mContainer == viewGroup && (view2 = fragment3.mView) != null) {
                        i = viewGroup.indexOfChild(view2) + 1;
                        break;
                    }
                    i2--;
                }
            }
        }
        fragment.mContainer.addView(fragment.mView, i);
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
                throw new IllegalStateException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, fragment.mTargetWho, " that does not belong to this FragmentManager!"));
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
        Iterator it = fragment.mOnPreAttachedListeners.iterator();
        while (it.hasNext()) {
            ((Fragment.OnPreAttachedListener) it.next()).onPreAttached();
        }
        fragment.mOnPreAttachedListeners.clear();
        fragment.mChildFragmentManager.attachController(fragment.mHost, fragment.createFragmentContainer(), fragment);
        fragment.mState = 0;
        fragment.mCalled = false;
        fragment.onAttach(fragment.mHost.mContext);
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onAttach()"));
        }
        Iterator it2 = fragment.mFragmentManager.mOnAttachListeners.iterator();
        while (it2.hasNext()) {
            ((FragmentOnAttachListener) it2.next()).onAttachFragment$1();
        }
        FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(0);
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentAttached(false);
    }

    public final int computeExpectedState() {
        SpecialEffectsController.Operation operation;
        SpecialEffectsController.Operation.LifecycleImpact lifecycleImpact;
        Fragment fragment = this.mFragment;
        if (fragment.mFragmentManager == null) {
            return fragment.mState;
        }
        int i = this.mFragmentManagerState;
        int i2 = AbstractC02452.$SwitchMap$androidx$lifecycle$Lifecycle$State[fragment.mMaxState.ordinal()];
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
        if (!fragment.mAdded) {
            i = Math.min(i, 1);
        }
        ViewGroup viewGroup = fragment.mContainer;
        SpecialEffectsController.Operation.LifecycleImpact lifecycleImpact2 = null;
        if (viewGroup != null) {
            SpecialEffectsController orCreateController = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager().getSpecialEffectsControllerFactory());
            orCreateController.getClass();
            SpecialEffectsController.Operation findPendingOperation = orCreateController.findPendingOperation(fragment);
            if (findPendingOperation != null) {
                lifecycleImpact = findPendingOperation.mLifecycleImpact;
            } else {
                Iterator it = orCreateController.mRunningOperations.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        operation = null;
                        break;
                    }
                    operation = (SpecialEffectsController.Operation) it.next();
                    if (operation.mFragment.equals(fragment) && !operation.mIsCanceled) {
                        break;
                    }
                }
                if (operation != null) {
                    lifecycleImpact = operation.mLifecycleImpact;
                }
            }
            lifecycleImpact2 = lifecycleImpact;
        }
        if (lifecycleImpact2 == SpecialEffectsController.Operation.LifecycleImpact.ADDING) {
            i = Math.min(i, 6);
        } else if (lifecycleImpact2 == SpecialEffectsController.Operation.LifecycleImpact.REMOVING) {
            i = Math.max(i, 3);
        } else if (fragment.mRemoving) {
            i = fragment.isInBackStack() ? Math.min(i, 1) : Math.min(i, -1);
        }
        if (fragment.mDeferStart && fragment.mState < 5) {
            i = Math.min(i, 4);
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(fragment);
        }
        return i;
    }

    public final void create() {
        Parcelable parcelable;
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        final Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "moveto CREATED: " + fragment);
        }
        if (fragment.mIsCreated) {
            Bundle bundle = fragment.mSavedFragmentState;
            if (bundle != null && (parcelable = bundle.getParcelable("android:support:fragments")) != null) {
                fragment.mChildFragmentManager.restoreSaveStateInternal(parcelable);
                FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
                fragmentManagerImpl.mStateSaved = false;
                fragmentManagerImpl.mStopped = false;
                fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
                fragmentManagerImpl.dispatchStateChange(1);
            }
            fragment.mState = 1;
            return;
        }
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentPreCreated(false);
        Bundle bundle2 = fragment.mSavedFragmentState;
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mState = 1;
        fragment.mCalled = false;
        fragment.mLifecycleRegistry.addObserver(new LifecycleEventObserver() { // from class: androidx.fragment.app.Fragment.6
            public C02276() {
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
        fragment.mSavedStateRegistryController.performRestore(bundle2);
        fragment.onCreate(bundle2);
        fragment.mIsCreated = true;
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onCreate()"));
        }
        fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentCreated(false);
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
        LayoutInflater performGetLayoutInflater = fragment.performGetLayoutInflater(fragment.mSavedFragmentState);
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup == null) {
            int i = fragment.mContainerId;
            if (i == 0) {
                viewGroup = null;
            } else {
                if (i == -1) {
                    throw new IllegalArgumentException(Fragment$5$$ExternalSyntheticOutline0.m37m("Cannot create fragment ", fragment, " for a container view with no id"));
                }
                viewGroup = (ViewGroup) fragment.mFragmentManager.mContainer.onFindViewById(i);
                if (viewGroup == null) {
                    if (!fragment.mRestored) {
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
        fragment.performCreateView(performGetLayoutInflater, viewGroup, fragment.mSavedFragmentState);
        View view = fragment.mView;
        if (view != null) {
            view.setSaveFromParentEnabled(false);
            fragment.mView.setTag(R.id.fragment_container_view_tag, fragment);
            if (viewGroup != null) {
                addViewToContainer();
            }
            if (fragment.mHidden) {
                fragment.mView.setVisibility(8);
            }
            View view2 = fragment.mView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isAttachedToWindow(view2)) {
                ViewCompat.Api20Impl.requestApplyInsets(fragment.mView);
            } else {
                final View view3 = fragment.mView;
                view3.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this) { // from class: androidx.fragment.app.FragmentStateManager.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view4) {
                        view3.removeOnAttachStateChangeListener(this);
                        View view5 = view3;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api20Impl.requestApplyInsets(view5);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view4) {
                    }
                });
            }
            fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
            fragment.mChildFragmentManager.dispatchStateChange(2);
            this.mDispatcher.dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void destroy() {
        boolean z;
        Fragment findActiveFragment;
        boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
        Fragment fragment = this.mFragment;
        if (isLoggingEnabled) {
            Log.d("FragmentManager", "movefrom CREATED: " + fragment);
        }
        boolean z2 = true;
        boolean z3 = fragment.mRemoving && !fragment.isInBackStack();
        FragmentStore fragmentStore = this.mFragmentStore;
        if (z3 && !fragment.mBeingSaved) {
            fragmentStore.setSavedState(fragment.mWho, null);
        }
        if (!z3) {
            FragmentManagerViewModel fragmentManagerViewModel = fragmentStore.mNonConfig;
            if (!((fragmentManagerViewModel.mRetainedFragments.containsKey(fragment.mWho) && fragmentManagerViewModel.mStateAutomaticallySaved) ? fragmentManagerViewModel.mHasBeenCleared : true)) {
                z = false;
                if (z) {
                    String str = fragment.mTargetWho;
                    if (str != null && (findActiveFragment = fragmentStore.findActiveFragment(str)) != null && findActiveFragment.mRetainInstance) {
                        fragment.mTarget = findActiveFragment;
                    }
                    fragment.mState = 0;
                    return;
                }
                FragmentHostCallback fragmentHostCallback = fragment.mHost;
                if (fragmentHostCallback instanceof ViewModelStoreOwner) {
                    z2 = fragmentStore.mNonConfig.mHasBeenCleared;
                } else {
                    Context context = fragmentHostCallback.mContext;
                    if (context instanceof Activity) {
                        z2 = true ^ ((Activity) context).isChangingConfigurations();
                    }
                }
                if ((z3 && !fragment.mBeingSaved) || z2) {
                    fragmentStore.mNonConfig.clearNonConfigState(fragment);
                }
                fragment.mChildFragmentManager.dispatchDestroy();
                fragment.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
                fragment.mState = 0;
                fragment.mCalled = false;
                fragment.mIsCreated = false;
                fragment.onDestroy();
                if (!fragment.mCalled) {
                    throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onDestroy()"));
                }
                this.mDispatcher.dispatchOnFragmentDestroyed(false);
                Iterator it = ((ArrayList) fragmentStore.getActiveFragmentStateManagers()).iterator();
                while (it.hasNext()) {
                    FragmentStateManager fragmentStateManager = (FragmentStateManager) it.next();
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
                return;
            }
        }
        z = true;
        if (z) {
        }
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
            if (fragmentViewLifecycleOwner.mLifecycleRegistry.mState.isAtLeast(Lifecycle.State.CREATED)) {
                fragment.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
            }
        }
        fragment.mState = 1;
        fragment.mCalled = false;
        fragment.onDestroyView();
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onDestroyView()"));
        }
        SparseArrayCompat sparseArrayCompat = new LoaderManagerImpl(fragment, fragment.getViewModelStore()).mLoaderViewModel.mLoaders;
        int size = sparseArrayCompat.size();
        for (int i = 0; i < size; i++) {
            ((LoaderManagerImpl.LoaderInfo) sparseArrayCompat.valueAt(i)).markForRedelivery();
        }
        fragment.mPerformedCreateView = false;
        this.mDispatcher.dispatchOnFragmentViewDestroyed(false);
        fragment.mContainer = null;
        fragment.mView = null;
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
        boolean z = false;
        fragment.mCalled = false;
        fragment.onDetach();
        fragment.mLayoutInflater = null;
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onDetach()"));
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
        boolean z2 = true;
        if (fragment.mRemoving && !fragment.isInBackStack()) {
            z = true;
        }
        if (!z) {
            FragmentManagerViewModel fragmentManagerViewModel = this.mFragmentStore.mNonConfig;
            if (fragmentManagerViewModel.mRetainedFragments.containsKey(fragment.mWho) && fragmentManagerViewModel.mStateAutomaticallySaved) {
                z2 = fragmentManagerViewModel.mHasBeenCleared;
            }
            if (!z2) {
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
            fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), null, fragment.mSavedFragmentState);
            View view = fragment.mView;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                fragment.mView.setTag(R.id.fragment_container_view_tag, fragment);
                if (fragment.mHidden) {
                    fragment.mView.setVisibility(8);
                }
                fragment.onViewCreated(fragment.mView, fragment.mSavedFragmentState);
                fragment.mChildFragmentManager.dispatchStateChange(2);
                this.mDispatcher.dispatchOnFragmentViewCreated(fragment, fragment.mView, fragment.mSavedFragmentState, false);
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
                    if (!z2 && i == -1 && fragment.mRemoving && !fragment.isInBackStack() && !fragment.mBeingSaved) {
                        if (FragmentManager.isLoggingEnabled(3)) {
                            Log.d("FragmentManager", "Cleaning up state of never attached fragment: " + fragment);
                        }
                        fragmentStore.mNonConfig.clearNonConfigState(fragment);
                        fragmentStore.makeInactive(this);
                        if (FragmentManager.isLoggingEnabled(3)) {
                            Log.d("FragmentManager", "initState called for fragment: " + fragment);
                        }
                        fragment.initState();
                    }
                    if (fragment.mHiddenChanged) {
                        if (fragment.mView != null && (viewGroup = fragment.mContainer) != null) {
                            SpecialEffectsController orCreateController = SpecialEffectsController.getOrCreateController(viewGroup, fragment.getParentFragmentManager().getSpecialEffectsControllerFactory());
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
                    return;
                }
                if (computeExpectedState <= i) {
                    switch (i - 1) {
                        case -1:
                            detach();
                            break;
                        case 0:
                            if (fragment.mBeingSaved) {
                                if (((FragmentState) fragmentStore.mSavedState.get(fragment.mWho)) == null) {
                                    saveState();
                                }
                            }
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
                            if (fragment.mBeingSaved) {
                                saveState();
                            } else if (fragment.mView != null && fragment.mSavedViewState == null) {
                                saveViewState();
                            }
                            if (fragment.mView != null && (viewGroup2 = fragment.mContainer) != null) {
                                SpecialEffectsController orCreateController2 = SpecialEffectsController.getOrCreateController(viewGroup2, fragment.getParentFragmentManager().getSpecialEffectsControllerFactory());
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
                                SpecialEffectsController orCreateController3 = SpecialEffectsController.getOrCreateController(viewGroup3, fragment.getParentFragmentManager().getSpecialEffectsControllerFactory());
                                SpecialEffectsController.Operation.State from = SpecialEffectsController.Operation.State.from(fragment.mView.getVisibility());
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
        } finally {
            this.mMovingToState = false;
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
        fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray("android:view_state");
        fragment.mSavedViewRegistryState = fragment.mSavedFragmentState.getBundle("android:view_registry_state");
        String string = fragment.mSavedFragmentState.getString("android:target_state");
        fragment.mTargetWho = string;
        if (string != null) {
            fragment.mTargetRequestCode = fragment.mSavedFragmentState.getInt("android:target_req_state", 0);
        }
        boolean z = fragment.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
        fragment.mUserVisibleHint = z;
        if (z) {
            return;
        }
        fragment.mDeferStart = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void resume() {
        boolean z;
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
                z = false;
                if (z) {
                    view.requestFocus();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        view.toString();
                        Objects.toString(fragment);
                        Objects.toString(fragment.mView.findFocus());
                    }
                }
            }
            z = true;
            if (z) {
            }
        }
        fragment.ensureAnimationInfo().mFocusedView = null;
        fragment.mChildFragmentManager.noteStateNotSaved();
        fragment.mChildFragmentManager.execPendingActions(true);
        fragment.mState = 7;
        fragment.mCalled = false;
        fragment.onResume();
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onResume()"));
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
        fragment.mSavedFragmentState = null;
        fragment.mSavedViewState = null;
        fragment.mSavedViewRegistryState = null;
    }

    public final Bundle saveBasicState() {
        Bundle bundle = new Bundle();
        Fragment fragment = this.mFragment;
        fragment.onSaveInstanceState(bundle);
        fragment.mSavedStateRegistryController.performSave(bundle);
        bundle.putParcelable("android:support:fragments", fragment.mChildFragmentManager.saveAllStateInternal());
        this.mDispatcher.dispatchOnFragmentSaveInstanceState(false);
        if (bundle.isEmpty()) {
            bundle = null;
        }
        if (fragment.mView != null) {
            saveViewState();
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.mSavedViewState);
        }
        if (fragment.mSavedViewRegistryState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBundle("android:view_registry_state", fragment.mSavedViewRegistryState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", fragment.mUserVisibleHint);
        }
        return bundle;
    }

    public final void saveState() {
        Fragment fragment = this.mFragment;
        FragmentState fragmentState = new FragmentState(fragment);
        if (fragment.mState <= -1 || fragmentState.mSavedFragmentState != null) {
            fragmentState.mSavedFragmentState = fragment.mSavedFragmentState;
        } else {
            Bundle saveBasicState = saveBasicState();
            fragmentState.mSavedFragmentState = saveBasicState;
            if (fragment.mTargetWho != null) {
                if (saveBasicState == null) {
                    fragmentState.mSavedFragmentState = new Bundle();
                }
                fragmentState.mSavedFragmentState.putString("android:target_state", fragment.mTargetWho);
                int i = fragment.mTargetRequestCode;
                if (i != 0) {
                    fragmentState.mSavedFragmentState.putInt("android:target_req_state", i);
                }
            }
        }
        this.mFragmentStore.setSavedState(fragment.mWho, fragmentState);
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
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onStart()"));
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
        if (!fragment.mCalled) {
            throw new SuperNotCalledException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " did not call through to super.onStop()"));
        }
        this.mDispatcher.dispatchOnFragmentStopped(false);
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, ClassLoader classLoader, FragmentFactory fragmentFactory, FragmentState fragmentState) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        Fragment instantiate = fragmentFactory.instantiate(classLoader, fragmentState.mClassName);
        Bundle bundle = fragmentState.mArguments;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
        instantiate.setArguments(fragmentState.mArguments);
        instantiate.mWho = fragmentState.mWho;
        instantiate.mFromLayout = fragmentState.mFromLayout;
        instantiate.mRestored = true;
        instantiate.mFragmentId = fragmentState.mFragmentId;
        instantiate.mContainerId = fragmentState.mContainerId;
        instantiate.mTag = fragmentState.mTag;
        instantiate.mRetainInstance = fragmentState.mRetainInstance;
        instantiate.mRemoving = fragmentState.mRemoving;
        instantiate.mDetached = fragmentState.mDetached;
        instantiate.mHidden = fragmentState.mHidden;
        instantiate.mMaxState = Lifecycle.State.values()[fragmentState.mMaxLifecycleState];
        Bundle bundle2 = fragmentState.mSavedFragmentState;
        if (bundle2 != null) {
            instantiate.mSavedFragmentState = bundle2;
        } else {
            instantiate.mSavedFragmentState = new Bundle();
        }
        this.mFragment = instantiate;
        if (FragmentManager.isLoggingEnabled(2)) {
            Objects.toString(instantiate);
        }
    }

    public FragmentStateManager(FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, FragmentStore fragmentStore, Fragment fragment, FragmentState fragmentState) {
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
        Bundle bundle = fragmentState.mSavedFragmentState;
        if (bundle != null) {
            fragment.mSavedFragmentState = bundle;
        } else {
            fragment.mSavedFragmentState = new Bundle();
        }
    }
}
