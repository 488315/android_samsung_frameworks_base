package android.app;

import android.animation.Animator;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.SuperNotCalledException;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

@Deprecated
/* loaded from: classes.dex */
public class Fragment implements ComponentCallbacks2, View.OnCreateContextMenuListener {
    static final int ACTIVITY_CREATED = 2;
    static final int CREATED = 1;
    static final int INITIALIZING = 0;
    static final int INVALID_STATE = -1;
    static final int RESUMED = 5;
    static final int STARTED = 4;
    static final int STOPPED = 3;
    boolean mAdded;
    AnimationInfo mAnimationInfo;
    Bundle mArguments;
    int mBackStackNesting;
    boolean mCalled;
    boolean mCheckedForLoaderManager;
    FragmentManagerImpl mChildFragmentManager;
    FragmentManagerNonConfig mChildNonConfig;
    ViewGroup mContainer;
    int mContainerId;
    boolean mDeferStart;
    boolean mDetached;
    int mFragmentId;
    FragmentManagerImpl mFragmentManager;
    boolean mFromLayout;
    boolean mHasMenu;
    boolean mHidden;
    boolean mHiddenChanged;
    FragmentHostCallback mHost;
    boolean mInLayout;
    boolean mIsCreated;
    boolean mIsNewlyAdded;
    LayoutInflater mLayoutInflater;
    LoaderManagerImpl mLoaderManager;
    boolean mLoadersStarted;
    Fragment mParentFragment;
    boolean mPerformedCreateView;
    boolean mRemoving;
    boolean mRestored;
    boolean mRetainInstance;
    boolean mRetaining;
    Bundle mSavedFragmentState;
    SparseArray<Parcelable> mSavedViewState;
    String mTag;
    Fragment mTarget;
    int mTargetRequestCode;
    View mView;
    String mWho;
    private static final ArrayMap<String, Class<?>> sClassMap = new ArrayMap<>();
    private static final Transition USE_DEFAULT_TRANSITION = new TransitionSet();
    int mState = 0;
    int mIndex = -1;
    int mTargetIndex = -1;
    boolean mMenuVisible = true;
    boolean mUserVisibleHint = true;

    interface OnStartEnterTransitionListener {
        void onStartEnterTransition();

        void startListening();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onAttachFragment(Fragment fragment) {
    }

    public boolean onContextItemSelected(MenuItem menuItem) {
        return false;
    }

    public Animator onCreateAnimator(int i, boolean z, int i2) {
        return null;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public void onDestroyOptionsMenu() {
    }

    public void onHiddenChanged(boolean z) {
    }

    @Deprecated
    public void onMultiWindowModeChanged(boolean z) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(Menu menu) {
    }

    @Deprecated
    public void onPictureInPictureModeChanged(boolean z) {
    }

    public void onPrepareOptionsMenu(Menu menu) {
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onViewCreated(View view, Bundle bundle) {
    }

    @Deprecated
    public static class SavedState implements Parcelable {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: android.app.Fragment.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        final Bundle mState;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SavedState(Bundle bundle) {
            this.mState = bundle;
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Bundle bundle = parcel.readBundle();
            this.mState = bundle;
            if (classLoader == null || bundle == null) {
                return;
            }
            bundle.setClassLoader(classLoader);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBundle(this.mState);
        }
    }

    @Deprecated
    public static class InstantiationException extends AndroidRuntimeException {
        public InstantiationException(String str, Exception exc) {
            super(str, exc);
        }
    }

    public static Fragment instantiate(Context context, String str) {
        return instantiate(context, str, null);
    }

    public static Fragment instantiate(Context context, String str, Bundle bundle) throws ClassNotFoundException {
        try {
            ArrayMap<String, Class<?>> arrayMap = sClassMap;
            Class<?> clsLoadClass = arrayMap.get(str);
            if (clsLoadClass == null) {
                clsLoadClass = context.getClassLoader().loadClass(str);
                if (!Fragment.class.isAssignableFrom(clsLoadClass)) {
                    throw new InstantiationException("Trying to instantiate a class " + str + " that is not a Fragment", new ClassCastException());
                }
                arrayMap.put(str, clsLoadClass);
            }
            Class[] clsArr = new Class[0];
            Fragment fragment = (Fragment) clsLoadClass.getConstructor(null).newInstance(null);
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.setArguments(bundle);
            }
            return fragment;
        } catch (ClassNotFoundException e) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (IllegalAccessException e2) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (java.lang.InstantiationException e3) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        } catch (NoSuchMethodException e4) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e4);
        } catch (InvocationTargetException e5) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e5);
        }
    }

    final void restoreViewState(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = this.mSavedViewState;
        if (sparseArray != null) {
            this.mView.restoreHierarchyState(sparseArray);
            this.mSavedViewState = null;
        }
        this.mCalled = false;
        onViewStateRestored(bundle);
        if (this.mCalled) {
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
    }

    final void setIndex(int i, Fragment fragment) {
        this.mIndex = i;
        if (fragment != null) {
            this.mWho = fragment.mWho + ":" + this.mIndex;
            return;
        }
        this.mWho = "android:fragment:" + this.mIndex;
    }

    final boolean isInBackStack() {
        return this.mBackStackNesting > 0;
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        DebugUtils.buildShortClassTag(this, sb);
        if (this.mIndex >= 0) {
            sb.append(" #");
            sb.append(this.mIndex);
        }
        if (this.mFragmentId != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.mFragmentId));
        }
        if (this.mTag != null) {
            sb.append(" ");
            sb.append(this.mTag);
        }
        sb.append('}');
        return sb.toString();
    }

    public final int getId() {
        return this.mFragmentId;
    }

    public final String getTag() {
        return this.mTag;
    }

    public void setArguments(Bundle bundle) {
        if (this.mIndex >= 0 && isStateSaved()) {
            throw new IllegalStateException("Fragment already active");
        }
        this.mArguments = bundle;
    }

    public final Bundle getArguments() {
        return this.mArguments;
    }

    public final boolean isStateSaved() {
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        if (fragmentManagerImpl == null) {
            return false;
        }
        return fragmentManagerImpl.isStateSaved();
    }

    public void setInitialSavedState(SavedState savedState) {
        if (this.mIndex >= 0) {
            throw new IllegalStateException("Fragment already active");
        }
        this.mSavedFragmentState = (savedState == null || savedState.mState == null) ? null : savedState.mState;
    }

    public void setTargetFragment(Fragment fragment, int i) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentManager fragmentManager2 = fragment != null ? fragment.getFragmentManager() : null;
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw new IllegalArgumentException("Fragment " + fragment + " must share the same FragmentManager to be set as a target fragment");
        }
        for (Fragment targetFragment = fragment; targetFragment != null; targetFragment = targetFragment.getTargetFragment()) {
            if (targetFragment == this) {
                throw new IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle");
            }
        }
        this.mTarget = fragment;
        this.mTargetRequestCode = i;
    }

    public final Fragment getTargetFragment() {
        return this.mTarget;
    }

    public final int getTargetRequestCode() {
        return this.mTargetRequestCode;
    }

    public Context getContext() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return fragmentHostCallback.getContext();
    }

    public final Activity getActivity() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return fragmentHostCallback.getActivity();
    }

    public final Object getHost() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            return null;
        }
        return fragmentHostCallback.onGetHost();
    }

    public final Resources getResources() {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        return fragmentHostCallback.getContext().getResources();
    }

    public final CharSequence getText(int i) {
        return getResources().getText(i);
    }

    public final String getString(int i) {
        return getResources().getString(i);
    }

    public final String getString(int i, Object... objArr) {
        return getResources().getString(i, objArr);
    }

    public final FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    public final FragmentManager getChildFragmentManager() throws Resources.NotFoundException {
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
            int i = this.mState;
            if (i >= 5) {
                this.mChildFragmentManager.dispatchResume();
            } else if (i >= 4) {
                this.mChildFragmentManager.dispatchStart();
            } else if (i >= 2) {
                this.mChildFragmentManager.dispatchActivityCreated();
            } else if (i >= 1) {
                this.mChildFragmentManager.dispatchCreate();
            }
        }
        return this.mChildFragmentManager;
    }

    public final Fragment getParentFragment() {
        return this.mParentFragment;
    }

    public final boolean isAdded() {
        return this.mHost != null && this.mAdded;
    }

    public final boolean isDetached() {
        return this.mDetached;
    }

    public final boolean isRemoving() {
        return this.mRemoving;
    }

    public final boolean isInLayout() {
        return this.mInLayout;
    }

    public final boolean isResumed() {
        return this.mState >= 5;
    }

    public final boolean isVisible() {
        View view;
        return (!isAdded() || isHidden() || (view = this.mView) == null || view.getWindowToken() == null || this.mView.getVisibility() != 0) ? false : true;
    }

    public final boolean isHidden() {
        return this.mHidden;
    }

    public void setRetainInstance(boolean z) {
        this.mRetainInstance = z;
    }

    public final boolean getRetainInstance() {
        return this.mRetainInstance;
    }

    public void setHasOptionsMenu(boolean z) {
        if (this.mHasMenu != z) {
            this.mHasMenu = z;
            if (!isAdded() || isHidden()) {
                return;
            }
            this.mFragmentManager.invalidateOptionsMenu();
        }
    }

    public void setMenuVisibility(boolean z) {
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z;
            if (this.mHasMenu && isAdded() && !isHidden()) {
                this.mFragmentManager.invalidateOptionsMenu();
            }
        }
    }

    public void setUserVisibleHint(boolean z) throws Resources.NotFoundException {
        Context context = getContext();
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        if (fragmentManagerImpl != null && fragmentManagerImpl.mHost != null) {
            context = this.mFragmentManager.mHost.getContext();
        }
        if (context == null || context.getApplicationInfo().targetSdkVersion > 23 ? !(this.mUserVisibleHint || !z || this.mState >= 4 || this.mFragmentManager == null || !isAdded()) : !(this.mUserVisibleHint || !z || this.mState >= 4 || this.mFragmentManager == null)) {
            this.mFragmentManager.performPendingDeferredStart(this);
        }
        this.mUserVisibleHint = z;
        this.mDeferStart = this.mState < 4 && !z;
    }

    public boolean getUserVisibleHint() {
        return this.mUserVisibleHint;
    }

    @Deprecated
    public LoaderManager getLoaderManager() {
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl != null) {
            return loaderManagerImpl;
        }
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        this.mCheckedForLoaderManager = true;
        LoaderManagerImpl loaderManager = fragmentHostCallback.getLoaderManager(this.mWho, this.mLoadersStarted, true);
        this.mLoaderManager = loaderManager;
        return loaderManager;
    }

    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(Intent intent, Bundle bundle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        if (bundle != null) {
            fragmentHostCallback.onStartActivityFromFragment(this, intent, -1, bundle);
        } else {
            fragmentHostCallback.onStartActivityFromFragment(this, intent, -1, null);
        }
    }

    public void startActivityForResult(Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(Intent intent, int i, Bundle bundle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        fragmentHostCallback.onStartActivityFromFragment(this, intent, i, bundle);
    }

    public void startActivityForResultAsUser(Intent intent, int i, Bundle bundle, UserHandle userHandle) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        fragmentHostCallback.onStartActivityAsUserFromFragment(this, intent, i, bundle, userHandle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        fragmentHostCallback.onStartIntentSenderFromFragment(this, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public final void requestPermissions(String[] strArr, int i) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        fragmentHostCallback.onRequestPermissionsFromFragment(this, strArr, i);
    }

    public boolean shouldShowRequestPermissionRationale(String str) {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            return fragmentHostCallback.getContext().getPackageManager().shouldShowRequestPermissionRationale(str);
        }
        return false;
    }

    public LayoutInflater onGetLayoutInflater(Bundle bundle) throws Resources.NotFoundException {
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        LayoutInflater layoutInflaterOnGetLayoutInflater = fragmentHostCallback.onGetLayoutInflater();
        if (this.mHost.onUseFragmentManagerInflaterFactory()) {
            getChildFragmentManager();
            layoutInflaterOnGetLayoutInflater.setPrivateFactory(this.mChildFragmentManager.getLayoutInflaterFactory());
        }
        return layoutInflaterOnGetLayoutInflater;
    }

    public final LayoutInflater getLayoutInflater() {
        LayoutInflater layoutInflater = this.mLayoutInflater;
        return layoutInflater == null ? performGetLayoutInflater(null) : layoutInflater;
    }

    LayoutInflater performGetLayoutInflater(Bundle bundle) throws Resources.NotFoundException {
        LayoutInflater layoutInflaterOnGetLayoutInflater = onGetLayoutInflater(bundle);
        this.mLayoutInflater = layoutInflaterOnGetLayoutInflater;
        return layoutInflaterOnGetLayoutInflater;
    }

    @Deprecated
    public void onInflate(AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        boolean z;
        boolean z2;
        onInflate(attributeSet, bundle);
        this.mCalled = true;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Fragment);
        setEnterTransition(loadTransition(context, typedArrayObtainStyledAttributes, getEnterTransition(), null, 4));
        Transition returnTransition = getReturnTransition();
        Transition transition = USE_DEFAULT_TRANSITION;
        setReturnTransition(loadTransition(context, typedArrayObtainStyledAttributes, returnTransition, transition, 6));
        setExitTransition(loadTransition(context, typedArrayObtainStyledAttributes, getExitTransition(), null, 3));
        setReenterTransition(loadTransition(context, typedArrayObtainStyledAttributes, getReenterTransition(), transition, 8));
        setSharedElementEnterTransition(loadTransition(context, typedArrayObtainStyledAttributes, getSharedElementEnterTransition(), null, 5));
        setSharedElementReturnTransition(loadTransition(context, typedArrayObtainStyledAttributes, getSharedElementReturnTransition(), transition, 7));
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            z = false;
            z2 = false;
        } else {
            z = animationInfo.mAllowEnterTransitionOverlap != null;
            z2 = this.mAnimationInfo.mAllowReturnTransitionOverlap != null;
        }
        if (!z) {
            setAllowEnterTransitionOverlap(typedArrayObtainStyledAttributes.getBoolean(9, true));
        }
        if (!z2) {
            setAllowReturnTransitionOverlap(typedArrayObtainStyledAttributes.getBoolean(10, true));
        }
        typedArrayObtainStyledAttributes.recycle();
        FragmentHostCallback fragmentHostCallback = this.mHost;
        Activity activity = fragmentHostCallback != null ? fragmentHostCallback.getActivity() : null;
        if (activity != null) {
            this.mCalled = false;
            onInflate(activity, attributeSet, bundle);
        }
    }

    @Deprecated
    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.mCalled = true;
    }

    public void onAttach(Context context) {
        this.mCalled = true;
        FragmentHostCallback fragmentHostCallback = this.mHost;
        Activity activity = fragmentHostCallback == null ? null : fragmentHostCallback.getActivity();
        if (activity != null) {
            this.mCalled = false;
            onAttach(activity);
        }
    }

    @Deprecated
    public void onAttach(Activity activity) {
        this.mCalled = true;
    }

    public void onCreate(Bundle bundle) throws Resources.NotFoundException {
        this.mCalled = true;
        Context context = getContext();
        if ((context != null ? context.getApplicationInfo().targetSdkVersion : 0) >= 24) {
            restoreChildFragmentState(bundle, true);
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            if (fragmentManagerImpl == null || fragmentManagerImpl.isStateAtLeast(1)) {
                return;
            }
            this.mChildFragmentManager.dispatchCreate();
        }
    }

    void restoreChildFragmentState(Bundle bundle, boolean z) {
        Parcelable parcelable;
        if (bundle == null || (parcelable = bundle.getParcelable("android:fragments")) == null) {
            return;
        }
        if (this.mChildFragmentManager == null) {
            instantiateChildFragmentManager();
        }
        this.mChildFragmentManager.restoreAllState(parcelable, z ? this.mChildNonConfig : null);
        this.mChildNonConfig = null;
        this.mChildFragmentManager.dispatchCreate();
    }

    public View getView() {
        return this.mView;
    }

    public void onActivityCreated(Bundle bundle) {
        this.mCalled = true;
    }

    public void onViewStateRestored(Bundle bundle) {
        this.mCalled = true;
    }

    public void onStart() {
        this.mCalled = true;
        if (this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, true, false);
        } else {
            LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
            if (loaderManagerImpl != null) {
                loaderManagerImpl.doStart();
            }
        }
    }

    public void onResume() {
        this.mCalled = true;
    }

    public void onMultiWindowModeChanged(boolean z, Configuration configuration) {
        onMultiWindowModeChanged(z);
    }

    public void onPictureInPictureModeChanged(boolean z, Configuration configuration) {
        onPictureInPictureModeChanged(z);
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        this.mCalled = true;
    }

    public void onPause() {
        this.mCalled = true;
    }

    public void onStop() {
        this.mCalled = true;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCalled = true;
    }

    public void onDestroyView() {
        this.mCalled = true;
    }

    public void onDestroy() {
        this.mCalled = true;
        if (!this.mCheckedForLoaderManager) {
            this.mCheckedForLoaderManager = true;
            this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, this.mLoadersStarted, false);
        }
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl != null) {
            loaderManagerImpl.doDestroy();
        }
    }

    void initState() {
        this.mIndex = -1;
        this.mWho = null;
        this.mAdded = false;
        this.mRemoving = false;
        this.mFromLayout = false;
        this.mInLayout = false;
        this.mRestored = false;
        this.mBackStackNesting = 0;
        this.mFragmentManager = null;
        this.mChildFragmentManager = null;
        this.mHost = null;
        this.mFragmentId = 0;
        this.mContainerId = 0;
        this.mTag = null;
        this.mHidden = false;
        this.mDetached = false;
        this.mRetaining = false;
        this.mLoaderManager = null;
        this.mLoadersStarted = false;
        this.mCheckedForLoaderManager = false;
    }

    public void onDetach() {
        this.mCalled = true;
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        getActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void registerForContextMenu(View view) throws Resources.NotFoundException {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(View view) throws Resources.NotFoundException {
        view.setOnCreateContextMenuListener(null);
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            if (this.mAnimationInfo == null) {
                return;
            } else {
                sharedElementCallback = SharedElementCallback.NULL_CALLBACK;
            }
        }
        ensureAnimationInfo().mEnterTransitionCallback = sharedElementCallback;
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            if (this.mAnimationInfo == null) {
                return;
            } else {
                sharedElementCallback = SharedElementCallback.NULL_CALLBACK;
            }
        }
        ensureAnimationInfo().mExitTransitionCallback = sharedElementCallback;
    }

    public void setEnterTransition(Transition transition) {
        if (shouldChangeTransition(transition, null)) {
            ensureAnimationInfo().mEnterTransition = transition;
        }
    }

    public Transition getEnterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mEnterTransition;
    }

    public void setReturnTransition(Transition transition) {
        if (shouldChangeTransition(transition, USE_DEFAULT_TRANSITION)) {
            ensureAnimationInfo().mReturnTransition = transition;
        }
    }

    public Transition getReturnTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mReturnTransition == USE_DEFAULT_TRANSITION ? getEnterTransition() : this.mAnimationInfo.mReturnTransition;
    }

    public void setExitTransition(Transition transition) {
        if (shouldChangeTransition(transition, null)) {
            ensureAnimationInfo().mExitTransition = transition;
        }
    }

    public Transition getExitTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mExitTransition;
    }

    public void setReenterTransition(Transition transition) {
        if (shouldChangeTransition(transition, USE_DEFAULT_TRANSITION)) {
            ensureAnimationInfo().mReenterTransition = transition;
        }
    }

    public Transition getReenterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mReenterTransition == USE_DEFAULT_TRANSITION ? getExitTransition() : this.mAnimationInfo.mReenterTransition;
    }

    public void setSharedElementEnterTransition(Transition transition) {
        if (shouldChangeTransition(transition, null)) {
            ensureAnimationInfo().mSharedElementEnterTransition = transition;
        }
    }

    public Transition getSharedElementEnterTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mSharedElementEnterTransition;
    }

    public void setSharedElementReturnTransition(Transition transition) {
        if (shouldChangeTransition(transition, USE_DEFAULT_TRANSITION)) {
            ensureAnimationInfo().mSharedElementReturnTransition = transition;
        }
    }

    public Transition getSharedElementReturnTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        if (animationInfo.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
            return getSharedElementEnterTransition();
        }
        return this.mAnimationInfo.mSharedElementReturnTransition;
    }

    public void setAllowEnterTransitionOverlap(boolean z) {
        ensureAnimationInfo().mAllowEnterTransitionOverlap = Boolean.valueOf(z);
    }

    public boolean getAllowEnterTransitionOverlap() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null || animationInfo.mAllowEnterTransitionOverlap == null) {
            return true;
        }
        return this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue();
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
        ensureAnimationInfo().mAllowReturnTransitionOverlap = Boolean.valueOf(z);
    }

    public boolean getAllowReturnTransitionOverlap() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null || animationInfo.mAllowReturnTransitionOverlap == null) {
            return true;
        }
        return this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue();
    }

    public void postponeEnterTransition() {
        ensureAnimationInfo().mEnterTransitionPostponed = true;
    }

    public void startPostponedEnterTransition() {
        FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
        if (fragmentManagerImpl == null || fragmentManagerImpl.mHost == null) {
            ensureAnimationInfo().mEnterTransitionPostponed = false;
        } else if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
            this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable() { // from class: android.app.Fragment$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.callStartTransitionListener();
                }
            });
        } else {
            callStartTransitionListener();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callStartTransitionListener() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        OnStartEnterTransitionListener onStartEnterTransitionListener = null;
        if (animationInfo != null) {
            animationInfo.mEnterTransitionPostponed = false;
            OnStartEnterTransitionListener onStartEnterTransitionListener2 = this.mAnimationInfo.mStartEnterTransitionListener;
            this.mAnimationInfo.mStartEnterTransitionListener = null;
            onStartEnterTransitionListener = onStartEnterTransitionListener2;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.onStartEnterTransition();
        }
    }

    private boolean shouldChangeTransition(Transition transition, Transition transition2) {
        return (transition == transition2 && this.mAnimationInfo == null) ? false : true;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.mFragmentId));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.mContainerId));
        printWriter.print(" mTag=");
        printWriter.println(this.mTag);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.mState);
        printWriter.print(" mIndex=");
        printWriter.print(this.mIndex);
        printWriter.print(" mWho=");
        printWriter.print(this.mWho);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.mBackStackNesting);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoving=");
        printWriter.print(this.mRemoving);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.mFromLayout);
        printWriter.print(" mInLayout=");
        printWriter.println(this.mInLayout);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.mHidden);
        printWriter.print(" mDetached=");
        printWriter.print(this.mDetached);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.mMenuVisible);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.mHasMenu);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.mRetainInstance);
        printWriter.print(" mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.mUserVisibleHint);
        if (this.mFragmentManager != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.mFragmentManager);
        }
        if (this.mHost != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.mHost);
        }
        if (this.mParentFragment != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.mParentFragment);
        }
        if (this.mArguments != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.mArguments);
        }
        if (this.mSavedFragmentState != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.mSavedFragmentState);
        }
        if (this.mSavedViewState != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.mSavedViewState);
        }
        if (this.mTarget != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(this.mTarget);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.mTargetRequestCode);
        }
        if (getNextAnim() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(getNextAnim());
        }
        if (this.mContainer != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.mContainer);
        }
        if (this.mView != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.mView);
        }
        if (getAnimatingAway() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(getAnimatingAway());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(getStateAfterAnimating());
        }
        if (this.mLoaderManager != null) {
            printWriter.print(str);
            printWriter.println("Loader Manager:");
            this.mLoaderManager.dump(str + "  ", fileDescriptor, printWriter, strArr);
        }
        if (this.mChildFragmentManager != null) {
            printWriter.print(str);
            printWriter.println("Child " + this.mChildFragmentManager + ":");
            this.mChildFragmentManager.dump(str + "  ", fileDescriptor, printWriter, strArr);
        }
    }

    Fragment findFragmentByWho(String str) {
        if (str.equals(this.mWho)) {
            return this;
        }
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            return fragmentManagerImpl.findFragmentByWho(str);
        }
        return null;
    }

    void instantiateChildFragmentManager() {
        FragmentManagerImpl fragmentManagerImpl = new FragmentManagerImpl();
        this.mChildFragmentManager = fragmentManagerImpl;
        fragmentManagerImpl.attachController(this.mHost, new FragmentContainer() { // from class: android.app.Fragment.1
            @Override // android.app.FragmentContainer
            public <T extends View> T onFindViewById(int i) {
                if (Fragment.this.mView == null) {
                    throw new IllegalStateException("Fragment does not have a view");
                }
                return (T) Fragment.this.mView.findViewById(i);
            }

            @Override // android.app.FragmentContainer
            public boolean onHasView() {
                return Fragment.this.mView != null;
            }
        }, this);
    }

    void performCreate(Bundle bundle) {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
        this.mState = 1;
        this.mCalled = false;
        onCreate(bundle);
        this.mIsCreated = true;
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
        }
        Context context = getContext();
        if ((context != null ? context.getApplicationInfo().targetSdkVersion : 0) < 24) {
            restoreChildFragmentState(bundle, false);
        }
    }

    View performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
        this.mPerformedCreateView = true;
        return onCreateView(layoutInflater, viewGroup, bundle);
    }

    void performActivityCreated(Bundle bundle) {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
        this.mState = 2;
        this.mCalled = false;
        onActivityCreated(bundle);
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
        }
        FragmentManagerImpl fragmentManagerImpl2 = this.mChildFragmentManager;
        if (fragmentManagerImpl2 != null) {
            fragmentManagerImpl2.dispatchActivityCreated();
        }
    }

    void performStart() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 4;
        this.mCalled = false;
        onStart();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
        }
        FragmentManagerImpl fragmentManagerImpl2 = this.mChildFragmentManager;
        if (fragmentManagerImpl2 != null) {
            fragmentManagerImpl2.dispatchStart();
        }
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl != null) {
            loaderManagerImpl.doReportStart();
        }
    }

    void performResume() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
            this.mChildFragmentManager.execPendingActions();
        }
        this.mState = 5;
        this.mCalled = false;
        onResume();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
        }
        FragmentManagerImpl fragmentManagerImpl2 = this.mChildFragmentManager;
        if (fragmentManagerImpl2 != null) {
            fragmentManagerImpl2.dispatchResume();
            this.mChildFragmentManager.execPendingActions();
        }
    }

    void noteStateNotSaved() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.noteStateNotSaved();
        }
    }

    @Deprecated
    void performMultiWindowModeChanged(boolean z) {
        onMultiWindowModeChanged(z);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchMultiWindowModeChanged(z);
        }
    }

    void performMultiWindowModeChanged(boolean z, Configuration configuration) {
        onMultiWindowModeChanged(z, configuration);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchMultiWindowModeChanged(z, configuration);
        }
    }

    @Deprecated
    void performPictureInPictureModeChanged(boolean z) {
        onPictureInPictureModeChanged(z);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchPictureInPictureModeChanged(z);
        }
    }

    void performPictureInPictureModeChanged(boolean z, Configuration configuration) {
        onPictureInPictureModeChanged(z, configuration);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchPictureInPictureModeChanged(z, configuration);
        }
    }

    void performConfigurationChanged(Configuration configuration) {
        onConfigurationChanged(configuration);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchConfigurationChanged(configuration);
        }
    }

    void performLowMemory() {
        onLowMemory();
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchLowMemory();
        }
    }

    void performTrimMemory(int i) {
        onTrimMemory(i);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchTrimMemory(i);
        }
    }

    boolean performCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z = false;
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                onCreateOptionsMenu(menu, menuInflater);
                z = true;
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            if (fragmentManagerImpl != null) {
                return fragmentManagerImpl.dispatchCreateOptionsMenu(menu, menuInflater) | z;
            }
        }
        return z;
    }

    boolean performPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        if (!this.mHidden) {
            if (this.mHasMenu && this.mMenuVisible) {
                onPrepareOptionsMenu(menu);
                z = true;
            }
            FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
            if (fragmentManagerImpl != null) {
                return fragmentManagerImpl.dispatchPrepareOptionsMenu(menu) | z;
            }
        }
        return z;
    }

    boolean performOptionsItemSelected(MenuItem menuItem) {
        if (this.mHidden) {
            return false;
        }
        if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(menuItem)) {
            return true;
        }
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        return fragmentManagerImpl != null && fragmentManagerImpl.dispatchOptionsItemSelected(menuItem);
    }

    boolean performContextItemSelected(MenuItem menuItem) {
        if (this.mHidden) {
            return false;
        }
        if (onContextItemSelected(menuItem)) {
            return true;
        }
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        return fragmentManagerImpl != null && fragmentManagerImpl.dispatchContextItemSelected(menuItem);
    }

    void performOptionsMenuClosed(Menu menu) {
        if (this.mHidden) {
            return;
        }
        if (this.mHasMenu && this.mMenuVisible) {
            onOptionsMenuClosed(menu);
        }
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchOptionsMenuClosed(menu);
        }
    }

    void performSaveInstanceState(Bundle bundle) {
        Parcelable parcelableSaveAllState;
        onSaveInstanceState(bundle);
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl == null || (parcelableSaveAllState = fragmentManagerImpl.saveAllState()) == null) {
            return;
        }
        bundle.putParcelable("android:fragments", parcelableSaveAllState);
    }

    void performPause() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchPause();
        }
        this.mState = 4;
        this.mCalled = false;
        onPause();
        if (this.mCalled) {
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
    }

    void performStop() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchStop();
        }
        this.mState = 3;
        this.mCalled = false;
        onStop();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
        }
        if (this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (!this.mCheckedForLoaderManager) {
                this.mCheckedForLoaderManager = true;
                this.mLoaderManager = this.mHost.getLoaderManager(this.mWho, false, false);
            }
            if (this.mLoaderManager != null) {
                if (this.mHost.getRetainLoaders()) {
                    this.mLoaderManager.doRetain();
                } else {
                    this.mLoaderManager.doStop();
                }
            }
        }
    }

    void performDestroyView() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchDestroyView();
        }
        this.mState = 1;
        this.mCalled = false;
        onDestroyView();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
        }
        LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
        if (loaderManagerImpl != null) {
            loaderManagerImpl.doReportNextStart();
        }
        this.mPerformedCreateView = false;
    }

    void performDestroy() {
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            fragmentManagerImpl.dispatchDestroy();
        }
        this.mState = 0;
        this.mCalled = false;
        this.mIsCreated = false;
        onDestroy();
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
        }
        this.mChildFragmentManager = null;
    }

    void performDetach() {
        this.mCalled = false;
        onDetach();
        this.mLayoutInflater = null;
        if (!this.mCalled) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
        }
        FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
        if (fragmentManagerImpl != null) {
            if (!this.mRetaining) {
                throw new IllegalStateException("Child FragmentManager of " + this + " was not  destroyed and this fragment is not retaining instance");
            }
            fragmentManagerImpl.dispatchDestroy();
            this.mChildFragmentManager = null;
        }
    }

    void setOnStartEnterTransitionListener(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        ensureAnimationInfo();
        if (onStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener) {
            return;
        }
        if (onStartEnterTransitionListener != null && this.mAnimationInfo.mStartEnterTransitionListener != null) {
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
        if (this.mAnimationInfo.mEnterTransitionPostponed) {
            this.mAnimationInfo.mStartEnterTransitionListener = onStartEnterTransitionListener;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.startListening();
        }
    }

    private static Transition loadTransition(Context context, TypedArray typedArray, Transition transition, Transition transition2, int i) {
        if (transition != transition2) {
            return transition;
        }
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId == 0 || resourceId == 17760256) {
            return transition2;
        }
        Transition transitionInflateTransition = TransitionInflater.from(context).inflateTransition(resourceId);
        if ((transitionInflateTransition instanceof TransitionSet) && ((TransitionSet) transitionInflateTransition).getTransitionCount() == 0) {
            return null;
        }
        return transitionInflateTransition;
    }

    private AnimationInfo ensureAnimationInfo() {
        if (this.mAnimationInfo == null) {
            this.mAnimationInfo = new AnimationInfo();
        }
        return this.mAnimationInfo;
    }

    int getNextAnim() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mNextAnim;
    }

    void setNextAnim(int i) {
        if (this.mAnimationInfo == null && i == 0) {
            return;
        }
        ensureAnimationInfo().mNextAnim = i;
    }

    int getNextTransition() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mNextTransition;
    }

    void setNextTransition(int i, int i2) {
        if (this.mAnimationInfo == null && i == 0 && i2 == 0) {
            return;
        }
        ensureAnimationInfo();
        this.mAnimationInfo.mNextTransition = i;
        this.mAnimationInfo.mNextTransitionStyle = i2;
    }

    int getNextTransitionStyle() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mNextTransitionStyle;
    }

    SharedElementCallback getEnterTransitionCallback() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return SharedElementCallback.NULL_CALLBACK;
        }
        return animationInfo.mEnterTransitionCallback;
    }

    SharedElementCallback getExitTransitionCallback() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return SharedElementCallback.NULL_CALLBACK;
        }
        return animationInfo.mExitTransitionCallback;
    }

    Animator getAnimatingAway() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return null;
        }
        return animationInfo.mAnimatingAway;
    }

    void setAnimatingAway(Animator animator) {
        ensureAnimationInfo().mAnimatingAway = animator;
    }

    int getStateAfterAnimating() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return 0;
        }
        return animationInfo.mStateAfterAnimating;
    }

    void setStateAfterAnimating(int i) {
        ensureAnimationInfo().mStateAfterAnimating = i;
    }

    boolean isPostponed() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return false;
        }
        return animationInfo.mEnterTransitionPostponed;
    }

    boolean isHideReplaced() {
        AnimationInfo animationInfo = this.mAnimationInfo;
        if (animationInfo == null) {
            return false;
        }
        return animationInfo.mIsHideReplaced;
    }

    void setHideReplaced(boolean z) {
        ensureAnimationInfo().mIsHideReplaced = z;
    }

    static class AnimationInfo {
        private Boolean mAllowEnterTransitionOverlap;
        private Boolean mAllowReturnTransitionOverlap;
        Animator mAnimatingAway;
        boolean mEnterTransitionPostponed;
        boolean mIsHideReplaced;
        int mNextAnim;
        int mNextTransition;
        int mNextTransitionStyle;
        OnStartEnterTransitionListener mStartEnterTransitionListener;
        int mStateAfterAnimating;
        private Transition mEnterTransition = null;
        private Transition mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
        private Transition mExitTransition = null;
        private Transition mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
        private Transition mSharedElementEnterTransition = null;
        private Transition mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
        SharedElementCallback mEnterTransitionCallback = SharedElementCallback.NULL_CALLBACK;
        SharedElementCallback mExitTransitionCallback = SharedElementCallback.NULL_CALLBACK;

        AnimationInfo() {
        }
    }
}
