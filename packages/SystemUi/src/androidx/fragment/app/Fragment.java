package androidx.fragment.app;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner {
    public static final Object USE_DEFAULT_TRANSITION = new Object();
    public boolean mAdded;
    public AnimationInfo mAnimationInfo;
    public Bundle mArguments;
    public int mBackStackNesting;
    public boolean mBeingSaved;
    public boolean mCalled;
    public FragmentManagerImpl mChildFragmentManager;
    public ViewGroup mContainer;
    public int mContainerId;
    public final int mContentLayoutId;
    public SavedStateViewModelFactory mDefaultFactory;
    public boolean mDeferStart;
    public boolean mDetached;
    public int mFragmentId;
    public FragmentManager mFragmentManager;
    public boolean mFromLayout;
    public boolean mHasMenu;
    public boolean mHidden;
    public boolean mHiddenChanged;
    public FragmentHostCallback mHost;
    public boolean mInLayout;
    public boolean mIsCreated;
    public Boolean mIsPrimaryNavigationFragment;
    public LayoutInflater mLayoutInflater;
    public LifecycleRegistry mLifecycleRegistry;
    public Lifecycle.State mMaxState;
    public boolean mMenuVisible;
    public final ArrayList mOnPreAttachedListeners;
    public Fragment mParentFragment;
    public boolean mPerformedCreateView;
    public String mPreviousWho;
    public boolean mRemoving;
    public boolean mRestored;
    public boolean mRetainInstance;
    public Bundle mSavedFragmentState;
    public final C02252 mSavedStateAttachListener;
    public SavedStateRegistryController mSavedStateRegistryController;
    public Bundle mSavedViewRegistryState;
    public SparseArray mSavedViewState;
    public int mState;
    public String mTag;
    public Fragment mTarget;
    public int mTargetRequestCode;
    public String mTargetWho;
    public boolean mUserVisibleHint;
    public View mView;
    public FragmentViewLifecycleOwner mViewLifecycleOwner;
    public final MutableLiveData mViewLifecycleOwnerLiveData;
    public String mWho;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.Fragment$2 */
    public final class C02252 extends OnPreAttachedListener {
        public C02252() {
            super();
        }

        @Override // androidx.fragment.app.Fragment.OnPreAttachedListener
        public final void onPreAttached() {
            Fragment fragment = Fragment.this;
            fragment.mSavedStateRegistryController.performAttach();
            SavedStateHandleSupport.enableSavedStateHandles(fragment);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.Fragment$5 */
    public final class C02265 extends FragmentContainer {
        public C02265() {
        }

        @Override // androidx.fragment.app.FragmentContainer
        public final View onFindViewById(int i) {
            Fragment fragment = Fragment.this;
            View view = fragment.mView;
            if (view != null) {
                return view.findViewById(i);
            }
            throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " does not have a view"));
        }

        @Override // androidx.fragment.app.FragmentContainer
        public final boolean onHasView() {
            return Fragment.this.mView != null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationInfo {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class InstantiationException extends RuntimeException {
        public InstantiationException(String str, Exception exc) {
            super(str, exc);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnPreAttachedListener {
        private OnPreAttachedListener() {
        }

        public abstract void onPreAttached();
    }

    public Fragment() {
        this.mState = -1;
        this.mWho = UUID.randomUUID().toString();
        this.mTargetWho = null;
        this.mIsPrimaryNavigationFragment = null;
        this.mChildFragmentManager = new FragmentManagerImpl();
        this.mMenuVisible = true;
        this.mUserVisibleHint = true;
        new Runnable() { // from class: androidx.fragment.app.Fragment.1
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
        this.mSavedStateAttachListener = new C02252();
        initLifecycle();
    }

    public FragmentContainer createFragmentContainer() {
        return new C02265();
    }

    public final AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final FragmentActivity getActivity() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return (FragmentActivity) fragmentHostCallback.mActivity;
    }

    public final FragmentManager getChildFragmentManager() {
        if (this.mHost != null) {
            return this.mChildFragmentManager;
        }
        throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", this, " has not been attached yet."));
    }

    public final Context getContext() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return fragmentHostCallback.mContext;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final MutableCreationExtras getDefaultViewModelCreationExtras() {
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

    public final LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater = this.mLayoutInflater;
        return layoutInflater == null ? performGetLayoutInflater(null) : layoutInflater;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
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
        throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", this, " not associated with a fragment manager."));
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
        return fragmentManager.findActiveFragment(str);
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        if (this.mFragmentManager == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (getMinimumMaxLifecycleState() == Lifecycle.State.INITIALIZED.ordinal()) {
            throw new IllegalStateException("Calling getViewModelStore() before a Fragment reaches onCreate() when using setMaxLifecycle(INITIALIZED) is not supported");
        }
        HashMap hashMap = this.mFragmentManager.mNonConfig.mViewModelStores;
        ViewModelStore viewModelStore = (ViewModelStore) hashMap.get(this.mWho);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        hashMap.put(this.mWho, viewModelStore2);
        return viewModelStore2;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final void initLifecycle() {
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        SavedStateRegistryController.Companion.getClass();
        this.mSavedStateRegistryController = new SavedStateRegistryController(this, null);
        this.mDefaultFactory = null;
        if (this.mOnPreAttachedListeners.contains(this.mSavedStateAttachListener)) {
            return;
        }
        C02252 c02252 = this.mSavedStateAttachListener;
        if (this.mState >= 0) {
            c02252.onPreAttached();
        } else {
            this.mOnPreAttachedListeners.add(c02252);
        }
    }

    final void initState() {
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

    public final boolean isAdded() {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isHidden() {
        if (!this.mHidden) {
            FragmentManager fragmentManager = this.mFragmentManager;
            if (fragmentManager == null) {
                return false;
            }
            Fragment fragment = this.mParentFragment;
            fragmentManager.getClass();
            if (!(fragment == null ? false : fragment.isHidden())) {
                return false;
            }
        }
        return true;
    }

    final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onAttach(Context context) {
        this.mCalled = true;
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if ((fragmentHostCallback == null ? null : fragmentHostCallback.mActivity) != null) {
            this.mCalled = true;
        }
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public void onCreate(Bundle bundle) {
        Parcelable parcelable;
        this.mCalled = true;
        if (bundle != null && (parcelable = bundle.getParcelable("android:support:fragments")) != null) {
            this.mChildFragmentManager.restoreSaveStateInternal(parcelable);
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            fragmentManagerImpl.mStateSaved = false;
            fragmentManagerImpl.mStopped = false;
            fragmentManagerImpl.mNonConfig.mIsStateSaved = false;
            fragmentManagerImpl.dispatchStateChange(1);
        }
        FragmentManagerImpl fragmentManagerImpl2 = this.mChildFragmentManager;
        if (fragmentManagerImpl2.mCurState >= 1) {
            return;
        }
        fragmentManagerImpl2.mStateSaved = false;
        fragmentManagerImpl2.mStopped = false;
        fragmentManagerImpl2.mNonConfig.mIsStateSaved = false;
        fragmentManagerImpl2.dispatchStateChange(1);
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
        this.mViewLifecycleOwner = new FragmentViewLifecycleOwner(this, getViewModelStore());
        View onCreateView = onCreateView(layoutInflater, viewGroup, bundle);
        this.mView = onCreateView;
        if (onCreateView == null) {
            if (this.mViewLifecycleOwner.mLifecycleRegistry != null) {
                throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
            }
            this.mViewLifecycleOwner = null;
        } else {
            this.mViewLifecycleOwner.initialize();
            this.mView.setTag(R.id.view_tree_lifecycle_owner, this.mViewLifecycleOwner);
            this.mView.setTag(R.id.view_tree_view_model_store_owner, this.mViewLifecycleOwner);
            this.mView.setTag(R.id.view_tree_saved_state_registry_owner, this.mViewLifecycleOwner);
            this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner);
        }
    }

    final LayoutInflater performGetLayoutInflater(Bundle bundle) {
        LayoutInflater onGetLayoutInflater = onGetLayoutInflater(bundle);
        this.mLayoutInflater = onGetLayoutInflater;
        return onGetLayoutInflater;
    }

    public final FragmentActivity requireActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", this, " not attached to an activity."));
    }

    public final Bundle requireArguments() {
        Bundle bundle = this.mArguments;
        if (bundle != null) {
            return bundle;
        }
        throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", this, " does not have any arguments."));
    }

    public final Context requireContext() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", this, " not attached to a context."));
    }

    public final View requireView() {
        View view = this.mView;
        if (view != null) {
            return view;
        }
        throw new IllegalStateException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", this, " did not return a View from onCreateView() or this was called before onCreateView()."));
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
        if (fragmentManager != null) {
            if (fragmentManager == null ? false : fragmentManager.isStateSaved()) {
                throw new IllegalStateException("Fragment already added and state has been saved");
            }
        }
        this.mArguments = bundle;
    }

    public final void setTargetFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentStrictMode fragmentStrictMode = FragmentStrictMode.INSTANCE;
            SetTargetFragmentUsageViolation setTargetFragmentUsageViolation = new SetTargetFragmentUsageViolation(this, fragment, 0);
            FragmentStrictMode.INSTANCE.getClass();
            FragmentStrictMode.logIfDebuggingEnabled(setTargetFragmentUsageViolation);
            FragmentStrictMode.Policy nearestPolicy = FragmentStrictMode.getNearestPolicy(this);
            if (nearestPolicy.flags.contains(FragmentStrictMode.Flag.DETECT_TARGET_FRAGMENT_USAGE) && FragmentStrictMode.shouldHandlePolicyViolation(nearestPolicy, getClass(), SetTargetFragmentUsageViolation.class)) {
                FragmentStrictMode.handlePolicyViolation(nearestPolicy, setTargetFragmentUsageViolation);
            }
        }
        FragmentManager fragmentManager = this.mFragmentManager;
        FragmentManager fragmentManager2 = fragment != null ? fragment.mFragmentManager : null;
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw new IllegalArgumentException(Fragment$5$$ExternalSyntheticOutline0.m37m("Fragment ", fragment, " must share the same FragmentManager to be set as a target fragment"));
        }
        for (Fragment fragment2 = fragment; fragment2 != null; fragment2 = fragment2.getTargetFragment(false)) {
            if (fragment2.equals(this)) {
                throw new IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle");
            }
        }
        if (fragment == null) {
            this.mTargetWho = null;
            this.mTarget = null;
        } else if (this.mFragmentManager == null || fragment.mFragmentManager == null) {
            this.mTargetWho = null;
            this.mTarget = fragment;
        } else {
            this.mTargetWho = fragment.mWho;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: androidx.fragment.app.Fragment.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        };
        public final Bundle mState;

        public SavedState(Bundle bundle) {
            this.mState = bundle;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeBundle(this.mState);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            Bundle readBundle = parcel.readBundle();
            this.mState = readBundle;
            if (classLoader == null || readBundle == null) {
                return;
            }
            readBundle.setClassLoader(classLoader);
        }
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
