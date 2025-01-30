package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.Cancellable;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManagerImpl;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FragmentManager {
    public ArrayList mBackStack;
    public FragmentContainer mContainer;
    public ArrayList mCreatedMenus;
    public int mCurState;
    public final C02344 mDefaultSpecialEffectsControllerFactory;
    public boolean mDestroyed;
    public final RunnableC02355 mExecCommit;
    public boolean mExecutingActions;
    public FragmentFactory mFragmentFactory;
    public boolean mHavePendingDeferredStart;
    public FragmentHostCallback mHost;
    public final C02333 mHostFragmentFactory;
    public ArrayDeque mLaunchedFragments;
    public final C02322 mMenuProvider;
    public boolean mNeedMenuInvalidate;
    public FragmentManagerViewModel mNonConfig;
    public OnBackPressedDispatcher mOnBackPressedDispatcher;
    public final FragmentManager$$ExternalSyntheticLambda0 mOnConfigurationChangedListener;
    public final FragmentManager$$ExternalSyntheticLambda0 mOnMultiWindowModeChangedListener;
    public final FragmentManager$$ExternalSyntheticLambda0 mOnPictureInPictureModeChangedListener;
    public final FragmentManager$$ExternalSyntheticLambda0 mOnTrimMemoryListener;
    public Fragment mParent;
    public Fragment mPrimaryNav;
    public ActivityResultRegistry.C00203 mRequestPermissions;
    public ActivityResultRegistry.C00203 mStartActivityForResult;
    public ActivityResultRegistry.C00203 mStartIntentSenderForResult;
    public boolean mStateSaved;
    public boolean mStopped;
    public ArrayList mTmpAddedFragments;
    public ArrayList mTmpIsPop;
    public ArrayList mTmpRecords;
    public final ArrayList mPendingActions = new ArrayList();
    public final FragmentStore mFragmentStore = new FragmentStore();
    public final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    public final C02301 mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {
            FragmentManager fragmentManager = FragmentManager.this;
            fragmentManager.execPendingActions(true);
            if (fragmentManager.mOnBackPressedCallback.isEnabled) {
                fragmentManager.popBackStackImmediate();
            } else {
                fragmentManager.mOnBackPressedDispatcher.onBackPressed();
            }
        }
    };
    public final AtomicInteger mBackStackIndex = new AtomicInteger();
    public final Map mBackStackStates = Collections.synchronizedMap(new HashMap());
    public final Map mResults = Collections.synchronizedMap(new HashMap());
    public final Map mResultListeners = Collections.synchronizedMap(new HashMap());
    public final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    public final CopyOnWriteArrayList mOnAttachListeners = new CopyOnWriteArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.FragmentManager$2 */
    public final class C02322 {
        public C02322() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.fragment.app.FragmentManager$6 */
    class C02366 implements LifecycleEventObserver {
        public final /* synthetic */ Lifecycle val$lifecycle;
        public final /* synthetic */ String val$requestKey;

        public C02366(String str, FragmentResultListener fragmentResultListener, Lifecycle lifecycle) {
            this.val$requestKey = str;
            this.val$lifecycle = lifecycle;
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.Event event2 = Lifecycle.Event.ON_START;
            String str = this.val$requestKey;
            FragmentManager fragmentManager = FragmentManager.this;
            if (event == event2 && ((Bundle) fragmentManager.mResults.get(str)) != null) {
                throw null;
            }
            if (event == Lifecycle.Event.ON_DESTROY) {
                this.val$lifecycle.removeObserver(this);
                fragmentManager.mResultListeners.remove(str);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FragmentIntentSenderContract extends ActivityResultContract {
        @Override // androidx.activity.result.contract.ActivityResultContract
        public final Object parseResult(int i, Intent intent) {
            return new ActivityResult(i, intent);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OpGenerator {
        boolean generateOps(ArrayList arrayList, ArrayList arrayList2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopBackStackState implements OpGenerator {
        public final int mFlags;
        public final int mId;
        public final String mName;

        public PopBackStackState(String str, int i, int i2) {
            this.mName = str;
            this.mId = i;
            this.mFlags = i2;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public final boolean generateOps(ArrayList arrayList, ArrayList arrayList2) {
            Fragment fragment = FragmentManager.this.mPrimaryNav;
            if (fragment == null || this.mId >= 0 || this.mName != null || !fragment.getChildFragmentManager().popBackStackImmediate()) {
                return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            }
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v14, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v15, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v16, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v20, types: [androidx.fragment.app.FragmentManager$3] */
    /* JADX WARN: Type inference failed for: r0v21, types: [androidx.fragment.app.FragmentManager$4] */
    /* JADX WARN: Type inference failed for: r0v23, types: [androidx.fragment.app.FragmentManager$5] */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.fragment.app.FragmentManager$1] */
    public FragmentManager() {
        final Object[] objArr = 0 == true ? 1 : 0;
        this.mOnConfigurationChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i = objArr;
                FragmentManager fragmentManager = this.f$0;
                switch (i) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        if (fragmentManager.isParentAdded() && num.intValue() == 80) {
                            fragmentManager.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.mIsInMultiWindowMode, false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.mIsInPictureInPictureMode, false);
                            break;
                        }
                        break;
                }
            }
        };
        final int i = 1;
        this.mOnTrimMemoryListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                FragmentManager fragmentManager = this.f$0;
                switch (i2) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        if (fragmentManager.isParentAdded() && num.intValue() == 80) {
                            fragmentManager.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.mIsInMultiWindowMode, false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.mIsInPictureInPictureMode, false);
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 2;
        this.mOnMultiWindowModeChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                FragmentManager fragmentManager = this.f$0;
                switch (i22) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        if (fragmentManager.isParentAdded() && num.intValue() == 80) {
                            fragmentManager.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.mIsInMultiWindowMode, false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.mIsInPictureInPictureMode, false);
                            break;
                        }
                        break;
                }
            }
        };
        final int i3 = 3;
        this.mOnPictureInPictureModeChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i22 = i3;
                FragmentManager fragmentManager = this.f$0;
                switch (i22) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        if (fragmentManager.isParentAdded() && num.intValue() == 80) {
                            fragmentManager.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.mIsInMultiWindowMode, false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.mIsInPictureInPictureMode, false);
                            break;
                        }
                        break;
                }
            }
        };
        this.mMenuProvider = new C02322();
        this.mCurState = -1;
        this.mFragmentFactory = null;
        this.mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
            @Override // androidx.fragment.app.FragmentFactory
            public final Fragment instantiate(ClassLoader classLoader, String str) {
                FragmentHostCallback fragmentHostCallback = FragmentManager.this.mHost;
                Context context = fragmentHostCallback.mContext;
                fragmentHostCallback.getClass();
                Object obj = Fragment.USE_DEFAULT_TRANSITION;
                try {
                    return (Fragment) FragmentFactory.loadFragmentClass(context.getClassLoader(), str).getConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (IllegalAccessException e) {
                    throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m29m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
                } catch (InstantiationException e2) {
                    throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m29m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
                } catch (NoSuchMethodException e3) {
                    throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m29m("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
                } catch (InvocationTargetException e4) {
                    throw new Fragment.InstantiationException(PathParser$$ExternalSyntheticOutline0.m29m("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
                }
            }
        };
        this.mDefaultSpecialEffectsControllerFactory = new Object(this) { // from class: androidx.fragment.app.FragmentManager.4
        };
        this.mLaunchedFragments = new ArrayDeque();
        this.mExecCommit = new Runnable() { // from class: androidx.fragment.app.FragmentManager.5
            @Override // java.lang.Runnable
            public final void run() {
                FragmentManager.this.execPendingActions(true);
            }
        };
    }

    public static boolean isLoggingEnabled(int i) {
        return Log.isLoggable("FragmentManager", i);
    }

    public static boolean isMenuAvailable(Fragment fragment) {
        boolean z;
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            return true;
        }
        Iterator it = ((ArrayList) fragment.mChildFragmentManager.mFragmentStore.getActiveFragments()).iterator();
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            Fragment fragment2 = (Fragment) it.next();
            if (fragment2 != null) {
                z2 = isMenuAvailable(fragment2);
            }
            if (z2) {
                z = true;
                break;
            }
        }
        return z;
    }

    public static boolean isParentMenuVisible(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.mMenuVisible && (fragment.mFragmentManager == null || isParentMenuVisible(fragment.mParentFragment));
    }

    public static boolean isPrimaryNavigation(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        return fragment.equals(fragmentManager.mPrimaryNav) && isPrimaryNavigation(fragmentManager.mParent);
    }

    public final FragmentStateManager addFragment(Fragment fragment) {
        String str = fragment.mPreviousWho;
        if (str != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str);
        }
        if (isLoggingEnabled(2)) {
            fragment.toString();
        }
        FragmentStateManager createOrGetFragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.makeActive(createOrGetFragmentStateManager);
        if (!fragment.mDetached) {
            fragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            if (fragment.mView == null) {
                fragment.mHiddenChanged = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return createOrGetFragmentStateManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void attachController(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, final Fragment fragment) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        CopyOnWriteArrayList copyOnWriteArrayList = this.mOnAttachListeners;
        if (fragment != null) {
            copyOnWriteArrayList.add(new FragmentOnAttachListener(this) { // from class: androidx.fragment.app.FragmentManager.7
                @Override // androidx.fragment.app.FragmentOnAttachListener
                public final void onAttachFragment$1() {
                    fragment.getClass();
                }
            });
        } else if (fragmentHostCallback instanceof FragmentOnAttachListener) {
            copyOnWriteArrayList.add((FragmentOnAttachListener) fragmentHostCallback);
        }
        if (this.mParent != null) {
            updateOnBackPressedCallbackEnabled();
        }
        if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
            OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) fragmentHostCallback;
            OnBackPressedDispatcher onBackPressedDispatcher = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
            this.mOnBackPressedDispatcher = onBackPressedDispatcher;
            LifecycleOwner lifecycleOwner = onBackPressedDispatcherOwner;
            if (fragment != null) {
                lifecycleOwner = fragment;
            }
            onBackPressedDispatcher.addCallback(lifecycleOwner, this.mOnBackPressedCallback);
        }
        if (fragment != null) {
            FragmentManagerViewModel fragmentManagerViewModel = fragment.mFragmentManager.mNonConfig;
            HashMap hashMap = fragmentManagerViewModel.mChildNonConfigs;
            FragmentManagerViewModel fragmentManagerViewModel2 = (FragmentManagerViewModel) hashMap.get(fragment.mWho);
            if (fragmentManagerViewModel2 == null) {
                fragmentManagerViewModel2 = new FragmentManagerViewModel(fragmentManagerViewModel.mStateAutomaticallySaved);
                hashMap.put(fragment.mWho, fragmentManagerViewModel2);
            }
            this.mNonConfig = fragmentManagerViewModel2;
        } else if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            this.mNonConfig = (FragmentManagerViewModel) new ViewModelProvider(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore(), FragmentManagerViewModel.FACTORY).get(FragmentManagerViewModel.class);
        } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
        }
        this.mNonConfig.mIsStateSaved = isStateSaved();
        this.mFragmentStore.mNonConfig = this.mNonConfig;
        Object obj = this.mHost;
        if ((obj instanceof SavedStateRegistryOwner) && fragment == null) {
            SavedStateRegistry savedStateRegistry = ((SavedStateRegistryOwner) obj).getSavedStateRegistry();
            final FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) this;
            savedStateRegistry.registerSavedStateProvider("android:support:fragments", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
                @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
                public final Bundle saveState() {
                    return fragmentManagerImpl.saveAllStateInternal();
                }
            });
            Bundle consumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey("android:support:fragments");
            if (consumeRestoredStateForKey != null) {
                restoreSaveStateInternal(consumeRestoredStateForKey);
            }
        }
        Object obj2 = this.mHost;
        if (obj2 instanceof ActivityResultRegistryOwner) {
            ActivityResultRegistry activityResultRegistry = ((ActivityResultRegistryOwner) obj2).getActivityResultRegistry();
            String m21m = KeyAttributes$$ExternalSyntheticOutline0.m21m("FragmentManager:", fragment != null ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), fragment.mWho, ":") : "");
            this.mStartActivityForResult = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m21m, "StartActivityForResult"), new ActivityResultContract() { // from class: androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                public final class Companion {
                    private Companion() {
                    }

                    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                        this();
                    }
                }

                static {
                    new Companion(null);
                }

                @Override // androidx.activity.result.contract.ActivityResultContract
                public final Object parseResult(int i, Intent intent) {
                    return new ActivityResult(i, intent);
                }
            }, new ActivityResultCallback() { // from class: androidx.fragment.app.FragmentManager.8
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj3) {
                    ActivityResult activityResult = (ActivityResult) obj3;
                    FragmentManager fragmentManager = FragmentManager.this;
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) fragmentManager.mLaunchedFragments.pollFirst();
                    if (launchedFragmentInfo == null) {
                        Log.w("FragmentManager", "No Activities were started for result for " + this);
                        return;
                    }
                    String str = launchedFragmentInfo.mWho;
                    Fragment findFragmentByWho = fragmentManager.mFragmentStore.findFragmentByWho(str);
                    if (findFragmentByWho == null) {
                        MotionLayout$$ExternalSyntheticOutline0.m23m("Activity result delivered for unknown Fragment ", str, "FragmentManager");
                        return;
                    }
                    int i = activityResult.mResultCode;
                    Intent intent = activityResult.mData;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFragmentByWho.toString();
                        Objects.toString(intent);
                    }
                }
            });
            this.mStartIntentSenderForResult = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m21m, "StartIntentSenderForResult"), new FragmentIntentSenderContract(), new ActivityResultCallback() { // from class: androidx.fragment.app.FragmentManager.9
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj3) {
                    ActivityResult activityResult = (ActivityResult) obj3;
                    FragmentManager fragmentManager = FragmentManager.this;
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) fragmentManager.mLaunchedFragments.pollFirst();
                    if (launchedFragmentInfo == null) {
                        Log.w("FragmentManager", "No IntentSenders were started for " + this);
                        return;
                    }
                    String str = launchedFragmentInfo.mWho;
                    Fragment findFragmentByWho = fragmentManager.mFragmentStore.findFragmentByWho(str);
                    if (findFragmentByWho == null) {
                        MotionLayout$$ExternalSyntheticOutline0.m23m("Intent Sender result delivered for unknown Fragment ", str, "FragmentManager");
                        return;
                    }
                    int i = activityResult.mResultCode;
                    Intent intent = activityResult.mData;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFragmentByWho.toString();
                        Objects.toString(intent);
                    }
                }
            });
            this.mRequestPermissions = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m21m, "RequestPermissions"), new ActivityResultContract() { // from class: androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                public final class Companion {
                    private Companion() {
                    }

                    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                        this();
                    }
                }

                static {
                    new Companion(null);
                }

                @Override // androidx.activity.result.contract.ActivityResultContract
                public final Object parseResult(int i, Intent intent) {
                    if (i != -1) {
                        return MapsKt__MapsKt.emptyMap();
                    }
                    if (intent == null) {
                        return MapsKt__MapsKt.emptyMap();
                    }
                    String[] stringArrayExtra = intent.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
                    int[] intArrayExtra = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
                    if (intArrayExtra == null || stringArrayExtra == null) {
                        return MapsKt__MapsKt.emptyMap();
                    }
                    ArrayList arrayList = new ArrayList(intArrayExtra.length);
                    for (int i2 : intArrayExtra) {
                        arrayList.add(Boolean.valueOf(i2 == 0));
                    }
                    return MapsKt__MapsKt.toMap(CollectionsKt___CollectionsKt.zip(ArraysKt___ArraysKt.filterNotNull(stringArrayExtra), arrayList));
                }
            }, new ActivityResultCallback() { // from class: androidx.fragment.app.FragmentManager.10
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(Object obj3) {
                    Map map = (Map) obj3;
                    ArrayList arrayList = new ArrayList(map.values());
                    int[] iArr = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        iArr[i] = ((Boolean) arrayList.get(i)).booleanValue() ? 0 : -1;
                    }
                    FragmentManager fragmentManager = FragmentManager.this;
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) fragmentManager.mLaunchedFragments.pollFirst();
                    if (launchedFragmentInfo == null) {
                        Log.w("FragmentManager", "No permissions were requested for " + this);
                    } else {
                        String str = launchedFragmentInfo.mWho;
                        if (fragmentManager.mFragmentStore.findFragmentByWho(str) == null) {
                            MotionLayout$$ExternalSyntheticOutline0.m23m("Permission request result delivered for unknown Fragment ", str, "FragmentManager");
                        }
                    }
                }
            });
        }
        Object obj3 = this.mHost;
        if (obj3 instanceof OnConfigurationChangedProvider) {
            ((OnConfigurationChangedProvider) obj3).addOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        }
        Object obj4 = this.mHost;
        if (obj4 instanceof OnTrimMemoryProvider) {
            ((OnTrimMemoryProvider) obj4).addOnTrimMemoryListener(this.mOnTrimMemoryListener);
        }
        Object obj5 = this.mHost;
        if (obj5 instanceof OnMultiWindowModeChangedProvider) {
            ((OnMultiWindowModeChangedProvider) obj5).addOnMultiWindowModeChangedListener(this.mOnMultiWindowModeChangedListener);
        }
        Object obj6 = this.mHost;
        if (obj6 instanceof OnPictureInPictureModeChangedProvider) {
            ((OnPictureInPictureModeChangedProvider) obj6).addOnPictureInPictureModeChangedListener(this.mOnPictureInPictureModeChangedListener);
        }
        Object obj7 = this.mHost;
        if ((obj7 instanceof MenuHost) && fragment == null) {
            ((MenuHost) obj7).addMenuProvider(this.mMenuProvider);
        }
    }

    public final void attachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Objects.toString(fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (fragment.mAdded) {
                return;
            }
            this.mFragmentStore.addFragment(fragment);
            if (isLoggingEnabled(2)) {
                fragment.toString();
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    public final void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    public final Set collectAllSpecialEffectsController() {
        HashSet hashSet = new HashSet();
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragmentStateManagers()).iterator();
        while (it.hasNext()) {
            ViewGroup viewGroup = ((FragmentStateManager) it.next()).mFragment.mContainer;
            if (viewGroup != null) {
                hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, getSpecialEffectsControllerFactory()));
            }
        }
        return hashSet;
    }

    public final FragmentStateManager createOrGetFragmentStateManager(Fragment fragment) {
        String str = fragment.mWho;
        FragmentStore fragmentStore = this.mFragmentStore;
        FragmentStateManager fragmentStateManager = (FragmentStateManager) fragmentStore.mActive.get(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager;
        }
        FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, fragmentStore, fragment);
        fragmentStateManager2.restoreState(this.mHost.mContext.getClassLoader());
        fragmentStateManager2.mFragmentManagerState = this.mCurState;
        return fragmentStateManager2;
    }

    public final void detachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Objects.toString(fragment);
        }
        if (fragment.mDetached) {
            return;
        }
        fragment.mDetached = true;
        if (fragment.mAdded) {
            if (isLoggingEnabled(2)) {
                fragment.toString();
            }
            FragmentStore fragmentStore = this.mFragmentStore;
            synchronized (fragmentStore.mAdded) {
                fragmentStore.mAdded.remove(fragment);
            }
            fragment.mAdded = false;
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            setVisibleRemovingFragment(fragment);
        }
    }

    public final void dispatchConfigurationChanged(boolean z, Configuration configuration) {
        if (z && (this.mHost instanceof OnConfigurationChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchConfigurationChanged() on host. Host implements OnConfigurationChangedProvider and automatically dispatches configuration changes to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.onConfigurationChanged(configuration);
                if (z) {
                    fragment.mChildFragmentManager.dispatchConfigurationChanged(true, configuration);
                }
            }
        }
    }

    public final boolean dispatchContextItemSelected() {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                if (!fragment.mHidden ? fragment.mChildFragmentManager.dispatchContextItemSelected() : false) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        boolean z;
        boolean z2;
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList arrayList = null;
        boolean z3 = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment)) {
                if (fragment.mHidden) {
                    z = false;
                } else {
                    if (fragment.mHasMenu && fragment.mMenuVisible) {
                        fragment.onCreateOptionsMenu(menu, menuInflater);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    z = z2 | fragment.mChildFragmentManager.dispatchCreateOptionsMenu(menu, menuInflater);
                }
                if (z) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(fragment);
                    z3 = true;
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment fragment2 = (Fragment) this.mCreatedMenus.get(i);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.getClass();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z3;
    }

    public final void dispatchDestroy() {
        boolean z = true;
        this.mDestroyed = true;
        execPendingActions(true);
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
        }
        FragmentHostCallback fragmentHostCallback = this.mHost;
        boolean z2 = fragmentHostCallback instanceof ViewModelStoreOwner;
        FragmentStore fragmentStore = this.mFragmentStore;
        if (z2) {
            z = fragmentStore.mNonConfig.mHasBeenCleared;
        } else {
            Context context = fragmentHostCallback.mContext;
            if (context instanceof Activity) {
                z = true ^ ((Activity) context).isChangingConfigurations();
            }
        }
        if (z) {
            Iterator it2 = this.mBackStackStates.values().iterator();
            while (it2.hasNext()) {
                for (String str : ((BackStackState) it2.next()).mFragments) {
                    FragmentManagerViewModel fragmentManagerViewModel = fragmentStore.mNonConfig;
                    if (isLoggingEnabled(3)) {
                        fragmentManagerViewModel.getClass();
                        Log.d("FragmentManager", "Clearing non-config state for saved state of Fragment " + str);
                    }
                    fragmentManagerViewModel.clearNonConfigStateInternal(str);
                }
            }
        }
        dispatchStateChange(-1);
        Object obj = this.mHost;
        if (obj instanceof OnTrimMemoryProvider) {
            ((OnTrimMemoryProvider) obj).removeOnTrimMemoryListener(this.mOnTrimMemoryListener);
        }
        Object obj2 = this.mHost;
        if (obj2 instanceof OnConfigurationChangedProvider) {
            ((OnConfigurationChangedProvider) obj2).removeOnConfigurationChangedListener(this.mOnConfigurationChangedListener);
        }
        Object obj3 = this.mHost;
        if (obj3 instanceof OnMultiWindowModeChangedProvider) {
            ((OnMultiWindowModeChangedProvider) obj3).removeOnMultiWindowModeChangedListener(this.mOnMultiWindowModeChangedListener);
        }
        Object obj4 = this.mHost;
        if (obj4 instanceof OnPictureInPictureModeChangedProvider) {
            ((OnPictureInPictureModeChangedProvider) obj4).removeOnPictureInPictureModeChangedListener(this.mOnPictureInPictureModeChangedListener);
        }
        Object obj5 = this.mHost;
        if (obj5 instanceof MenuHost) {
            ((MenuHost) obj5).removeMenuProvider(this.mMenuProvider);
        }
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            Iterator it3 = this.mOnBackPressedCallback.cancellables.iterator();
            while (it3.hasNext()) {
                ((Cancellable) it3.next()).cancel();
            }
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultRegistry.C00203 c00203 = this.mStartActivityForResult;
        if (c00203 != null) {
            c00203.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    public final void dispatchLowMemory(boolean z) {
        if (z && (this.mHost instanceof OnTrimMemoryProvider)) {
            throwException(new IllegalStateException("Do not call dispatchLowMemory() on host. Host implements OnTrimMemoryProvider and automatically dispatches low memory callbacks to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.onLowMemory();
                if (z) {
                    fragment.mChildFragmentManager.dispatchLowMemory(true);
                }
            }
        }
    }

    public final void dispatchMultiWindowModeChanged(boolean z, boolean z2) {
        if (z2 && (this.mHost instanceof OnMultiWindowModeChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchMultiWindowModeChanged() on host. Host implements OnMultiWindowModeChangedProvider and automatically dispatches multi-window mode changes to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && z2) {
                fragment.mChildFragmentManager.dispatchMultiWindowModeChanged(z, true);
            }
        }
    }

    public final void dispatchOnHiddenChanged() {
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragments()).iterator();
        while (it.hasNext()) {
            Fragment fragment = (Fragment) it.next();
            if (fragment != null) {
                fragment.isHidden();
                fragment.mChildFragmentManager.dispatchOnHiddenChanged();
            }
        }
    }

    public final boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                if (!fragment.mHidden ? (fragment.mHasMenu && fragment.mMenuVisible && fragment.onOptionsItemSelected(menuItem)) ? true : fragment.mChildFragmentManager.dispatchOptionsItemSelected(menuItem) : false) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void dispatchOptionsMenuClosed() {
        if (this.mCurState < 1) {
            return;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && !fragment.mHidden) {
                fragment.mChildFragmentManager.dispatchOptionsMenuClosed();
            }
        }
    }

    public final void dispatchParentPrimaryNavigationFragmentChanged(Fragment fragment) {
        if (fragment == null || !fragment.equals(findActiveFragment(fragment.mWho))) {
            return;
        }
        fragment.mFragmentManager.getClass();
        boolean isPrimaryNavigation = isPrimaryNavigation(fragment);
        Boolean bool = fragment.mIsPrimaryNavigationFragment;
        if (bool == null || bool.booleanValue() != isPrimaryNavigation) {
            fragment.mIsPrimaryNavigationFragment = Boolean.valueOf(isPrimaryNavigation);
            FragmentManagerImpl fragmentManagerImpl = fragment.mChildFragmentManager;
            fragmentManagerImpl.updateOnBackPressedCallbackEnabled();
            fragmentManagerImpl.dispatchParentPrimaryNavigationFragmentChanged(fragmentManagerImpl.mPrimaryNav);
        }
    }

    public final void dispatchPictureInPictureModeChanged(boolean z, boolean z2) {
        if (z2 && (this.mHost instanceof OnPictureInPictureModeChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchPictureInPictureModeChanged() on host. Host implements OnPictureInPictureModeChangedProvider and automatically dispatches picture-in-picture mode changes to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && z2) {
                fragment.mChildFragmentManager.dispatchPictureInPictureModeChanged(z, true);
            }
        }
    }

    public final boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean z;
        boolean z2;
        if (this.mCurState < 1) {
            return false;
        }
        boolean z3 = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment)) {
                if (fragment.mHidden) {
                    z = false;
                } else {
                    if (fragment.mHasMenu && fragment.mMenuVisible) {
                        fragment.onPrepareOptionsMenu(menu);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    z = fragment.mChildFragmentManager.dispatchPrepareOptionsMenu(menu) | z2;
                }
                if (z) {
                    z3 = true;
                }
            }
        }
        return z3;
    }

    public final void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            for (FragmentStateManager fragmentStateManager : this.mFragmentStore.mActive.values()) {
                if (fragmentStateManager != null) {
                    fragmentStateManager.mFragmentManagerState = i;
                }
            }
            moveToState(i, false);
            Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
            while (it.hasNext()) {
                ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
            }
            this.mExecutingActions = false;
            execPendingActions(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    public final void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "    ");
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.getClass();
        String str2 = str + "    ";
        HashMap hashMap = fragmentStore.mActive;
        if (!hashMap.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : hashMap.values()) {
                printWriter.print(str);
                if (fragmentStateManager != null) {
                    Fragment fragment = fragmentStateManager.mFragment;
                    printWriter.println(fragment);
                    fragment.getClass();
                    printWriter.print(str2);
                    printWriter.print("mFragmentId=#");
                    printWriter.print(Integer.toHexString(fragment.mFragmentId));
                    printWriter.print(" mContainerId=#");
                    printWriter.print(Integer.toHexString(fragment.mContainerId));
                    printWriter.print(" mTag=");
                    printWriter.println(fragment.mTag);
                    printWriter.print(str2);
                    printWriter.print("mState=");
                    printWriter.print(fragment.mState);
                    printWriter.print(" mWho=");
                    printWriter.print(fragment.mWho);
                    printWriter.print(" mBackStackNesting=");
                    printWriter.println(fragment.mBackStackNesting);
                    printWriter.print(str2);
                    printWriter.print("mAdded=");
                    printWriter.print(fragment.mAdded);
                    printWriter.print(" mRemoving=");
                    printWriter.print(fragment.mRemoving);
                    printWriter.print(" mFromLayout=");
                    printWriter.print(fragment.mFromLayout);
                    printWriter.print(" mInLayout=");
                    printWriter.println(fragment.mInLayout);
                    printWriter.print(str2);
                    printWriter.print("mHidden=");
                    printWriter.print(fragment.mHidden);
                    printWriter.print(" mDetached=");
                    printWriter.print(fragment.mDetached);
                    printWriter.print(" mMenuVisible=");
                    printWriter.print(fragment.mMenuVisible);
                    printWriter.print(" mHasMenu=");
                    printWriter.println(fragment.mHasMenu);
                    printWriter.print(str2);
                    printWriter.print("mRetainInstance=");
                    printWriter.print(fragment.mRetainInstance);
                    printWriter.print(" mUserVisibleHint=");
                    printWriter.println(fragment.mUserVisibleHint);
                    if (fragment.mFragmentManager != null) {
                        printWriter.print(str2);
                        printWriter.print("mFragmentManager=");
                        printWriter.println(fragment.mFragmentManager);
                    }
                    if (fragment.mHost != null) {
                        printWriter.print(str2);
                        printWriter.print("mHost=");
                        printWriter.println(fragment.mHost);
                    }
                    if (fragment.mParentFragment != null) {
                        printWriter.print(str2);
                        printWriter.print("mParentFragment=");
                        printWriter.println(fragment.mParentFragment);
                    }
                    if (fragment.mArguments != null) {
                        printWriter.print(str2);
                        printWriter.print("mArguments=");
                        printWriter.println(fragment.mArguments);
                    }
                    if (fragment.mSavedFragmentState != null) {
                        printWriter.print(str2);
                        printWriter.print("mSavedFragmentState=");
                        printWriter.println(fragment.mSavedFragmentState);
                    }
                    if (fragment.mSavedViewState != null) {
                        printWriter.print(str2);
                        printWriter.print("mSavedViewState=");
                        printWriter.println(fragment.mSavedViewState);
                    }
                    if (fragment.mSavedViewRegistryState != null) {
                        printWriter.print(str2);
                        printWriter.print("mSavedViewRegistryState=");
                        printWriter.println(fragment.mSavedViewRegistryState);
                    }
                    Object targetFragment = fragment.getTargetFragment(false);
                    if (targetFragment != null) {
                        printWriter.print(str2);
                        printWriter.print("mTarget=");
                        printWriter.print(targetFragment);
                        printWriter.print(" mTargetRequestCode=");
                        printWriter.println(fragment.mTargetRequestCode);
                    }
                    printWriter.print(str2);
                    printWriter.print("mPopDirection=");
                    Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
                    printWriter.println(animationInfo == null ? false : animationInfo.mIsPop);
                    Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
                    if ((animationInfo2 == null ? 0 : animationInfo2.mEnterAnim) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getEnterAnim=");
                        Fragment.AnimationInfo animationInfo3 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo3 == null ? 0 : animationInfo3.mEnterAnim);
                    }
                    Fragment.AnimationInfo animationInfo4 = fragment.mAnimationInfo;
                    if ((animationInfo4 == null ? 0 : animationInfo4.mExitAnim) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getExitAnim=");
                        Fragment.AnimationInfo animationInfo5 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo5 == null ? 0 : animationInfo5.mExitAnim);
                    }
                    Fragment.AnimationInfo animationInfo6 = fragment.mAnimationInfo;
                    if ((animationInfo6 == null ? 0 : animationInfo6.mPopEnterAnim) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getPopEnterAnim=");
                        Fragment.AnimationInfo animationInfo7 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo7 == null ? 0 : animationInfo7.mPopEnterAnim);
                    }
                    Fragment.AnimationInfo animationInfo8 = fragment.mAnimationInfo;
                    if ((animationInfo8 == null ? 0 : animationInfo8.mPopExitAnim) != 0) {
                        printWriter.print(str2);
                        printWriter.print("getPopExitAnim=");
                        Fragment.AnimationInfo animationInfo9 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo9 == null ? 0 : animationInfo9.mPopExitAnim);
                    }
                    if (fragment.mContainer != null) {
                        printWriter.print(str2);
                        printWriter.print("mContainer=");
                        printWriter.println(fragment.mContainer);
                    }
                    if (fragment.mView != null) {
                        printWriter.print(str2);
                        printWriter.print("mView=");
                        printWriter.println(fragment.mView);
                    }
                    if (fragment.getContext() != null) {
                        new LoaderManagerImpl(fragment, fragment.getViewModelStore()).dump(str2, fileDescriptor, printWriter, strArr);
                    }
                    printWriter.print(str2);
                    printWriter.println("Child " + fragment.mChildFragmentManager + ":");
                    fragment.mChildFragmentManager.dump(AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, "  "), fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        ArrayList arrayList = fragmentStore.mAdded;
        int size3 = arrayList.size();
        if (size3 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size3; i++) {
                Fragment fragment2 = (Fragment) arrayList.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
        ArrayList arrayList2 = this.mCreatedMenus;
        if (arrayList2 != null && (size2 = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i2 = 0; i2 < size2; i2++) {
                Fragment fragment3 = (Fragment) this.mCreatedMenus.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment3.toString());
            }
        }
        ArrayList arrayList3 = this.mBackStack;
        if (arrayList3 != null && (size = arrayList3.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i3 = 0; i3 < size; i3++) {
                BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(m14m, printWriter, true);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int size4 = this.mPendingActions.size();
            if (size4 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i4 = 0; i4 < size4; i4++) {
                    Object obj = (OpGenerator) this.mPendingActions.get(i4);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i4);
                    printWriter.print(": ");
                    printWriter.println(obj);
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.mCurState);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
    }

    public final void enqueueAction(OpGenerator opGenerator, boolean z) {
        if (!z) {
            if (this.mHost == null) {
                if (!this.mDestroyed) {
                    throw new IllegalStateException("FragmentManager has not been attached to a host.");
                }
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            if (isStateSaved()) {
                throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
            }
        }
        synchronized (this.mPendingActions) {
            if (this.mHost == null) {
                if (!z) {
                    throw new IllegalStateException("Activity has been destroyed");
                }
            } else {
                this.mPendingActions.add(opGenerator);
                scheduleCommit();
            }
        }
    }

    public final void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            if (!this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.mHandler.getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z && isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList();
            this.mTmpIsPop = new ArrayList();
        }
    }

    public final boolean execPendingActions(boolean z) {
        boolean z2;
        ensureExecReady(z);
        boolean z3 = false;
        while (true) {
            ArrayList arrayList = this.mTmpRecords;
            ArrayList arrayList2 = this.mTmpIsPop;
            synchronized (this.mPendingActions) {
                if (this.mPendingActions.isEmpty()) {
                    z2 = false;
                } else {
                    try {
                        int size = this.mPendingActions.size();
                        z2 = false;
                        for (int i = 0; i < size; i++) {
                            z2 |= ((OpGenerator) this.mPendingActions.get(i)).generateOps(arrayList, arrayList2);
                        }
                    } finally {
                    }
                }
            }
            if (!z2) {
                updateOnBackPressedCallbackEnabled();
                doPendingDeferredStart();
                this.mFragmentStore.mActive.values().removeAll(Collections.singleton(null));
                return z3;
            }
            z3 = true;
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
    }

    public final void execSingleAction(OpGenerator opGenerator, boolean z) {
        if (z && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(z);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.mActive.values().removeAll(Collections.singleton(null));
    }

    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v38, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v39 */
    public final void executeOpsTogether(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        ViewGroup viewGroup;
        FragmentStore fragmentStore;
        FragmentStore fragmentStore2;
        FragmentStore fragmentStore3;
        int i3;
        int i4;
        ?? r3;
        ArrayList arrayList3 = arrayList;
        ArrayList arrayList4 = arrayList2;
        boolean z = ((BackStackRecord) arrayList3.get(i)).mReorderingAllowed;
        ArrayList arrayList5 = this.mTmpAddedFragments;
        if (arrayList5 == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            arrayList5.clear();
        }
        ArrayList arrayList6 = this.mTmpAddedFragments;
        FragmentStore fragmentStore4 = this.mFragmentStore;
        arrayList6.addAll(fragmentStore4.getFragments());
        Fragment fragment = this.mPrimaryNav;
        int i5 = i;
        boolean z2 = false;
        while (true) {
            int i6 = 1;
            if (i5 >= i2) {
                FragmentStore fragmentStore5 = fragmentStore4;
                this.mTmpAddedFragments.clear();
                if (!z && this.mCurState >= 1) {
                    for (int i7 = i; i7 < i2; i7++) {
                        Iterator it = ((BackStackRecord) arrayList.get(i7)).mOps.iterator();
                        while (it.hasNext()) {
                            Fragment fragment2 = ((FragmentTransaction.C0247Op) it.next()).mFragment;
                            if (fragment2 == null || fragment2.mFragmentManager == null) {
                                fragmentStore = fragmentStore5;
                            } else {
                                fragmentStore = fragmentStore5;
                                fragmentStore.makeActive(createOrGetFragmentStateManager(fragment2));
                            }
                            fragmentStore5 = fragmentStore;
                        }
                    }
                }
                for (int i8 = i; i8 < i2; i8++) {
                    BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i8);
                    if (((Boolean) arrayList2.get(i8)).booleanValue()) {
                        backStackRecord.bumpBackStackNesting(-1);
                        ArrayList arrayList7 = backStackRecord.mOps;
                        for (int size = arrayList7.size() - 1; size >= 0; size--) {
                            FragmentTransaction.C0247Op c0247Op = (FragmentTransaction.C0247Op) arrayList7.get(size);
                            Fragment fragment3 = c0247Op.mFragment;
                            if (fragment3 != null) {
                                fragment3.mBeingSaved = backStackRecord.mBeingSaved;
                                if (fragment3.mAnimationInfo != null) {
                                    fragment3.ensureAnimationInfo().mIsPop = true;
                                }
                                int i9 = backStackRecord.mTransition;
                                int i10 = 8194;
                                int i11 = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND;
                                if (i9 != 4097) {
                                    if (i9 != 8194) {
                                        i10 = 8197;
                                        i11 = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_WRONG_STATE;
                                        if (i9 != 8197) {
                                            if (i9 == 4099) {
                                                i11 = 4099;
                                            } else if (i9 != 4100) {
                                                i10 = 0;
                                            }
                                        }
                                    }
                                    i10 = i11;
                                }
                                if (fragment3.mAnimationInfo != null || i10 != 0) {
                                    fragment3.ensureAnimationInfo();
                                    fragment3.mAnimationInfo.mNextTransition = i10;
                                }
                                ArrayList arrayList8 = backStackRecord.mSharedElementTargetNames;
                                ArrayList arrayList9 = backStackRecord.mSharedElementSourceNames;
                                fragment3.ensureAnimationInfo();
                                Fragment.AnimationInfo animationInfo = fragment3.mAnimationInfo;
                                animationInfo.mSharedElementSourceNames = arrayList8;
                                animationInfo.mSharedElementTargetNames = arrayList9;
                            }
                            int i12 = c0247Op.mCmd;
                            FragmentManager fragmentManager = backStackRecord.mManager;
                            switch (i12) {
                                case 1:
                                    fragment3.setAnimations(c0247Op.mEnterAnim, c0247Op.mExitAnim, c0247Op.mPopEnterAnim, c0247Op.mPopExitAnim);
                                    fragmentManager.setExitAnimationOrder(fragment3, true);
                                    fragmentManager.removeFragment(fragment3);
                                    break;
                                case 2:
                                default:
                                    throw new IllegalArgumentException("Unknown cmd: " + c0247Op.mCmd);
                                case 3:
                                    fragment3.setAnimations(c0247Op.mEnterAnim, c0247Op.mExitAnim, c0247Op.mPopEnterAnim, c0247Op.mPopExitAnim);
                                    fragmentManager.addFragment(fragment3);
                                    break;
                                case 4:
                                    fragment3.setAnimations(c0247Op.mEnterAnim, c0247Op.mExitAnim, c0247Op.mPopEnterAnim, c0247Op.mPopExitAnim);
                                    fragmentManager.getClass();
                                    if (isLoggingEnabled(2)) {
                                        Objects.toString(fragment3);
                                    }
                                    if (fragment3.mHidden) {
                                        fragment3.mHidden = false;
                                        fragment3.mHiddenChanged = !fragment3.mHiddenChanged;
                                        break;
                                    } else {
                                        break;
                                    }
                                case 5:
                                    fragment3.setAnimations(c0247Op.mEnterAnim, c0247Op.mExitAnim, c0247Op.mPopEnterAnim, c0247Op.mPopExitAnim);
                                    fragmentManager.setExitAnimationOrder(fragment3, true);
                                    if (isLoggingEnabled(2)) {
                                        Objects.toString(fragment3);
                                    }
                                    if (fragment3.mHidden) {
                                        break;
                                    } else {
                                        fragment3.mHidden = true;
                                        fragment3.mHiddenChanged = !fragment3.mHiddenChanged;
                                        fragmentManager.setVisibleRemovingFragment(fragment3);
                                        break;
                                    }
                                case 6:
                                    fragment3.setAnimations(c0247Op.mEnterAnim, c0247Op.mExitAnim, c0247Op.mPopEnterAnim, c0247Op.mPopExitAnim);
                                    fragmentManager.attachFragment(fragment3);
                                    break;
                                case 7:
                                    fragment3.setAnimations(c0247Op.mEnterAnim, c0247Op.mExitAnim, c0247Op.mPopEnterAnim, c0247Op.mPopExitAnim);
                                    fragmentManager.setExitAnimationOrder(fragment3, true);
                                    fragmentManager.detachFragment(fragment3);
                                    break;
                                case 8:
                                    fragmentManager.setPrimaryNavigationFragment(null);
                                    break;
                                case 9:
                                    fragmentManager.setPrimaryNavigationFragment(fragment3);
                                    break;
                                case 10:
                                    fragmentManager.setMaxLifecycle(fragment3, c0247Op.mOldMaxState);
                                    break;
                            }
                        }
                    } else {
                        backStackRecord.bumpBackStackNesting(1);
                        ArrayList arrayList10 = backStackRecord.mOps;
                        int size2 = arrayList10.size();
                        for (int i13 = 0; i13 < size2; i13++) {
                            FragmentTransaction.C0247Op c0247Op2 = (FragmentTransaction.C0247Op) arrayList10.get(i13);
                            Fragment fragment4 = c0247Op2.mFragment;
                            if (fragment4 != null) {
                                fragment4.mBeingSaved = backStackRecord.mBeingSaved;
                                if (fragment4.mAnimationInfo != null) {
                                    fragment4.ensureAnimationInfo().mIsPop = false;
                                }
                                int i14 = backStackRecord.mTransition;
                                if (fragment4.mAnimationInfo != null || i14 != 0) {
                                    fragment4.ensureAnimationInfo();
                                    fragment4.mAnimationInfo.mNextTransition = i14;
                                }
                                ArrayList arrayList11 = backStackRecord.mSharedElementSourceNames;
                                ArrayList arrayList12 = backStackRecord.mSharedElementTargetNames;
                                fragment4.ensureAnimationInfo();
                                Fragment.AnimationInfo animationInfo2 = fragment4.mAnimationInfo;
                                animationInfo2.mSharedElementSourceNames = arrayList11;
                                animationInfo2.mSharedElementTargetNames = arrayList12;
                            }
                            int i15 = c0247Op2.mCmd;
                            FragmentManager fragmentManager2 = backStackRecord.mManager;
                            switch (i15) {
                                case 1:
                                    fragment4.setAnimations(c0247Op2.mEnterAnim, c0247Op2.mExitAnim, c0247Op2.mPopEnterAnim, c0247Op2.mPopExitAnim);
                                    fragmentManager2.setExitAnimationOrder(fragment4, false);
                                    fragmentManager2.addFragment(fragment4);
                                case 2:
                                default:
                                    throw new IllegalArgumentException("Unknown cmd: " + c0247Op2.mCmd);
                                case 3:
                                    fragment4.setAnimations(c0247Op2.mEnterAnim, c0247Op2.mExitAnim, c0247Op2.mPopEnterAnim, c0247Op2.mPopExitAnim);
                                    fragmentManager2.removeFragment(fragment4);
                                case 4:
                                    fragment4.setAnimations(c0247Op2.mEnterAnim, c0247Op2.mExitAnim, c0247Op2.mPopEnterAnim, c0247Op2.mPopExitAnim);
                                    fragmentManager2.getClass();
                                    if (isLoggingEnabled(2)) {
                                        Objects.toString(fragment4);
                                    }
                                    if (!fragment4.mHidden) {
                                        fragment4.mHidden = true;
                                        fragment4.mHiddenChanged = !fragment4.mHiddenChanged;
                                        fragmentManager2.setVisibleRemovingFragment(fragment4);
                                    }
                                case 5:
                                    fragment4.setAnimations(c0247Op2.mEnterAnim, c0247Op2.mExitAnim, c0247Op2.mPopEnterAnim, c0247Op2.mPopExitAnim);
                                    fragmentManager2.setExitAnimationOrder(fragment4, false);
                                    if (isLoggingEnabled(2)) {
                                        Objects.toString(fragment4);
                                    }
                                    if (fragment4.mHidden) {
                                        fragment4.mHidden = false;
                                        fragment4.mHiddenChanged = !fragment4.mHiddenChanged;
                                    }
                                case 6:
                                    fragment4.setAnimations(c0247Op2.mEnterAnim, c0247Op2.mExitAnim, c0247Op2.mPopEnterAnim, c0247Op2.mPopExitAnim);
                                    fragmentManager2.detachFragment(fragment4);
                                case 7:
                                    fragment4.setAnimations(c0247Op2.mEnterAnim, c0247Op2.mExitAnim, c0247Op2.mPopEnterAnim, c0247Op2.mPopExitAnim);
                                    fragmentManager2.setExitAnimationOrder(fragment4, false);
                                    fragmentManager2.attachFragment(fragment4);
                                case 8:
                                    fragmentManager2.setPrimaryNavigationFragment(fragment4);
                                case 9:
                                    fragmentManager2.setPrimaryNavigationFragment(null);
                                case 10:
                                    fragmentManager2.setMaxLifecycle(fragment4, c0247Op2.mCurrentMaxState);
                            }
                        }
                    }
                }
                boolean booleanValue = ((Boolean) arrayList2.get(i2 - 1)).booleanValue();
                for (int i16 = i; i16 < i2; i16++) {
                    BackStackRecord backStackRecord2 = (BackStackRecord) arrayList.get(i16);
                    if (booleanValue) {
                        for (int size3 = backStackRecord2.mOps.size() - 1; size3 >= 0; size3--) {
                            Fragment fragment5 = ((FragmentTransaction.C0247Op) backStackRecord2.mOps.get(size3)).mFragment;
                            if (fragment5 != null) {
                                createOrGetFragmentStateManager(fragment5).moveToExpectedState();
                            }
                        }
                    } else {
                        Iterator it2 = backStackRecord2.mOps.iterator();
                        while (it2.hasNext()) {
                            Fragment fragment6 = ((FragmentTransaction.C0247Op) it2.next()).mFragment;
                            if (fragment6 != null) {
                                createOrGetFragmentStateManager(fragment6).moveToExpectedState();
                            }
                        }
                    }
                }
                moveToState(this.mCurState, true);
                HashSet hashSet = new HashSet();
                for (int i17 = i; i17 < i2; i17++) {
                    Iterator it3 = ((BackStackRecord) arrayList.get(i17)).mOps.iterator();
                    while (it3.hasNext()) {
                        Fragment fragment7 = ((FragmentTransaction.C0247Op) it3.next()).mFragment;
                        if (fragment7 != null && (viewGroup = fragment7.mContainer) != null) {
                            hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, getSpecialEffectsControllerFactory()));
                        }
                    }
                }
                Iterator it4 = hashSet.iterator();
                while (it4.hasNext()) {
                    SpecialEffectsController specialEffectsController = (SpecialEffectsController) it4.next();
                    specialEffectsController.mOperationDirectionIsPop = booleanValue;
                    specialEffectsController.markPostponedState();
                    specialEffectsController.executePendingOperations();
                }
                for (int i18 = i; i18 < i2; i18++) {
                    BackStackRecord backStackRecord3 = (BackStackRecord) arrayList.get(i18);
                    if (((Boolean) arrayList2.get(i18)).booleanValue() && backStackRecord3.mIndex >= 0) {
                        backStackRecord3.mIndex = -1;
                    }
                    backStackRecord3.getClass();
                }
                return;
            }
            BackStackRecord backStackRecord4 = (BackStackRecord) arrayList3.get(i5);
            if (((Boolean) arrayList4.get(i5)).booleanValue()) {
                fragmentStore2 = fragmentStore4;
                int i19 = 1;
                ArrayList arrayList13 = this.mTmpAddedFragments;
                ArrayList arrayList14 = backStackRecord4.mOps;
                int size4 = arrayList14.size() - 1;
                while (size4 >= 0) {
                    FragmentTransaction.C0247Op c0247Op3 = (FragmentTransaction.C0247Op) arrayList14.get(size4);
                    int i20 = c0247Op3.mCmd;
                    if (i20 != i19) {
                        if (i20 != 3) {
                            switch (i20) {
                                case 8:
                                    fragment = null;
                                    break;
                                case 9:
                                    fragment = c0247Op3.mFragment;
                                    break;
                                case 10:
                                    c0247Op3.mCurrentMaxState = c0247Op3.mOldMaxState;
                                    break;
                            }
                            size4--;
                            i19 = 1;
                        }
                        arrayList13.add(c0247Op3.mFragment);
                        size4--;
                        i19 = 1;
                    }
                    arrayList13.remove(c0247Op3.mFragment);
                    size4--;
                    i19 = 1;
                }
            } else {
                ArrayList arrayList15 = this.mTmpAddedFragments;
                int i21 = 0;
                while (true) {
                    ArrayList arrayList16 = backStackRecord4.mOps;
                    if (i21 < arrayList16.size()) {
                        FragmentTransaction.C0247Op c0247Op4 = (FragmentTransaction.C0247Op) arrayList16.get(i21);
                        int i22 = c0247Op4.mCmd;
                        if (i22 != i6) {
                            if (i22 != 2) {
                                if (i22 == 3 || i22 == 6) {
                                    arrayList15.remove(c0247Op4.mFragment);
                                    Fragment fragment8 = c0247Op4.mFragment;
                                    if (fragment8 == fragment) {
                                        arrayList16.add(i21, new FragmentTransaction.C0247Op(9, fragment8));
                                        i21++;
                                        fragmentStore3 = fragmentStore4;
                                        i3 = 1;
                                        fragment = null;
                                    }
                                } else if (i22 == 7) {
                                    fragmentStore3 = fragmentStore4;
                                    i3 = 1;
                                } else if (i22 == 8) {
                                    arrayList16.add(i21, new FragmentTransaction.C0247Op(9, fragment, true));
                                    c0247Op4.mFromExpandedOp = true;
                                    i21++;
                                    fragment = c0247Op4.mFragment;
                                }
                                fragmentStore3 = fragmentStore4;
                                i3 = 1;
                            } else {
                                Fragment fragment9 = c0247Op4.mFragment;
                                int i23 = fragment9.mContainerId;
                                int size5 = arrayList15.size() - 1;
                                boolean z3 = false;
                                while (size5 >= 0) {
                                    FragmentStore fragmentStore6 = fragmentStore4;
                                    Fragment fragment10 = (Fragment) arrayList15.get(size5);
                                    if (fragment10.mContainerId != i23) {
                                        i4 = i23;
                                    } else if (fragment10 == fragment9) {
                                        i4 = i23;
                                        z3 = true;
                                    } else {
                                        if (fragment10 == fragment) {
                                            i4 = i23;
                                            r3 = 1;
                                            arrayList16.add(i21, new FragmentTransaction.C0247Op(9, fragment10, true));
                                            i21++;
                                            fragment = null;
                                        } else {
                                            i4 = i23;
                                            r3 = 1;
                                        }
                                        FragmentTransaction.C0247Op c0247Op5 = new FragmentTransaction.C0247Op(3, fragment10, (boolean) r3);
                                        c0247Op5.mEnterAnim = c0247Op4.mEnterAnim;
                                        c0247Op5.mPopEnterAnim = c0247Op4.mPopEnterAnim;
                                        c0247Op5.mExitAnim = c0247Op4.mExitAnim;
                                        c0247Op5.mPopExitAnim = c0247Op4.mPopExitAnim;
                                        arrayList16.add(i21, c0247Op5);
                                        arrayList15.remove(fragment10);
                                        i21 += r3;
                                        fragment = fragment;
                                    }
                                    size5--;
                                    i23 = i4;
                                    fragmentStore4 = fragmentStore6;
                                }
                                fragmentStore3 = fragmentStore4;
                                i3 = 1;
                                if (z3) {
                                    arrayList16.remove(i21);
                                    i21--;
                                } else {
                                    c0247Op4.mCmd = 1;
                                    c0247Op4.mFromExpandedOp = true;
                                    arrayList15.add(fragment9);
                                }
                            }
                            i21 += i3;
                            i6 = i3;
                            fragmentStore4 = fragmentStore3;
                        } else {
                            fragmentStore3 = fragmentStore4;
                            i3 = i6;
                        }
                        arrayList15.add(c0247Op4.mFragment);
                        i21 += i3;
                        i6 = i3;
                        fragmentStore4 = fragmentStore3;
                    } else {
                        fragmentStore2 = fragmentStore4;
                    }
                }
            }
            z2 = z2 || backStackRecord4.mAddToBackStack;
            i5++;
            arrayList3 = arrayList;
            arrayList4 = arrayList2;
            fragmentStore4 = fragmentStore2;
        }
    }

    public final Fragment findActiveFragment(String str) {
        return this.mFragmentStore.findActiveFragment(str);
    }

    public final Fragment findFragmentById(int i) {
        FragmentStore fragmentStore = this.mFragmentStore;
        ArrayList arrayList = fragmentStore.mAdded;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                for (FragmentStateManager fragmentStateManager : fragmentStore.mActive.values()) {
                    if (fragmentStateManager != null) {
                        Fragment fragment = fragmentStateManager.mFragment;
                        if (fragment.mFragmentId == i) {
                            return fragment;
                        }
                    }
                }
                return null;
            }
            Fragment fragment2 = (Fragment) arrayList.get(size);
            if (fragment2 != null && fragment2.mFragmentId == i) {
                return fragment2;
            }
        }
    }

    public final Fragment findFragmentByTag(String str) {
        FragmentStore fragmentStore = this.mFragmentStore;
        ArrayList arrayList = fragmentStore.mAdded;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                for (FragmentStateManager fragmentStateManager : fragmentStore.mActive.values()) {
                    if (fragmentStateManager != null) {
                        Fragment fragment = fragmentStateManager.mFragment;
                        if (str.equals(fragment.mTag)) {
                            return fragment;
                        }
                    }
                }
                return null;
            }
            Fragment fragment2 = (Fragment) arrayList.get(size);
            if (fragment2 != null && str.equals(fragment2.mTag)) {
                return fragment2;
            }
        }
    }

    public final void forcePostponedTransactions() {
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            SpecialEffectsController specialEffectsController = (SpecialEffectsController) it.next();
            if (specialEffectsController.mIsContainerPostponed) {
                specialEffectsController.mIsContainerPostponed = false;
                specialEffectsController.executePendingOperations();
            }
        }
    }

    public final ViewGroup getFragmentContainer(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.mContainerId > 0 && this.mContainer.onHasView()) {
            View onFindViewById = this.mContainer.onFindViewById(fragment.mContainerId);
            if (onFindViewById instanceof ViewGroup) {
                return (ViewGroup) onFindViewById;
            }
        }
        return null;
    }

    public final FragmentFactory getFragmentFactory() {
        FragmentFactory fragmentFactory = this.mFragmentFactory;
        if (fragmentFactory != null) {
            return fragmentFactory;
        }
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getFragmentFactory() : this.mHostFragmentFactory;
    }

    public final C02344 getSpecialEffectsControllerFactory() {
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getSpecialEffectsControllerFactory() : this.mDefaultSpecialEffectsControllerFactory;
    }

    public final boolean isParentAdded() {
        Fragment fragment = this.mParent;
        if (fragment == null) {
            return true;
        }
        return fragment.isAdded() && this.mParent.getParentFragmentManager().isParentAdded();
    }

    public final boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    public final void moveToState(int i, boolean z) {
        HashMap hashMap;
        FragmentHostCallback fragmentHostCallback;
        if (this.mHost == null && i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            FragmentStore fragmentStore = this.mFragmentStore;
            Iterator it = fragmentStore.mAdded.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                hashMap = fragmentStore.mActive;
                if (!hasNext) {
                    break;
                }
                FragmentStateManager fragmentStateManager = (FragmentStateManager) hashMap.get(((Fragment) it.next()).mWho);
                if (fragmentStateManager != null) {
                    fragmentStateManager.moveToExpectedState();
                }
            }
            int size = hashMap.size();
            Iterator it2 = hashMap.values().iterator();
            while (true) {
                boolean z2 = false;
                if (!it2.hasNext()) {
                    break;
                }
                FragmentStateManager fragmentStateManager2 = (FragmentStateManager) it2.next();
                if (fragmentStateManager2 != null) {
                    fragmentStateManager2.moveToExpectedState();
                    Fragment fragment = fragmentStateManager2.mFragment;
                    if (fragment.mRemoving && !fragment.isInBackStack()) {
                        z2 = true;
                    }
                    if (z2) {
                        if (fragment.mBeingSaved && !fragmentStore.mSavedState.containsKey(fragment.mWho)) {
                            fragmentStateManager2.saveState();
                        }
                        fragmentStore.makeInactive(fragmentStateManager2);
                    }
                }
                if (size != hashMap.size()) {
                    Log.d("FragmentManager", fragmentStore + "[enhanced for loop] expected Active size is " + size + ", but " + hashMap.size());
                }
            }
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    public final void noteStateNotSaved() {
        if (this.mHost == null) {
            return;
        }
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.mIsStateSaved = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.mChildFragmentManager.noteStateNotSaved();
            }
        }
    }

    public final boolean popBackStackImmediate() {
        return popBackStackImmediate(-1, 0);
    }

    public final boolean popBackStackState(ArrayList arrayList, ArrayList arrayList2, String str, int i, int i2) {
        boolean z = (i2 & 1) != 0;
        ArrayList arrayList3 = this.mBackStack;
        int i3 = -1;
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            if (str != null || i >= 0) {
                int size = this.mBackStack.size() - 1;
                while (size >= 0) {
                    BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(size);
                    if ((str != null && str.equals(backStackRecord.mName)) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break;
                    }
                    size--;
                }
                if (size >= 0) {
                    if (z) {
                        while (size > 0) {
                            int i4 = size - 1;
                            BackStackRecord backStackRecord2 = (BackStackRecord) this.mBackStack.get(i4);
                            if ((str == null || !str.equals(backStackRecord2.mName)) && (i < 0 || i != backStackRecord2.mIndex)) {
                                break;
                            }
                            size = i4;
                        }
                    } else if (size != this.mBackStack.size() - 1) {
                        size++;
                    }
                }
                i3 = size;
            } else {
                i3 = z ? 0 : (-1) + this.mBackStack.size();
            }
        }
        if (i3 < 0) {
            return false;
        }
        for (int size2 = this.mBackStack.size() - 1; size2 >= i3; size2--) {
            arrayList.add((BackStackRecord) this.mBackStack.remove(size2));
            arrayList2.add(Boolean.TRUE);
        }
        return true;
    }

    public final void removeFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Objects.toString(fragment);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            FragmentStore fragmentStore = this.mFragmentStore;
            synchronized (fragmentStore.mAdded) {
                fragmentStore.mAdded.remove(fragment);
            }
            fragment.mAdded = false;
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    public final void removeRedundantOperationsAndExecute(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!((BackStackRecord) arrayList.get(i)).mReorderingAllowed) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (((Boolean) arrayList2.get(i)).booleanValue()) {
                    while (i2 < size && ((Boolean) arrayList2.get(i2)).booleanValue() && !((BackStackRecord) arrayList.get(i2)).mReorderingAllowed) {
                        i2++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            executeOpsTogether(arrayList, arrayList2, i2, size);
        }
    }

    public final void restoreSaveStateInternal(Parcelable parcelable) {
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher;
        int i;
        FragmentStateManager fragmentStateManager;
        Bundle bundle;
        Bundle bundle2;
        Bundle bundle3 = (Bundle) parcelable;
        for (String str : bundle3.keySet()) {
            if (str.startsWith("result_") && (bundle2 = bundle3.getBundle(str)) != null) {
                bundle2.setClassLoader(this.mHost.mContext.getClassLoader());
                this.mResults.put(str.substring(7), bundle2);
            }
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : bundle3.keySet()) {
            if (str2.startsWith("fragment_") && (bundle = bundle3.getBundle(str2)) != null) {
                bundle.setClassLoader(this.mHost.mContext.getClassLoader());
                arrayList.add((FragmentState) bundle.getParcelable("state"));
            }
        }
        FragmentStore fragmentStore = this.mFragmentStore;
        HashMap hashMap = fragmentStore.mSavedState;
        hashMap.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            FragmentState fragmentState = (FragmentState) it.next();
            hashMap.put(fragmentState.mWho, fragmentState);
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState) bundle3.getParcelable("state");
        if (fragmentManagerState == null) {
            return;
        }
        HashMap hashMap2 = fragmentStore.mActive;
        hashMap2.clear();
        Log.d("FragmentManager", fragmentStore + " clear Active Fragments: " + hashMap2 + ", mActive size: " + hashMap2.size());
        Iterator it2 = fragmentManagerState.mActive.iterator();
        while (true) {
            boolean hasNext = it2.hasNext();
            fragmentLifecycleCallbacksDispatcher = this.mLifecycleCallbacksDispatcher;
            if (!hasNext) {
                break;
            }
            FragmentState savedState = fragmentStore.setSavedState((String) it2.next(), null);
            if (savedState != null) {
                Fragment fragment = (Fragment) this.mNonConfig.mRetainedFragments.get(savedState.mWho);
                if (fragment != null) {
                    if (isLoggingEnabled(2)) {
                        fragment.toString();
                    }
                    fragmentStateManager = new FragmentStateManager(fragmentLifecycleCallbacksDispatcher, fragmentStore, fragment, savedState);
                } else {
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.mContext.getClassLoader(), getFragmentFactory(), savedState);
                }
                Fragment fragment2 = fragmentStateManager.mFragment;
                fragment2.mFragmentManager = this;
                if (isLoggingEnabled(2)) {
                    fragment2.toString();
                }
                fragmentStateManager.restoreState(this.mHost.mContext.getClassLoader());
                fragmentStore.makeActive(fragmentStateManager);
                fragmentStateManager.mFragmentManagerState = this.mCurState;
            }
        }
        FragmentManagerViewModel fragmentManagerViewModel = this.mNonConfig;
        fragmentManagerViewModel.getClass();
        Iterator it3 = new ArrayList(fragmentManagerViewModel.mRetainedFragments.values()).iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            Fragment fragment3 = (Fragment) it3.next();
            if ((hashMap2.get(fragment3.mWho) != null ? 1 : 0) == 0) {
                if (isLoggingEnabled(2)) {
                    fragment3.toString();
                    Objects.toString(fragmentManagerState.mActive);
                }
                this.mNonConfig.removeRetainedFragment(fragment3);
                fragment3.mFragmentManager = this;
                FragmentStateManager fragmentStateManager2 = new FragmentStateManager(fragmentLifecycleCallbacksDispatcher, fragmentStore, fragment3);
                fragmentStateManager2.mFragmentManagerState = 1;
                fragmentStateManager2.moveToExpectedState();
                fragment3.mRemoving = true;
                fragmentStateManager2.moveToExpectedState();
            }
        }
        ArrayList<String> arrayList2 = fragmentManagerState.mAdded;
        fragmentStore.mAdded.clear();
        if (arrayList2 != null) {
            for (String str3 : arrayList2) {
                Fragment findActiveFragment = fragmentStore.findActiveFragment(str3);
                if (findActiveFragment == null) {
                    throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("No instantiated fragment for (", str3, ")"));
                }
                if (isLoggingEnabled(2)) {
                    findActiveFragment.toString();
                }
                fragmentStore.addFragment(findActiveFragment);
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            int i2 = 0;
            while (true) {
                BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.mBackStack;
                if (i2 >= backStackRecordStateArr.length) {
                    break;
                }
                BackStackRecordState backStackRecordState = backStackRecordStateArr[i2];
                backStackRecordState.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(this);
                int i3 = 0;
                int i4 = 0;
                while (i3 < backStackRecordState.mOps.length) {
                    FragmentTransaction.C0247Op c0247Op = new FragmentTransaction.C0247Op();
                    int i5 = i3 + 1;
                    c0247Op.mCmd = backStackRecordState.mOps[i3];
                    if (isLoggingEnabled(2)) {
                        Objects.toString(backStackRecord);
                        int i6 = backStackRecordState.mOps[i5];
                    }
                    c0247Op.mOldMaxState = Lifecycle.State.values()[backStackRecordState.mOldMaxLifecycleStates[i4]];
                    c0247Op.mCurrentMaxState = Lifecycle.State.values()[backStackRecordState.mCurrentMaxLifecycleStates[i4]];
                    int[] iArr = backStackRecordState.mOps;
                    int i7 = i5 + 1;
                    c0247Op.mFromExpandedOp = iArr[i5] != 0;
                    int i8 = i7 + 1;
                    int i9 = iArr[i7];
                    c0247Op.mEnterAnim = i9;
                    int i10 = i8 + 1;
                    int i11 = iArr[i8];
                    c0247Op.mExitAnim = i11;
                    int i12 = i10 + 1;
                    int i13 = iArr[i10];
                    c0247Op.mPopEnterAnim = i13;
                    int i14 = iArr[i12];
                    c0247Op.mPopExitAnim = i14;
                    backStackRecord.mEnterAnim = i9;
                    backStackRecord.mExitAnim = i11;
                    backStackRecord.mPopEnterAnim = i13;
                    backStackRecord.mPopExitAnim = i14;
                    backStackRecord.addOp(c0247Op);
                    i4++;
                    i3 = i12 + 1;
                }
                backStackRecord.mTransition = backStackRecordState.mTransition;
                backStackRecord.mName = backStackRecordState.mName;
                backStackRecord.mAddToBackStack = true;
                backStackRecord.mBreadCrumbTitleRes = backStackRecordState.mBreadCrumbTitleRes;
                backStackRecord.mBreadCrumbTitleText = backStackRecordState.mBreadCrumbTitleText;
                backStackRecord.mBreadCrumbShortTitleRes = backStackRecordState.mBreadCrumbShortTitleRes;
                backStackRecord.mBreadCrumbShortTitleText = backStackRecordState.mBreadCrumbShortTitleText;
                backStackRecord.mSharedElementSourceNames = backStackRecordState.mSharedElementSourceNames;
                backStackRecord.mSharedElementTargetNames = backStackRecordState.mSharedElementTargetNames;
                backStackRecord.mReorderingAllowed = backStackRecordState.mReorderingAllowed;
                backStackRecord.mIndex = backStackRecordState.mIndex;
                for (int i15 = 0; i15 < backStackRecordState.mFragmentWhos.size(); i15++) {
                    String str4 = (String) backStackRecordState.mFragmentWhos.get(i15);
                    if (str4 != null) {
                        ((FragmentTransaction.C0247Op) backStackRecord.mOps.get(i15)).mFragment = findActiveFragment(str4);
                    }
                }
                backStackRecord.bumpBackStackNesting(1);
                if (isLoggingEnabled(2)) {
                    backStackRecord.toString();
                    PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
                    backStackRecord.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.mBackStack.add(backStackRecord);
                i2++;
            }
        } else {
            this.mBackStack = null;
        }
        this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
        String str5 = fragmentManagerState.mPrimaryNavActiveWho;
        if (str5 != null) {
            Fragment findActiveFragment2 = findActiveFragment(str5);
            this.mPrimaryNav = findActiveFragment2;
            dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment2);
        }
        ArrayList arrayList3 = fragmentManagerState.mBackStackStateKeys;
        if (arrayList3 != null) {
            while (i < arrayList3.size()) {
                this.mBackStackStates.put((String) arrayList3.get(i), (BackStackState) fragmentManagerState.mBackStackStates.get(i));
                i++;
            }
        }
        this.mLaunchedFragments = new ArrayDeque(fragmentManagerState.mLaunchedFragments);
    }

    public final Bundle saveAllStateInternal() {
        BackStackRecordState[] backStackRecordStateArr;
        ArrayList arrayList;
        int size;
        Bundle bundle = new Bundle();
        forcePostponedTransactions();
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
        }
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.mIsStateSaved = true;
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.getClass();
        HashMap hashMap = fragmentStore.mActive;
        ArrayList arrayList2 = new ArrayList(hashMap.size());
        for (FragmentStateManager fragmentStateManager : hashMap.values()) {
            if (fragmentStateManager != null) {
                fragmentStateManager.saveState();
                Fragment fragment = fragmentStateManager.mFragment;
                arrayList2.add(fragment.mWho);
                if (isLoggingEnabled(2)) {
                    fragment.toString();
                    Objects.toString(fragment.mSavedFragmentState);
                }
            }
        }
        FragmentStore fragmentStore2 = this.mFragmentStore;
        fragmentStore2.getClass();
        ArrayList arrayList3 = new ArrayList(fragmentStore2.mSavedState.values());
        if (!arrayList3.isEmpty()) {
            FragmentStore fragmentStore3 = this.mFragmentStore;
            synchronized (fragmentStore3.mAdded) {
                backStackRecordStateArr = null;
                if (fragmentStore3.mAdded.isEmpty()) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList(fragmentStore3.mAdded.size());
                    Iterator it2 = fragmentStore3.mAdded.iterator();
                    while (it2.hasNext()) {
                        Fragment fragment2 = (Fragment) it2.next();
                        arrayList.add(fragment2.mWho);
                        if (isLoggingEnabled(2)) {
                            fragment2.toString();
                        }
                    }
                }
            }
            ArrayList arrayList4 = this.mBackStack;
            if (arrayList4 != null && (size = arrayList4.size()) > 0) {
                backStackRecordStateArr = new BackStackRecordState[size];
                for (int i = 0; i < size; i++) {
                    backStackRecordStateArr[i] = new BackStackRecordState((BackStackRecord) this.mBackStack.get(i));
                    if (isLoggingEnabled(2)) {
                        Objects.toString(this.mBackStack.get(i));
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.mActive = arrayList2;
            fragmentManagerState.mAdded = arrayList;
            fragmentManagerState.mBackStack = backStackRecordStateArr;
            fragmentManagerState.mBackStackIndex = this.mBackStackIndex.get();
            Fragment fragment3 = this.mPrimaryNav;
            if (fragment3 != null) {
                fragmentManagerState.mPrimaryNavActiveWho = fragment3.mWho;
            }
            fragmentManagerState.mBackStackStateKeys.addAll(this.mBackStackStates.keySet());
            fragmentManagerState.mBackStackStates.addAll(this.mBackStackStates.values());
            fragmentManagerState.mLaunchedFragments = new ArrayList(this.mLaunchedFragments);
            bundle.putParcelable("state", fragmentManagerState);
            for (String str : this.mResults.keySet()) {
                bundle.putBundle(KeyAttributes$$ExternalSyntheticOutline0.m21m("result_", str), (Bundle) this.mResults.get(str));
            }
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                FragmentState fragmentState = (FragmentState) it3.next();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("state", fragmentState);
                bundle.putBundle("fragment_" + fragmentState.mWho, bundle2);
            }
        }
        return bundle;
    }

    public final void scheduleCommit() {
        synchronized (this.mPendingActions) {
            boolean z = true;
            if (this.mPendingActions.size() != 1) {
                z = false;
            }
            if (z) {
                this.mHost.mHandler.removeCallbacks(this.mExecCommit);
                this.mHost.mHandler.post(this.mExecCommit);
                updateOnBackPressedCallbackEnabled();
            }
        }
    }

    public final void setExitAnimationOrder(Fragment fragment, boolean z) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || !(fragmentContainer instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) fragmentContainer).drawDisappearingViewsFirst = !z;
    }

    public final void setMaxLifecycle(Fragment fragment, Lifecycle.State state) {
        if (fragment.equals(findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this)) {
            fragment.mMaxState = state;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    public final void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment == null || (fragment.equals(findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this))) {
            Fragment fragment2 = this.mPrimaryNav;
            this.mPrimaryNav = fragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragment2);
            dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    public final void setVisibleRemovingFragment(Fragment fragment) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer != null) {
            Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
            if ((animationInfo == null ? 0 : animationInfo.mPopExitAnim) + (animationInfo == null ? 0 : animationInfo.mPopEnterAnim) + (animationInfo == null ? 0 : animationInfo.mExitAnim) + (animationInfo == null ? 0 : animationInfo.mEnterAnim) > 0) {
                if (fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag) == null) {
                    fragmentContainer.setTag(R.id.visible_removing_fragment_view_tag, fragment);
                }
                Fragment fragment2 = (Fragment) fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag);
                Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
                boolean z = animationInfo2 != null ? animationInfo2.mIsPop : false;
                if (fragment2.mAnimationInfo == null) {
                    return;
                }
                fragment2.ensureAnimationInfo().mIsPop = z;
            }
        }
    }

    public final void startPendingDeferredFragments() {
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragmentStateManagers()).iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = (FragmentStateManager) it.next();
            Fragment fragment = fragmentStateManager.mFragment;
            if (fragment.mDeferStart) {
                if (this.mExecutingActions) {
                    this.mHavePendingDeferredStart = true;
                } else {
                    fragment.mDeferStart = false;
                    fragmentStateManager.moveToExpectedState();
                }
            }
        }
    }

    public final void throwException(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            try {
                fragmentHostCallback.onDump(printWriter, new String[0]);
                throw runtimeException;
            } catch (Exception e) {
                Log.e("FragmentManager", "Failed dumping state", e);
                throw runtimeException;
            }
        }
        try {
            dump("  ", null, printWriter, new String[0]);
            throw runtimeException;
        } catch (Exception e2) {
            Log.e("FragmentManager", "Failed dumping state", e2);
            throw runtimeException;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.mParent;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.mParent)));
            sb.append("}");
        } else {
            FragmentHostCallback fragmentHostCallback = this.mHost;
            if (fragmentHostCallback != null) {
                sb.append(fragmentHostCallback.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.mHost)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    public final void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            try {
                if (!this.mPendingActions.isEmpty()) {
                    C02301 c02301 = this.mOnBackPressedCallback;
                    c02301.isEnabled = true;
                    Consumer consumer = c02301.enabledConsumer;
                    if (consumer != null) {
                        consumer.accept(Boolean.valueOf(c02301.isEnabled));
                    }
                    return;
                }
                C02301 c023012 = this.mOnBackPressedCallback;
                ArrayList arrayList = this.mBackStack;
                c023012.isEnabled = (arrayList != null ? arrayList.size() : 0) > 0 && isPrimaryNavigation(this.mParent);
                Consumer consumer2 = c023012.enabledConsumer;
                if (consumer2 != null) {
                    consumer2.accept(Boolean.valueOf(c023012.isEnabled));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean popBackStackImmediate(int i, int i2) {
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && i < 0 && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean popBackStackState = popBackStackState(this.mTmpRecords, this.mTmpIsPop, null, i, i2);
        if (popBackStackState) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.mActive.values().removeAll(Collections.singleton(null));
        return popBackStackState;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator() { // from class: androidx.fragment.app.FragmentManager.LaunchedFragmentInfo.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new LaunchedFragmentInfo[i];
            }
        };
        public final int mRequestCode;
        public final String mWho;

        public LaunchedFragmentInfo(String str, int i) {
            this.mWho = str;
            this.mRequestCode = i;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mWho);
            parcel.writeInt(this.mRequestCode);
        }

        public LaunchedFragmentInfo(Parcel parcel) {
            this.mWho = parcel.readString();
            this.mRequestCode = parcel.readInt();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class FragmentLifecycleCallbacks {
        public void onFragmentViewCreated(FragmentManager fragmentManager, Fragment fragment, View view) {
        }
    }
}
