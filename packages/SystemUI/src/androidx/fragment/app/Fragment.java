package androidx.fragment.app;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.fragment.app.strictmode.GetTargetFragmentUsageViolation;
import androidx.fragment.app.strictmode.SetTargetFragmentUsageViolation;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.preference.PreferenceFragmentCompat;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner {
    public static final Object USE_DEFAULT_TRANSITION = new Object();
    public boolean mAdded;
    public AnimationInfo mAnimationInfo;
    public Bundle mArguments;
    public int mBackStackNesting;
    public boolean mCalled;
    public FragmentManagerImpl mChildFragmentManager;
    public ViewGroup mContainer;
    public int mContainerId;
    public final int mContentLayoutId;
    public SavedStateViewModelFactory mDefaultFactory;
    public boolean mDeferStart;
    public boolean mDetached;
    public Fragment$$ExternalSyntheticLambda1 mDisposableHandle;
    public int mFragmentId;
    public FragmentManager mFragmentManager;
    public SeslFragmentTransitionHelper mFragmentTransitionHelper;
    public boolean mFromLayout;
    public boolean mHasMenu;
    public boolean mHidden;
    public boolean mHiddenChanged;
    public FragmentHostCallback mHost;
    public boolean mInDynamicContainer;
    public boolean mInLayout;
    public boolean mIsCreated;
    public Boolean mIsPrimaryNavigationFragment;
    public LayoutInflater mLayoutInflater;
    public LifecycleRegistry mLifecycleRegistry;
    public Lifecycle.State mMaxState;
    public final boolean mMenuVisible;
    public final ArrayList mOnPreAttachedListeners;
    public Fragment mParentFragment;
    public boolean mPerformedCreateView;
    public final AnonymousClass1 mPostponedDurationRunnable;
    public String mPreviousWho;
    public boolean mRemoving;
    public boolean mRestored;
    public boolean mRetainInstance;
    public Bundle mSavedFragmentState;
    public final AnonymousClass2 mSavedStateAttachListener;
    public SavedStateRegistryController mSavedStateRegistryController;
    public Bundle mSavedViewRegistryState;
    public SparseArray mSavedViewState;
    public int mState;
    public String mTag;
    public Fragment mTarget;
    public int mTargetRequestCode;
    public String mTargetWho;
    public boolean mTransitioning;
    public boolean mUserVisibleHint;
    public View mView;
    public FragmentViewLifecycleOwner mViewLifecycleOwner;
    public final MutableLiveData mViewLifecycleOwnerLiveData;
    public String mWho;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.fragment.app.Fragment$2, reason: invalid class name */
    public class AnonymousClass2 extends OnPreAttachedListener {
        public AnonymousClass2() {
            super();
        }

        @Override // androidx.fragment.app.Fragment.OnPreAttachedListener
        public final void onPreAttached() {
            Fragment fragment = Fragment.this;
            fragment.mSavedStateRegistryController.performAttach();
            SavedStateHandleSupport.enableSavedStateHandles(fragment);
            Bundle bundle = fragment.mSavedFragmentState;
            fragment.mSavedStateRegistryController.performRestore(bundle != null ? bundle.getBundle("registryState") : null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.fragment.app.Fragment$5, reason: invalid class name */
    public class AnonymousClass5 extends FragmentContainer {
        public AnonymousClass5() {
        }

        @Override // androidx.fragment.app.FragmentContainer
        public final View onFindViewById(int i) {
            Fragment fragment = Fragment.this;
            View view = fragment.mView;
            if (view != null) {
                return view.findViewById(i);
            }
            throw new IllegalStateException("Fragment " + fragment + " does not have a view");
        }

        @Override // androidx.fragment.app.FragmentContainer
        public final boolean onHasView() {
            return Fragment.this.mView != null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimationInfo {
        public int mEnterAnim;
        public int mExitAnim;
        public View mFocusedView;
        public boolean mIsPop;
        public int mNextTransition;
        public int mPopEnterAnim;
        public int mPopExitAnim;
        public float mPostOnViewCreatedAlpha;
        public final Object mReenterTransition;
        public final Object mReturnTransition;
        public final Object mSharedElementReturnTransition;
        public ArrayList mSharedElementSourceNames;
        public ArrayList mSharedElementTargetNames;

        public AnimationInfo() {
            Object obj = Fragment.USE_DEFAULT_TRANSITION;
            this.mReturnTransition = obj;
            this.mReenterTransition = obj;
            this.mSharedElementReturnTransition = obj;
            this.mPostOnViewCreatedAlpha = 1.0f;
            this.mFocusedView = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class InstantiationException extends RuntimeException {
        public InstantiationException(String str, Exception exc) {
            super(str, exc);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class OnPreAttachedListener {
        private OnPreAttachedListener() {
        }

        public abstract void onPreAttached();
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [androidx.fragment.app.Fragment$1] */
    public Fragment() {
        this.mState = -1;
        this.mWho = UUID.randomUUID().toString();
        this.mTargetWho = null;
        this.mIsPrimaryNavigationFragment = null;
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mMenuVisible = true;
        this.mUserVisibleHint = true;
        this.mPostponedDurationRunnable = new Runnable() { // from class: androidx.fragment.app.Fragment.1
            @Override // java.lang.Runnable
            public final void run() {
                Fragment fragment = Fragment.this;
                if (fragment.mAnimationInfo != null) {
                    fragment.ensureAnimationInfo().getClass();
                }
            }
        };
        this.mMaxState = Lifecycle.State.RESUMED;
        this.mViewLifecycleOwnerLiveData = new MutableLiveData();
        new AtomicInteger();
        this.mOnPreAttachedListeners = new ArrayList();
        this.mDisposableHandle = null;
        this.mSavedStateAttachListener = new AnonymousClass2();
        initLifecycle();
    }

    public FragmentContainer createFragmentContainer() {
        return new AnonymousClass5();
    }

    public final AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    public final FragmentActivity getActivity() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return (FragmentActivity) fragmentHostCallback.activity;
    }

    public final FragmentManager getChildFragmentManager() {
        if (this.mHost != null) {
            return this.mChildFragmentManager;
        }
        throw new IllegalStateException("Fragment " + this + " has not been attached yet.");
    }

    public final Context getContext() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return fragmentHostCallback.context;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final CreationExtras getDefaultViewModelCreationExtras() {
        Application application;
        Context applicationContext = requireContext().getApplicationContext();
        while (true) {
            if (!(applicationContext instanceof ContextWrapper)) {
                application = null;
                break;
            }
            if (applicationContext instanceof Application) {
                application = (Application) applicationContext;
                break;
            }
            applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
        }
        if (application == null && FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "Could not find Application instance from Context " + requireContext().getApplicationContext() + ", you will not be able to use AndroidViewModel with the default ViewModelProvider.Factory");
        }
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras();
        if (application != null) {
            mutableCreationExtras.set(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY, application);
        }
        mutableCreationExtras.set(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY, this);
        mutableCreationExtras.set(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY, this);
        Bundle bundle = this.mArguments;
        if (bundle != null) {
            mutableCreationExtras.set(SavedStateHandleSupport.DEFAULT_ARGS_KEY, bundle);
        }
        return mutableCreationExtras;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        Application application;
        if (this.mFragmentManager == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (this.mDefaultFactory == null) {
            Context applicationContext = requireContext().getApplicationContext();
            while (true) {
                if (!(applicationContext instanceof ContextWrapper)) {
                    application = null;
                    break;
                }
                if (applicationContext instanceof Application) {
                    application = (Application) applicationContext;
                    break;
                }
                applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
            }
            if (application == null && FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "Could not find Application instance from Context " + requireContext().getApplicationContext() + ", you will need CreationExtras to use AndroidViewModel with the default ViewModelProvider.Factory");
            }
            this.mDefaultFactory = new SavedStateViewModelFactory(application, this, this.mArguments);
        }
        return this.mDefaultFactory;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    public final int getMinimumMaxLifecycleState() {
        Lifecycle.State state = this.mMaxState;
        return (state == Lifecycle.State.INITIALIZED || this.mParentFragment == null) ? state.ordinal() : Math.min(state.ordinal(), this.mParentFragment.getMinimumMaxLifecycleState());
    }

    public final FragmentManager getParentFragmentManager() {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null) {
            return fragmentManager;
        }
        throw new IllegalStateException("Fragment " + this + " not associated with a fragment manager.");
    }

    public final Resources getResources() {
        return requireContext().getResources();
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.mSavedStateRegistryController.savedStateRegistry;
    }

    public final Fragment getTargetFragment(boolean z) {
        String str;
        if (z) {
            FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
            GetTargetFragmentUsageViolation getTargetFragmentUsageViolation = new GetTargetFragmentUsageViolation(this);
            FragmentStrictMode.INSTANCE.getClass();
            FragmentStrictMode.logIfDebuggingEnabled(getTargetFragmentUsageViolation);
            FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(this);
            if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_TARGET_FRAGMENT_USAGE) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, getClass(), GetTargetFragmentUsageViolation.class)) {
                FragmentStrictMode.handlePolicyViolation(nearestPolicy, getTargetFragmentUsageViolation);
            }
        }
        Fragment fragment = this.mTarget;
        if (fragment != null) {
            return fragment;
        }
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager == null || (str = this.mTargetWho) == null) {
            return null;
        }
        return fragmentManager.mFragmentStore.findActiveFragment(str);
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        if (this.mFragmentManager == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (getMinimumMaxLifecycleState() == Lifecycle.State.INITIALIZED.ordinal()) {
            throw new IllegalStateException("Calling getViewModelStore() before a Fragment reaches onCreate() when using setMaxLifecycle(INITIALIZED) is not supported");
        }
        FragmentManagerViewModel fragmentManagerViewModel = this.mFragmentManager.mNonConfig;
        ViewModelStore viewModelStore = (ViewModelStore) fragmentManagerViewModel.mViewModelStores.get(this.mWho);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        fragmentManagerViewModel.mViewModelStores.put(this.mWho, viewModelStore2);
        return viewModelStore2;
    }

    public final void initLifecycle() {
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        SavedStateRegistryController.Companion.getClass();
        this.mSavedStateRegistryController = SavedStateRegistryController.Companion.create(this);
        this.mDefaultFactory = null;
        if (this.mOnPreAttachedListeners.contains(this.mSavedStateAttachListener)) {
            return;
        }
        AnonymousClass2 anonymousClass2 = this.mSavedStateAttachListener;
        if (this.mState >= 0) {
            anonymousClass2.onPreAttached();
        } else {
            this.mOnPreAttachedListeners.add(anonymousClass2);
        }
    }

    public final void initState() {
        initLifecycle();
        this.mPreviousWho = this.mWho;
        this.mWho = UUID.randomUUID().toString();
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
    }

    public final boolean isHidden() {
        if (this.mHidden) {
            return true;
        }
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null) {
            Fragment fragment = this.mParentFragment;
            fragmentManager.getClass();
            if (fragment == null ? false : fragment.isHidden()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public final boolean isPredictiveBackEnabled() {
        Log.e("FragmentManager", "SeslFragmentTransitionHelper is not initiated.");
        return false;
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onAttach(Context context) {
        this.mCalled = true;
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if ((fragmentHostCallback == null ? null : fragmentHostCallback.activity) != null) {
            this.mCalled = true;
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public void onCreate(Bundle bundle) {
        this.mCalled = true;
        restoreChildFragmentState();
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl.mCurState >= 1) {
            return;
        }
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(1);
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public final void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        requireActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = this.mContentLayoutId;
        if (i != 0) {
            return layoutInflater.inflate(i, viewGroup, false);
        }
        return null;
    }

    public void onDestroy() {
        this.mCalled = true;
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    public LayoutInflater onGetLayoutInflater(Bundle bundle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        LayoutInflater onGetLayoutInflater = fragmentHostCallback.onGetLayoutInflater();
        onGetLayoutInflater.setFactory2(this.mChildFragmentManager.mLayoutInflaterFactory);
        return onGetLayoutInflater;
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
        this.mCalled = true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onStart() {
        this.mCalled = true;
    }

    public void onStop() {
        this.mCalled = true;
    }

    public void onViewStateRestored(Bundle bundle) {
        this.mCalled = true;
    }

    public void performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mChildFragmentManager.noteStateNotSaved();
        this.mPerformedCreateView = true;
        this.mViewLifecycleOwner = new FragmentViewLifecycleOwner(this, getViewModelStore(), new Runnable() { // from class: androidx.fragment.app.Fragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Fragment fragment = Fragment.this;
                FragmentViewLifecycleOwner fragmentViewLifecycleOwner = fragment.mViewLifecycleOwner;
                fragmentViewLifecycleOwner.mSavedStateRegistryController.performRestore(fragment.mSavedViewRegistryState);
                fragment.mSavedViewRegistryState = null;
            }
        });
        View onCreateView = onCreateView(layoutInflater, viewGroup, bundle);
        this.mView = onCreateView;
        if (onCreateView == null) {
            if (this.mViewLifecycleOwner.mLifecycleRegistry != null) {
                throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
            }
            this.mViewLifecycleOwner = null;
            return;
        }
        this.mViewLifecycleOwner.initialize();
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "Setting ViewLifecycleOwner on View " + this.mView + " for Fragment " + this);
        }
        ViewTreeLifecycleOwner.set(this.mViewLifecycleOwner, this.mView);
        ViewTreeViewModelStoreOwner.set(this.mView, this.mViewLifecycleOwner);
        ViewTreeSavedStateRegistryOwner.set(this.mView, this.mViewLifecycleOwner);
        this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner);
    }

    public final FragmentActivity requireActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
    }

    public final Context requireContext() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to a context.");
    }

    public final View requireView() {
        View view = this.mView;
        if (view != null) {
            return view;
        }
        throw new IllegalStateException("Fragment " + this + " did not return a View from onCreateView() or this was called before onCreateView().");
    }

    public final void restoreChildFragmentState() {
        Bundle bundle;
        Bundle bundle2 = this.mSavedFragmentState;
        if (bundle2 == null || (bundle = bundle2.getBundle("childFragmentManager")) == null) {
            return;
        }
        this.mChildFragmentManager.restoreSaveStateInternal(bundle);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(1);
    }

    public final void setAnimations(int i, int i2, int i3, int i4) {
        if (this.mAnimationInfo == null && i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return;
        }
        ensureAnimationInfo().mEnterAnim = i;
        ensureAnimationInfo().mExitAnim = i2;
        ensureAnimationInfo().mPopEnterAnim = i3;
        ensureAnimationInfo().mPopExitAnim = i4;
    }

    public final void setArguments(Bundle bundle) {
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null && (fragmentManager.mStateSaved || fragmentManager.mStopped)) {
            throw new IllegalStateException("Fragment already added and state has been saved");
        }
        this.mArguments = bundle;
    }

    public final void setTargetFragment(PreferenceFragmentCompat preferenceFragmentCompat) {
        FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
        SetTargetFragmentUsageViolation setTargetFragmentUsageViolation = new SetTargetFragmentUsageViolation(this, preferenceFragmentCompat, 0);
        FragmentStrictMode.INSTANCE.getClass();
        FragmentStrictMode.logIfDebuggingEnabled(setTargetFragmentUsageViolation);
        FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(this);
        if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_TARGET_FRAGMENT_USAGE) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, getClass(), SetTargetFragmentUsageViolation.class)) {
            FragmentStrictMode.handlePolicyViolation(nearestPolicy, setTargetFragmentUsageViolation);
        }
        FragmentManager fragmentManager = this.mFragmentManager;
        FragmentManager fragmentManager2 = preferenceFragmentCompat.mFragmentManager;
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw new IllegalArgumentException("Fragment " + preferenceFragmentCompat + " must share the same FragmentManager to be set as a target fragment");
        }
        for (Fragment fragment = preferenceFragmentCompat; fragment != null; fragment = fragment.getTargetFragment(false)) {
            if (super.equals(this)) {
                throw new IllegalArgumentException("Setting " + preferenceFragmentCompat + " as the target of " + this + " would create a target cycle");
            }
        }
        if (this.mFragmentManager == null || preferenceFragmentCompat.mFragmentManager == null) {
            this.mTargetWho = null;
            this.mTarget = preferenceFragmentCompat;
        } else {
            this.mTargetWho = preferenceFragmentCompat.mWho;
            this.mTarget = null;
        }
        this.mTargetRequestCode = 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} (");
        sb.append(this.mWho);
        if (this.mFragmentId != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            sb.append(" tag=");
            sb.append(this.mTag);
        }
        sb.append(")");
        return sb.toString();
    }

    public Fragment(int i) {
        this();
        this.mContentLayoutId = i;
    }

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    public void onViewCreated(View view, Bundle bundle) {
    }
}
