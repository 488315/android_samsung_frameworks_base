package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.helper.widget.LogJson$JsonWriter$$ExternalSyntheticOutline0;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import com.samsung.android.biometrics.app.setting.R;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class FragmentManager {
    ArrayList<BackStackRecord> mBackStack;
    private FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    int mCurState;
    private C01274 mDefaultSpecialEffectsControllerFactory;
    private boolean mDestroyed;
    private Runnable mExecCommit;
    private boolean mExecutingActions;
    private boolean mHavePendingDeferredStart;
    private FragmentHostCallback<?> mHost;
    private FragmentFactory mHostFragmentFactory;
    ArrayDeque<LaunchedFragmentInfo> mLaunchedFragments;
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher;
    private final MenuProvider mMenuProvider;
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners;
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private final FragmentManager$$ExternalSyntheticLambda0 mOnConfigurationChangedListener;
    private final FragmentManager$$ExternalSyntheticLambda0 mOnMultiWindowModeChangedListener;
    private final FragmentManager$$ExternalSyntheticLambda0 mOnPictureInPictureModeChangedListener;
    private final FragmentManager$$ExternalSyntheticLambda0 mOnTrimMemoryListener;
    private Fragment mParent;
    Fragment mPrimaryNav;
    private ActivityResultLauncher<String[]> mRequestPermissions;
    private ActivityResultLauncher<Intent> mStartActivityForResult;
    private ActivityResultLauncher<Object> mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    private final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback() { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {
            FragmentManager.this.handleOnBackPressed();
        }
    };
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private final Map<String, BackStackState> mBackStackStates = Collections.synchronizedMap(new HashMap());
    private final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());

    /* renamed from: androidx.fragment.app.FragmentManager$4 */
    final class C01274 implements SpecialEffectsControllerFactory {
    }

    /* renamed from: androidx.fragment.app.FragmentManager$6 */
    class C01296 implements LifecycleEventObserver {
        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                throw null;
            }
            if (event == Lifecycle.Event.ON_DESTROY) {
                throw null;
            }
        }
    }

    static class FragmentIntentSenderContract extends ActivityResultContract<Object, ActivityResult> {
        @Override // androidx.activity.result.contract.ActivityResultContract
        public final Object parseResult(Intent intent, int i) {
            return new ActivityResult(intent, i);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new C01331();
        int mRequestCode;
        String mWho;

        /* renamed from: androidx.fragment.app.FragmentManager$LaunchedFragmentInfo$1 */
        final class C01331 implements Parcelable.Creator<LaunchedFragmentInfo> {
            @Override // android.os.Parcelable.Creator
            public final LaunchedFragmentInfo createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final LaunchedFragmentInfo[] newArray(int i) {
                return new LaunchedFragmentInfo[i];
            }
        }

        LaunchedFragmentInfo(Parcel parcel) {
            this.mWho = parcel.readString();
            this.mRequestCode = parcel.readInt();
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
    }

    interface OpGenerator {
        void generateOps(ArrayList arrayList, ArrayList arrayList2);
    }

    /* renamed from: $r8$lambda$OSbytOFWiBhg-JuC-68xxqeOPrI, reason: not valid java name */
    public static /* synthetic */ void m18$r8$lambda$OSbytOFWiBhgJuC68xxqeOPrI(FragmentManager fragmentManager, Integer num) {
        if (fragmentManager.isParentAdded() && num.intValue() == 80) {
            fragmentManager.dispatchLowMemory(false);
        }
    }

    public static /* synthetic */ void $r8$lambda$RBeuZ6iM1FSdhmZNBJJQnbWnePM(FragmentManager fragmentManager, PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo) {
        if (fragmentManager.isParentAdded()) {
            fragmentManager.dispatchPictureInPictureModeChanged(pictureInPictureModeChangedInfo.isInPictureInPictureMode(), false);
        }
    }

    /* renamed from: $r8$lambda$VJusB-xtSf6gkO7njmGWkIPixRc, reason: not valid java name */
    public static /* synthetic */ void m19$r8$lambda$VJusBxtSf6gkO7njmGWkIPixRc(FragmentManager fragmentManager, MultiWindowModeChangedInfo multiWindowModeChangedInfo) {
        if (fragmentManager.isParentAdded()) {
            fragmentManager.dispatchMultiWindowModeChanged(multiWindowModeChangedInfo.isInMultiWindowMode(), false);
        }
    }

    public static /* synthetic */ void $r8$lambda$fsqPmeLyp8R2NG2vLmIMKnwX3q4(FragmentManager fragmentManager) {
        if (fragmentManager.isParentAdded()) {
            fragmentManager.dispatchConfigurationChanged(false);
        }
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v13, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v14, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v15, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0] */
    public FragmentManager() {
        Collections.synchronizedMap(new HashMap());
        this.mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
        this.mOnAttachListeners = new CopyOnWriteArrayList<>();
        final int i = 0;
        this.mOnConfigurationChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
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
                        FragmentManager.$r8$lambda$fsqPmeLyp8R2NG2vLmIMKnwX3q4(fragmentManager);
                        break;
                    case 1:
                        FragmentManager.m18$r8$lambda$OSbytOFWiBhgJuC68xxqeOPrI(fragmentManager, (Integer) obj);
                        break;
                    case 2:
                        FragmentManager.m19$r8$lambda$VJusBxtSf6gkO7njmGWkIPixRc(fragmentManager, (MultiWindowModeChangedInfo) obj);
                        break;
                    default:
                        FragmentManager.$r8$lambda$RBeuZ6iM1FSdhmZNBJJQnbWnePM(fragmentManager, (PictureInPictureModeChangedInfo) obj);
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mOnTrimMemoryListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
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
                        FragmentManager.$r8$lambda$fsqPmeLyp8R2NG2vLmIMKnwX3q4(fragmentManager);
                        break;
                    case 1:
                        FragmentManager.m18$r8$lambda$OSbytOFWiBhgJuC68xxqeOPrI(fragmentManager, (Integer) obj);
                        break;
                    case 2:
                        FragmentManager.m19$r8$lambda$VJusBxtSf6gkO7njmGWkIPixRc(fragmentManager, (MultiWindowModeChangedInfo) obj);
                        break;
                    default:
                        FragmentManager.$r8$lambda$RBeuZ6iM1FSdhmZNBJJQnbWnePM(fragmentManager, (PictureInPictureModeChangedInfo) obj);
                        break;
                }
            }
        };
        final int i3 = 2;
        this.mOnMultiWindowModeChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
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
                        FragmentManager.$r8$lambda$fsqPmeLyp8R2NG2vLmIMKnwX3q4(fragmentManager);
                        break;
                    case 1:
                        FragmentManager.m18$r8$lambda$OSbytOFWiBhgJuC68xxqeOPrI(fragmentManager, (Integer) obj);
                        break;
                    case 2:
                        FragmentManager.m19$r8$lambda$VJusBxtSf6gkO7njmGWkIPixRc(fragmentManager, (MultiWindowModeChangedInfo) obj);
                        break;
                    default:
                        FragmentManager.$r8$lambda$RBeuZ6iM1FSdhmZNBJJQnbWnePM(fragmentManager, (PictureInPictureModeChangedInfo) obj);
                        break;
                }
            }
        };
        final int i4 = 3;
        this.mOnPictureInPictureModeChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                int i22 = i4;
                FragmentManager fragmentManager = this.f$0;
                switch (i22) {
                    case 0:
                        FragmentManager.$r8$lambda$fsqPmeLyp8R2NG2vLmIMKnwX3q4(fragmentManager);
                        break;
                    case 1:
                        FragmentManager.m18$r8$lambda$OSbytOFWiBhgJuC68xxqeOPrI(fragmentManager, (Integer) obj);
                        break;
                    case 2:
                        FragmentManager.m19$r8$lambda$VJusBxtSf6gkO7njmGWkIPixRc(fragmentManager, (MultiWindowModeChangedInfo) obj);
                        break;
                    default:
                        FragmentManager.$r8$lambda$RBeuZ6iM1FSdhmZNBJJQnbWnePM(fragmentManager, (PictureInPictureModeChangedInfo) obj);
                        break;
                }
            }
        };
        this.mMenuProvider = new MenuProvider() { // from class: androidx.fragment.app.FragmentManager.2
            @Override // androidx.core.view.MenuProvider
            public final void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                FragmentManager.this.dispatchCreateOptionsMenu();
            }

            @Override // androidx.core.view.MenuProvider
            public final void onMenuClosed(Menu menu) {
                FragmentManager.this.dispatchOptionsMenuClosed();
            }

            @Override // androidx.core.view.MenuProvider
            public final boolean onMenuItemSelected(MenuItem menuItem) {
                return FragmentManager.this.dispatchOptionsItemSelected();
            }

            @Override // androidx.core.view.MenuProvider
            public final void onPrepareMenu(Menu menu) {
                FragmentManager.this.dispatchPrepareOptionsMenu();
            }
        };
        this.mCurState = -1;
        this.mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
            @Override // androidx.fragment.app.FragmentFactory
            public final Fragment instantiate(String str) {
                FragmentManager fragmentManager = FragmentManager.this;
                FragmentHostCallback<?> host = fragmentManager.getHost();
                Context context = fragmentManager.getHost().getContext();
                host.getClass();
                Object obj = Fragment.USE_DEFAULT_TRANSITION;
                try {
                    return FragmentFactory.loadFragmentClass(context.getClassLoader(), str).getConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (IllegalAccessException e) {
                    throw new Fragment.InstantiationException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
                } catch (InstantiationException e2) {
                    throw new Fragment.InstantiationException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
                } catch (NoSuchMethodException e3) {
                    throw new Fragment.InstantiationException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
                } catch (InvocationTargetException e4) {
                    throw new Fragment.InstantiationException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
                }
            }
        };
        this.mDefaultSpecialEffectsControllerFactory = new C01274();
        this.mLaunchedFragments = new ArrayDeque<>();
        this.mExecCommit = new Runnable() { // from class: androidx.fragment.app.FragmentManager.5
            @Override // java.lang.Runnable
            public final void run() {
                FragmentManager.this.execPendingActions(true);
            }
        };
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private Set<SpecialEffectsController> collectAllSpecialEffectsController() {
        Object defaultSpecialEffectsController;
        HashSet hashSet = new HashSet();
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragmentStateManagers()).iterator();
        while (it.hasNext()) {
            ViewGroup viewGroup = ((FragmentStateManager) it.next()).getFragment().mContainer;
            if (viewGroup != null) {
                SpecialEffectsControllerFactory factory = getSpecialEffectsControllerFactory();
                Intrinsics.checkNotNullParameter(factory, "factory");
                Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
                if (tag instanceof SpecialEffectsController) {
                    defaultSpecialEffectsController = (SpecialEffectsController) tag;
                } else {
                    defaultSpecialEffectsController = new DefaultSpecialEffectsController(viewGroup);
                    viewGroup.setTag(R.id.special_effects_controller_view_tag, defaultSpecialEffectsController);
                }
                hashSet.add(defaultSpecialEffectsController);
            }
        }
        return hashSet;
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(Fragment fragment) {
        if (fragment == null || !fragment.equals(findActiveFragment(fragment.mWho))) {
            return;
        }
        fragment.performPrimaryNavigationFragmentChanged();
    }

    private void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.dispatchStateChange(i);
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

    private void endAnimatingAwayFragments() {
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
        }
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            if (!this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z && isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<>();
            this.mTmpIsPop = new ArrayList<>();
        }
    }

    private void executeOpsTogether(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2) {
        ViewGroup viewGroup;
        Object defaultSpecialEffectsController;
        FragmentStore fragmentStore;
        FragmentStore fragmentStore2;
        FragmentStore fragmentStore3;
        int i3;
        int i4;
        int i5;
        ArrayList<BackStackRecord> arrayList3 = arrayList;
        ArrayList<Boolean> arrayList4 = arrayList2;
        boolean z = arrayList3.get(i).mReorderingAllowed;
        ArrayList<Fragment> arrayList5 = this.mTmpAddedFragments;
        if (arrayList5 == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            arrayList5.clear();
        }
        ArrayList<Fragment> arrayList6 = this.mTmpAddedFragments;
        FragmentStore fragmentStore4 = this.mFragmentStore;
        arrayList6.addAll(fragmentStore4.getFragments());
        Fragment fragment = this.mPrimaryNav;
        int i6 = i;
        boolean z2 = false;
        while (true) {
            int i7 = 1;
            if (i6 >= i2) {
                FragmentStore fragmentStore5 = fragmentStore4;
                this.mTmpAddedFragments.clear();
                if (!z && this.mCurState >= 1) {
                    for (int i8 = i; i8 < i2; i8++) {
                        Iterator<FragmentTransaction.C0138Op> it = arrayList.get(i8).mOps.iterator();
                        while (it.hasNext()) {
                            Fragment fragment2 = it.next().mFragment;
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
                for (int i9 = i; i9 < i2; i9++) {
                    BackStackRecord backStackRecord = arrayList.get(i9);
                    if (arrayList2.get(i9).booleanValue()) {
                        backStackRecord.bumpBackStackNesting(-1);
                        boolean z3 = true;
                        int size = backStackRecord.mOps.size() - 1;
                        while (size >= 0) {
                            FragmentTransaction.C0138Op c0138Op = backStackRecord.mOps.get(size);
                            Fragment fragment3 = c0138Op.mFragment;
                            if (fragment3 != null) {
                                fragment3.setPopDirection(z3);
                                int i10 = backStackRecord.mTransition;
                                int i11 = 8194;
                                int i12 = 4097;
                                if (i10 != 4097) {
                                    if (i10 != 8194) {
                                        i11 = 4100;
                                        i12 = 8197;
                                        if (i10 != 8197) {
                                            if (i10 == 4099) {
                                                i11 = 4099;
                                            } else if (i10 != 4100) {
                                                i11 = 0;
                                            }
                                        }
                                    }
                                    i11 = i12;
                                }
                                fragment3.setNextTransition(i11);
                                fragment3.setSharedElementNames(backStackRecord.mSharedElementTargetNames, backStackRecord.mSharedElementSourceNames);
                            }
                            int i13 = c0138Op.mCmd;
                            FragmentManager fragmentManager = backStackRecord.mManager;
                            switch (i13) {
                                case 1:
                                    fragment3.setAnimations(c0138Op.mEnterAnim, c0138Op.mExitAnim, c0138Op.mPopEnterAnim, c0138Op.mPopExitAnim);
                                    fragmentManager.setExitAnimationOrder(fragment3, true);
                                    fragmentManager.removeFragment(fragment3);
                                    break;
                                case 2:
                                default:
                                    throw new IllegalArgumentException("Unknown cmd: " + c0138Op.mCmd);
                                case 3:
                                    fragment3.setAnimations(c0138Op.mEnterAnim, c0138Op.mExitAnim, c0138Op.mPopEnterAnim, c0138Op.mPopExitAnim);
                                    fragmentManager.addFragment(fragment3);
                                    break;
                                case 4:
                                    fragment3.setAnimations(c0138Op.mEnterAnim, c0138Op.mExitAnim, c0138Op.mPopEnterAnim, c0138Op.mPopExitAnim);
                                    fragmentManager.getClass();
                                    showFragment(fragment3);
                                    break;
                                case 5:
                                    fragment3.setAnimations(c0138Op.mEnterAnim, c0138Op.mExitAnim, c0138Op.mPopEnterAnim, c0138Op.mPopExitAnim);
                                    fragmentManager.setExitAnimationOrder(fragment3, true);
                                    fragmentManager.hideFragment(fragment3);
                                    break;
                                case 6:
                                    fragment3.setAnimations(c0138Op.mEnterAnim, c0138Op.mExitAnim, c0138Op.mPopEnterAnim, c0138Op.mPopExitAnim);
                                    fragmentManager.attachFragment(fragment3);
                                    break;
                                case 7:
                                    fragment3.setAnimations(c0138Op.mEnterAnim, c0138Op.mExitAnim, c0138Op.mPopEnterAnim, c0138Op.mPopExitAnim);
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
                                    fragmentManager.setMaxLifecycle(fragment3, c0138Op.mOldMaxState);
                                    break;
                            }
                            size--;
                            z3 = true;
                        }
                    } else {
                        backStackRecord.bumpBackStackNesting(1);
                        int size2 = backStackRecord.mOps.size();
                        for (int i14 = 0; i14 < size2; i14++) {
                            FragmentTransaction.C0138Op c0138Op2 = backStackRecord.mOps.get(i14);
                            Fragment fragment4 = c0138Op2.mFragment;
                            if (fragment4 != null) {
                                fragment4.setPopDirection(false);
                                fragment4.setNextTransition(backStackRecord.mTransition);
                                fragment4.setSharedElementNames(backStackRecord.mSharedElementSourceNames, backStackRecord.mSharedElementTargetNames);
                            }
                            int i15 = c0138Op2.mCmd;
                            FragmentManager fragmentManager2 = backStackRecord.mManager;
                            switch (i15) {
                                case 1:
                                    fragment4.setAnimations(c0138Op2.mEnterAnim, c0138Op2.mExitAnim, c0138Op2.mPopEnterAnim, c0138Op2.mPopExitAnim);
                                    fragmentManager2.setExitAnimationOrder(fragment4, false);
                                    fragmentManager2.addFragment(fragment4);
                                case 2:
                                default:
                                    throw new IllegalArgumentException("Unknown cmd: " + c0138Op2.mCmd);
                                case 3:
                                    fragment4.setAnimations(c0138Op2.mEnterAnim, c0138Op2.mExitAnim, c0138Op2.mPopEnterAnim, c0138Op2.mPopExitAnim);
                                    fragmentManager2.removeFragment(fragment4);
                                case 4:
                                    fragment4.setAnimations(c0138Op2.mEnterAnim, c0138Op2.mExitAnim, c0138Op2.mPopEnterAnim, c0138Op2.mPopExitAnim);
                                    fragmentManager2.hideFragment(fragment4);
                                case 5:
                                    fragment4.setAnimations(c0138Op2.mEnterAnim, c0138Op2.mExitAnim, c0138Op2.mPopEnterAnim, c0138Op2.mPopExitAnim);
                                    fragmentManager2.setExitAnimationOrder(fragment4, false);
                                    showFragment(fragment4);
                                case 6:
                                    fragment4.setAnimations(c0138Op2.mEnterAnim, c0138Op2.mExitAnim, c0138Op2.mPopEnterAnim, c0138Op2.mPopExitAnim);
                                    fragmentManager2.detachFragment(fragment4);
                                case 7:
                                    fragment4.setAnimations(c0138Op2.mEnterAnim, c0138Op2.mExitAnim, c0138Op2.mPopEnterAnim, c0138Op2.mPopExitAnim);
                                    fragmentManager2.setExitAnimationOrder(fragment4, false);
                                    fragmentManager2.attachFragment(fragment4);
                                case 8:
                                    fragmentManager2.setPrimaryNavigationFragment(fragment4);
                                case 9:
                                    fragmentManager2.setPrimaryNavigationFragment(null);
                                case 10:
                                    fragmentManager2.setMaxLifecycle(fragment4, c0138Op2.mCurrentMaxState);
                            }
                        }
                    }
                }
                boolean booleanValue = arrayList2.get(i2 - 1).booleanValue();
                for (int i16 = i; i16 < i2; i16++) {
                    BackStackRecord backStackRecord2 = arrayList.get(i16);
                    if (booleanValue) {
                        for (int size3 = backStackRecord2.mOps.size() - 1; size3 >= 0; size3--) {
                            Fragment fragment5 = backStackRecord2.mOps.get(size3).mFragment;
                            if (fragment5 != null) {
                                createOrGetFragmentStateManager(fragment5).moveToExpectedState();
                            }
                        }
                    } else {
                        Iterator<FragmentTransaction.C0138Op> it2 = backStackRecord2.mOps.iterator();
                        while (it2.hasNext()) {
                            Fragment fragment6 = it2.next().mFragment;
                            if (fragment6 != null) {
                                createOrGetFragmentStateManager(fragment6).moveToExpectedState();
                            }
                        }
                    }
                }
                moveToState(this.mCurState, true);
                HashSet hashSet = new HashSet();
                for (int i17 = i; i17 < i2; i17++) {
                    Iterator<FragmentTransaction.C0138Op> it3 = arrayList.get(i17).mOps.iterator();
                    while (it3.hasNext()) {
                        Fragment fragment7 = it3.next().mFragment;
                        if (fragment7 != null && (viewGroup = fragment7.mContainer) != null) {
                            Intrinsics.checkNotNullExpressionValue(getSpecialEffectsControllerFactory(), "fragmentManager.specialEffectsControllerFactory");
                            Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
                            if (tag instanceof SpecialEffectsController) {
                                defaultSpecialEffectsController = (SpecialEffectsController) tag;
                            } else {
                                defaultSpecialEffectsController = new DefaultSpecialEffectsController(viewGroup);
                                viewGroup.setTag(R.id.special_effects_controller_view_tag, defaultSpecialEffectsController);
                            }
                            hashSet.add(defaultSpecialEffectsController);
                        }
                    }
                }
                Iterator it4 = hashSet.iterator();
                while (it4.hasNext()) {
                    SpecialEffectsController specialEffectsController = (SpecialEffectsController) it4.next();
                    specialEffectsController.updateOperationDirection(booleanValue);
                    specialEffectsController.markPostponedState();
                    specialEffectsController.executePendingOperations();
                }
                for (int i18 = i; i18 < i2; i18++) {
                    BackStackRecord backStackRecord3 = arrayList.get(i18);
                    if (arrayList2.get(i18).booleanValue() && backStackRecord3.mIndex >= 0) {
                        backStackRecord3.mIndex = -1;
                    }
                    backStackRecord3.getClass();
                }
                return;
            }
            BackStackRecord backStackRecord4 = arrayList3.get(i6);
            if (arrayList4.get(i6).booleanValue()) {
                fragmentStore2 = fragmentStore4;
                int i19 = 1;
                ArrayList<Fragment> arrayList7 = this.mTmpAddedFragments;
                int size4 = backStackRecord4.mOps.size() - 1;
                while (size4 >= 0) {
                    FragmentTransaction.C0138Op c0138Op3 = backStackRecord4.mOps.get(size4);
                    int i20 = c0138Op3.mCmd;
                    if (i20 != i19) {
                        if (i20 != 3) {
                            switch (i20) {
                                case 8:
                                    fragment = null;
                                    break;
                                case 9:
                                    fragment = c0138Op3.mFragment;
                                    break;
                                case 10:
                                    c0138Op3.mCurrentMaxState = c0138Op3.mOldMaxState;
                                    break;
                            }
                            size4--;
                            i19 = 1;
                        }
                        arrayList7.add(c0138Op3.mFragment);
                        size4--;
                        i19 = 1;
                    }
                    arrayList7.remove(c0138Op3.mFragment);
                    size4--;
                    i19 = 1;
                }
            } else {
                ArrayList<Fragment> arrayList8 = this.mTmpAddedFragments;
                int i21 = 0;
                while (i21 < backStackRecord4.mOps.size()) {
                    FragmentTransaction.C0138Op c0138Op4 = backStackRecord4.mOps.get(i21);
                    int i22 = c0138Op4.mCmd;
                    if (i22 == i7) {
                        fragmentStore3 = fragmentStore4;
                        i3 = i7;
                    } else if (i22 != 2) {
                        if (i22 == 3 || i22 == 6) {
                            arrayList8.remove(c0138Op4.mFragment);
                            Fragment fragment8 = c0138Op4.mFragment;
                            if (fragment8 == fragment) {
                                backStackRecord4.mOps.add(i21, new FragmentTransaction.C0138Op(9, fragment8));
                                i21++;
                                fragmentStore3 = fragmentStore4;
                                i3 = 1;
                                fragment = null;
                                i21 += i3;
                                i7 = i3;
                                fragmentStore4 = fragmentStore3;
                            }
                        } else if (i22 == 7) {
                            fragmentStore3 = fragmentStore4;
                            i3 = 1;
                        } else if (i22 == 8) {
                            backStackRecord4.mOps.add(i21, new FragmentTransaction.C0138Op(9, fragment, 0));
                            c0138Op4.mFromExpandedOp = true;
                            i21++;
                            fragment = c0138Op4.mFragment;
                        }
                        fragmentStore3 = fragmentStore4;
                        i3 = 1;
                        i21 += i3;
                        i7 = i3;
                        fragmentStore4 = fragmentStore3;
                    } else {
                        Fragment fragment9 = c0138Op4.mFragment;
                        int i23 = fragment9.mContainerId;
                        int size5 = arrayList8.size() - 1;
                        boolean z4 = false;
                        while (size5 >= 0) {
                            Fragment fragment10 = arrayList8.get(size5);
                            FragmentStore fragmentStore6 = fragmentStore4;
                            if (fragment10.mContainerId != i23) {
                                i4 = i23;
                            } else if (fragment10 == fragment9) {
                                i4 = i23;
                                z4 = true;
                            } else {
                                if (fragment10 == fragment) {
                                    i4 = i23;
                                    i5 = 0;
                                    backStackRecord4.mOps.add(i21, new FragmentTransaction.C0138Op(9, fragment10, 0));
                                    i21++;
                                    fragment = null;
                                } else {
                                    i4 = i23;
                                    i5 = 0;
                                }
                                FragmentTransaction.C0138Op c0138Op5 = new FragmentTransaction.C0138Op(3, fragment10, i5);
                                c0138Op5.mEnterAnim = c0138Op4.mEnterAnim;
                                c0138Op5.mPopEnterAnim = c0138Op4.mPopEnterAnim;
                                c0138Op5.mExitAnim = c0138Op4.mExitAnim;
                                c0138Op5.mPopExitAnim = c0138Op4.mPopExitAnim;
                                backStackRecord4.mOps.add(i21, c0138Op5);
                                arrayList8.remove(fragment10);
                                i21++;
                            }
                            size5--;
                            fragmentStore4 = fragmentStore6;
                            i23 = i4;
                        }
                        fragmentStore3 = fragmentStore4;
                        if (z4) {
                            backStackRecord4.mOps.remove(i21);
                            i21--;
                            i3 = 1;
                            i21 += i3;
                            i7 = i3;
                            fragmentStore4 = fragmentStore3;
                        } else {
                            i3 = 1;
                            c0138Op4.mCmd = 1;
                            c0138Op4.mFromExpandedOp = true;
                            arrayList8.add(fragment9);
                            i21 += i3;
                            i7 = i3;
                            fragmentStore4 = fragmentStore3;
                        }
                    }
                    arrayList8.add(c0138Op4.mFragment);
                    i21 += i3;
                    i7 = i3;
                    fragmentStore4 = fragmentStore3;
                }
                fragmentStore2 = fragmentStore4;
            }
            z2 = z2 || backStackRecord4.mAddToBackStack;
            i6++;
            arrayList3 = arrayList;
            arrayList4 = arrayList2;
            fragmentStore4 = fragmentStore2;
        }
    }

    private ViewGroup getFragmentContainer(Fragment fragment) {
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

    public static boolean isLoggingEnabled(int i) {
        return Log.isLoggable("FragmentManager", i);
    }

    private static boolean isMenuAvailable(Fragment fragment) {
        fragment.getClass();
        Iterator it = ((ArrayList) fragment.mChildFragmentManager.mFragmentStore.getActiveFragments()).iterator();
        boolean z = false;
        while (it.hasNext()) {
            Fragment fragment2 = (Fragment) it.next();
            if (fragment2 != null) {
                z = isMenuAvailable(fragment2);
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    private boolean isParentAdded() {
        Fragment fragment = this.mParent;
        if (fragment == null) {
            return true;
        }
        return fragment.isAdded() && this.mParent.getParentFragmentManager().isParentAdded();
    }

    static boolean isParentMenuVisible(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.mMenuVisible && (fragment.mFragmentManager == null || isParentMenuVisible(fragment.mParentFragment));
    }

    static boolean isPrimaryNavigation(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.mFragmentManager;
        return fragment.equals(fragmentManager.mPrimaryNav) && isPrimaryNavigation(fragmentManager.mParent);
    }

    private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
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
            if (!arrayList.get(i).mReorderingAllowed) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (arrayList2.get(i).booleanValue()) {
                    while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).mReorderingAllowed) {
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

    private void setVisibleRemovingFragment(Fragment fragment) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer != null) {
            Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
            if ((animationInfo == null ? 0 : animationInfo.mEnterAnim) + (animationInfo == null ? 0 : animationInfo.mExitAnim) + (animationInfo == null ? 0 : animationInfo.mPopEnterAnim) + (animationInfo == null ? 0 : animationInfo.mPopExitAnim) > 0) {
                if (fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag) == null) {
                    fragmentContainer.setTag(R.id.visible_removing_fragment_view_tag, fragment);
                }
                Fragment fragment2 = (Fragment) fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag);
                Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
                fragment2.setPopDirection(animationInfo2 != null ? animationInfo2.mIsPop : false);
            }
        }
    }

    static void showFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            fragment.mHiddenChanged = !fragment.mHiddenChanged;
        }
    }

    private void startPendingDeferredFragments() {
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragmentStateManagers()).iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = (FragmentStateManager) it.next();
            Fragment fragment = fragmentStateManager.getFragment();
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

    private void throwException(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter());
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
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

    private void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            if (!this.mPendingActions.isEmpty()) {
                this.mOnBackPressedCallback.setEnabled(true);
                return;
            }
            OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
            ArrayList<BackStackRecord> arrayList = this.mBackStack;
            onBackPressedCallback.setEnabled((arrayList != null ? arrayList.size() : 0) > 0 && isPrimaryNavigation(this.mParent));
        }
    }

    final FragmentStateManager addFragment(Fragment fragment) {
        String str = fragment.mPreviousWho;
        if (str != null) {
            FragmentStrictMode.onFragmentReuse(fragment, str);
        }
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        FragmentStateManager createOrGetFragmentStateManager = createOrGetFragmentStateManager(fragment);
        fragment.mFragmentManager = this;
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.makeActive(createOrGetFragmentStateManager);
        if (!fragment.mDetached) {
            fragmentStore.addFragment(fragment);
            fragment.mRemoving = false;
            fragment.mHiddenChanged = false;
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return createOrGetFragmentStateManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint({"SyntheticAccessor"})
    final void attachController(FragmentHostCallback<?> fragmentHostCallback, FragmentContainer fragmentContainer, final Fragment fragment) {
        String str;
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        CopyOnWriteArrayList<FragmentOnAttachListener> copyOnWriteArrayList = this.mOnAttachListeners;
        if (fragment != null) {
            copyOnWriteArrayList.add(new FragmentOnAttachListener() { // from class: androidx.fragment.app.FragmentManager.7
                @Override // androidx.fragment.app.FragmentOnAttachListener
                public final void onAttachFragment$1() {
                    Fragment.this.getClass();
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
            this.mNonConfig = fragment.mFragmentManager.mNonConfig.getChildNonConfig(fragment);
        } else if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore());
        } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
        }
        this.mNonConfig.setIsStateSaved(isStateSaved());
        this.mFragmentStore.setNonConfig(this.mNonConfig);
        Object obj = this.mHost;
        if ((obj instanceof SavedStateRegistryOwner) && fragment == null) {
            SavedStateRegistry savedStateRegistry = ((SavedStateRegistryOwner) obj).getSavedStateRegistry();
            savedStateRegistry.registerSavedStateProvider("android:support:fragments", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
                @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
                public final Bundle saveState() {
                    return FragmentManager.this.saveAllStateInternal();
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
            if (fragment != null) {
                str = fragment.mWho + ":";
            } else {
                str = "";
            }
            String str2 = "FragmentManager:" + str;
            this.mStartActivityForResult = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, "StartActivityForResult"), new ActivityResultContracts$StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.8
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(ActivityResult activityResult) {
                    ActivityResult activityResult2 = activityResult;
                    FragmentManager fragmentManager = FragmentManager.this;
                    LaunchedFragmentInfo pollFirst = fragmentManager.mLaunchedFragments.pollFirst();
                    if (pollFirst == null) {
                        Log.w("FragmentManager", "No Activities were started for result for " + this);
                        return;
                    }
                    String str3 = pollFirst.mWho;
                    int i = pollFirst.mRequestCode;
                    Fragment findFragmentByWho = fragmentManager.mFragmentStore.findFragmentByWho(str3);
                    if (findFragmentByWho != null) {
                        findFragmentByWho.onActivityResult(i, activityResult2.getResultCode(), activityResult2.getData());
                        return;
                    }
                    Log.w("FragmentManager", "Activity result delivered for unknown Fragment " + str3);
                }
            });
            this.mStartIntentSenderForResult = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, "StartIntentSenderForResult"), new FragmentIntentSenderContract(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.9
                @Override // androidx.activity.result.ActivityResultCallback
                public final void onActivityResult(ActivityResult activityResult) {
                    ActivityResult activityResult2 = activityResult;
                    FragmentManager fragmentManager = FragmentManager.this;
                    LaunchedFragmentInfo pollFirst = fragmentManager.mLaunchedFragments.pollFirst();
                    if (pollFirst == null) {
                        Log.w("FragmentManager", "No IntentSenders were started for " + this);
                        return;
                    }
                    String str3 = pollFirst.mWho;
                    int i = pollFirst.mRequestCode;
                    Fragment findFragmentByWho = fragmentManager.mFragmentStore.findFragmentByWho(str3);
                    if (findFragmentByWho != null) {
                        findFragmentByWho.onActivityResult(i, activityResult2.getResultCode(), activityResult2.getData());
                        return;
                    }
                    Log.w("FragmentManager", "Intent Sender result delivered for unknown Fragment " + str3);
                }
            });
            this.mRequestPermissions = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str2, "RequestPermissions"), new ActivityResultContracts$RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: androidx.fragment.app.FragmentManager.10
                @Override // androidx.activity.result.ActivityResultCallback
                @SuppressLint({"SyntheticAccessor"})
                public final void onActivityResult(Map<String, Boolean> map) {
                    Map<String, Boolean> map2 = map;
                    ArrayList arrayList = new ArrayList(map2.values());
                    int[] iArr = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        iArr[i] = ((Boolean) arrayList.get(i)).booleanValue() ? 0 : -1;
                    }
                    FragmentManager fragmentManager = FragmentManager.this;
                    LaunchedFragmentInfo pollFirst = fragmentManager.mLaunchedFragments.pollFirst();
                    if (pollFirst == null) {
                        Log.w("FragmentManager", "No permissions were requested for " + this);
                    } else {
                        String str3 = pollFirst.mWho;
                        if (fragmentManager.mFragmentStore.findFragmentByWho(str3) == null) {
                            Log.w("FragmentManager", "Permission request result delivered for unknown Fragment " + str3);
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

    final void attachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (fragment.mAdded) {
                return;
            }
            this.mFragmentStore.addFragment(fragment);
            if (isLoggingEnabled(2)) {
                Log.v("FragmentManager", "add from attach: " + fragment);
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    final FragmentStateManager createOrGetFragmentStateManager(Fragment fragment) {
        String str = fragment.mWho;
        FragmentStore fragmentStore = this.mFragmentStore;
        FragmentStateManager fragmentStateManager = fragmentStore.getFragmentStateManager(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager;
        }
        FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, fragmentStore, fragment);
        fragmentStateManager2.restoreState(this.mHost.getContext().getClassLoader());
        fragmentStateManager2.setFragmentManagerState(this.mCurState);
        return fragmentStateManager2;
    }

    final void detachFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (fragment.mDetached) {
            return;
        }
        fragment.mDetached = true;
        if (fragment.mAdded) {
            if (isLoggingEnabled(2)) {
                Log.v("FragmentManager", "remove from detach: " + fragment);
            }
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            setVisibleRemovingFragment(fragment);
        }
    }

    final void dispatchActivityCreated() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(4);
    }

    final void dispatchAttach() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(0);
    }

    final void dispatchConfigurationChanged(boolean z) {
        if (z && (this.mHost instanceof OnConfigurationChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchConfigurationChanged() on host. Host implements OnConfigurationChangedProvider and automatically dispatches configuration changes to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performConfigurationChanged();
                if (z) {
                    fragment.mChildFragmentManager.dispatchConfigurationChanged(true);
                }
            }
        }
    }

    final boolean dispatchContextItemSelected() {
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

    final void dispatchCreate() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(1);
    }

    final boolean dispatchCreateOptionsMenu() {
        if (this.mCurState < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment)) {
                if (!fragment.mHidden ? fragment.mChildFragmentManager.dispatchCreateOptionsMenu() | false : false) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(fragment);
                    z = true;
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment fragment2 = this.mCreatedMenus.get(i);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.getClass();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    final void dispatchDestroy() {
        boolean z = true;
        this.mDestroyed = true;
        execPendingActions(true);
        endAnimatingAwayFragments();
        FragmentHostCallback<?> fragmentHostCallback = this.mHost;
        boolean z2 = fragmentHostCallback instanceof ViewModelStoreOwner;
        FragmentStore fragmentStore = this.mFragmentStore;
        if (z2) {
            z = fragmentStore.getNonConfig().isCleared();
        } else if (fragmentHostCallback.getContext() instanceof Activity) {
            z = true ^ ((Activity) this.mHost.getContext()).isChangingConfigurations();
        }
        if (z) {
            Iterator<BackStackState> it = this.mBackStackStates.values().iterator();
            while (it.hasNext()) {
                Iterator<String> it2 = it.next().mFragments.iterator();
                while (it2.hasNext()) {
                    fragmentStore.getNonConfig().clearNonConfigState(it2.next());
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
        if ((obj5 instanceof MenuHost) && this.mParent == null) {
            ((MenuHost) obj5).removeMenuProvider(this.mMenuProvider);
        }
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultLauncher<Intent> activityResultLauncher = this.mStartActivityForResult;
        if (activityResultLauncher != null) {
            activityResultLauncher.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    final void dispatchDestroyView() {
        dispatchStateChange(1);
    }

    final void dispatchLowMemory(boolean z) {
        if (z && (this.mHost instanceof OnTrimMemoryProvider)) {
            throwException(new IllegalStateException("Do not call dispatchLowMemory() on host. Host implements OnTrimMemoryProvider and automatically dispatches low memory callbacks to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.performLowMemory();
                if (z) {
                    fragment.mChildFragmentManager.dispatchLowMemory(true);
                }
            }
        }
    }

    final void dispatchMultiWindowModeChanged(boolean z, boolean z2) {
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

    final void dispatchOnAttachFragment(Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment$1();
        }
    }

    final void dispatchOnHiddenChanged() {
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragments()).iterator();
        while (it.hasNext()) {
            Fragment fragment = (Fragment) it.next();
            if (fragment != null) {
                fragment.isHidden();
                fragment.mChildFragmentManager.dispatchOnHiddenChanged();
            }
        }
    }

    final boolean dispatchOptionsItemSelected() {
        if (this.mCurState < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                if (!fragment.mHidden ? fragment.mChildFragmentManager.dispatchOptionsItemSelected() : false) {
                    return true;
                }
            }
        }
        return false;
    }

    final void dispatchOptionsMenuClosed() {
        if (this.mCurState < 1) {
            return;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && !fragment.mHidden) {
                fragment.mChildFragmentManager.dispatchOptionsMenuClosed();
            }
        }
    }

    final void dispatchPause() {
        dispatchStateChange(5);
    }

    final void dispatchPictureInPictureModeChanged(boolean z, boolean z2) {
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

    final boolean dispatchPrepareOptionsMenu() {
        if (this.mCurState < 1) {
            return false;
        }
        boolean z = false;
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && isParentMenuVisible(fragment)) {
                if (!fragment.mHidden ? fragment.mChildFragmentManager.dispatchPrepareOptionsMenu() | false : false) {
                    z = true;
                }
            }
        }
        return z;
    }

    final void dispatchPrimaryNavigationFragmentChanged() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
    }

    final void dispatchResume() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(7);
    }

    final void dispatchStart() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        dispatchStateChange(5);
    }

    final void dispatchStop() {
        this.mStopped = true;
        this.mNonConfig.setIsStateSaved(true);
        dispatchStateChange(4);
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        String m1m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str, "    ");
        this.mFragmentStore.dump(str, fileDescriptor, printWriter, strArr);
        ArrayList<Fragment> arrayList = this.mCreatedMenus;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i = 0; i < size2; i++) {
                Fragment fragment = this.mCreatedMenus.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment.toString());
            }
        }
        ArrayList<BackStackRecord> arrayList2 = this.mBackStack;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i2 = 0; i2 < size; i2++) {
                BackStackRecord backStackRecord = this.mBackStack.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(m1m, printWriter, true);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int size3 = this.mPendingActions.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i3 = 0; i3 < size3; i3++) {
                    OpGenerator opGenerator = this.mPendingActions.get(i3);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i3);
                    printWriter.print(": ");
                    printWriter.println(opGenerator);
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

    final boolean execPendingActions(boolean z) {
        boolean z2;
        ensureExecReady(z);
        boolean z3 = false;
        while (true) {
            ArrayList<BackStackRecord> arrayList = this.mTmpRecords;
            ArrayList<Boolean> arrayList2 = this.mTmpIsPop;
            synchronized (this.mPendingActions) {
                if (this.mPendingActions.isEmpty()) {
                    z2 = false;
                } else {
                    try {
                        int size = this.mPendingActions.size();
                        z2 = false;
                        for (int i = 0; i < size; i++) {
                            this.mPendingActions.get(i).generateOps(arrayList, arrayList2);
                            z2 |= true;
                        }
                    } finally {
                    }
                }
            }
            if (!z2) {
                break;
            }
            z3 = true;
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
        this.mFragmentStore.burpActive();
        return z3;
    }

    final void execSingleAction(BackStackRecord backStackRecord) {
        if (this.mHost == null || this.mDestroyed) {
            return;
        }
        ensureExecReady(true);
        backStackRecord.generateOps(this.mTmpRecords, this.mTmpIsPop);
        this.mExecutingActions = true;
        try {
            removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            cleanupExec();
            updateOnBackPressedCallbackEnabled();
            if (this.mHavePendingDeferredStart) {
                this.mHavePendingDeferredStart = false;
                startPendingDeferredFragments();
            }
            this.mFragmentStore.burpActive();
        } catch (Throwable th) {
            cleanupExec();
            throw th;
        }
    }

    final Fragment findActiveFragment(String str) {
        return this.mFragmentStore.findActiveFragment(str);
    }

    public final Fragment findFragmentById(int i) {
        return this.mFragmentStore.findFragmentById(i);
    }

    public final Fragment findFragmentByTag(String str) {
        return this.mFragmentStore.findFragmentByTag(str);
    }

    final Fragment findFragmentByWho(String str) {
        return this.mFragmentStore.findFragmentByWho(str);
    }

    final FragmentContainer getContainer() {
        return this.mContainer;
    }

    public final FragmentFactory getFragmentFactory() {
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getFragmentFactory() : this.mHostFragmentFactory;
    }

    public final List<Fragment> getFragments() {
        return this.mFragmentStore.getFragments();
    }

    public final FragmentHostCallback<?> getHost() {
        return this.mHost;
    }

    final LayoutInflater.Factory2 getLayoutInflaterFactory() {
        return this.mLayoutInflaterFactory;
    }

    final FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher() {
        return this.mLifecycleCallbacksDispatcher;
    }

    final Fragment getParent() {
        return this.mParent;
    }

    final SpecialEffectsControllerFactory getSpecialEffectsControllerFactory() {
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getSpecialEffectsControllerFactory() : this.mDefaultSpecialEffectsControllerFactory;
    }

    final ViewModelStore getViewModelStore(Fragment fragment) {
        return this.mNonConfig.getViewModelStore(fragment);
    }

    final void handleOnBackPressed() {
        execPendingActions(true);
        if (this.mOnBackPressedCallback.isEnabled()) {
            popBackStackImmediate();
        } else {
            this.mOnBackPressedDispatcher.onBackPressed();
        }
    }

    final void hideFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (fragment.mHidden) {
            return;
        }
        fragment.mHidden = true;
        fragment.mHiddenChanged = true ^ fragment.mHiddenChanged;
        setVisibleRemovingFragment(fragment);
    }

    final void invalidateMenuForFragment(Fragment fragment) {
        if (fragment.mAdded && isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    public final boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    final void moveToState(int i, boolean z) {
        FragmentHostCallback<?> fragmentHostCallback;
        if (this.mHost == null && i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            this.mFragmentStore.moveToExpectedState();
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && (fragmentHostCallback = this.mHost) != null && this.mCurState == 7) {
                fragmentHostCallback.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    final void noteStateNotSaved() {
        if (this.mHost == null) {
            return;
        }
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.setIsStateSaved(false);
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null) {
                fragment.mChildFragmentManager.noteStateNotSaved();
            }
        }
    }

    final void onContainerAvailable(FragmentContainerView fragmentContainerView) {
        Iterator it = ((ArrayList) this.mFragmentStore.getActiveFragmentStateManagers()).iterator();
        while (it.hasNext()) {
            int i = ((FragmentStateManager) it.next()).getFragment().mContainerId;
            fragmentContainerView.getId();
        }
    }

    public final boolean popBackStackImmediate() {
        boolean z;
        execPendingActions(false);
        ensureExecReady(true);
        Fragment fragment = this.mPrimaryNav;
        if (fragment != null && fragment.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        ArrayList<BackStackRecord> arrayList = this.mTmpRecords;
        ArrayList<Boolean> arrayList2 = this.mTmpIsPop;
        ArrayList<BackStackRecord> arrayList3 = this.mBackStack;
        int i = -1;
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            i = (-1) + this.mBackStack.size();
        }
        if (i < 0) {
            z = false;
        } else {
            for (int size = this.mBackStack.size() - 1; size >= i; size--) {
                arrayList.add(this.mBackStack.remove(size));
                arrayList2.add(Boolean.TRUE);
            }
            z = true;
        }
        if (z) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
        this.mFragmentStore.burpActive();
        return z;
    }

    final void removeFragment(Fragment fragment) {
        if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        boolean z = !fragment.isInBackStack();
        if (!fragment.mDetached || z) {
            this.mFragmentStore.removeFragment(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mRemoving = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    final void restoreSaveStateInternal(Parcelable parcelable) {
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher;
        FragmentStateManager fragmentStateManager;
        Bundle bundle;
        Bundle bundle2;
        Bundle bundle3 = (Bundle) parcelable;
        for (String str : bundle3.keySet()) {
            if (str.startsWith("result_") && (bundle2 = bundle3.getBundle(str)) != null) {
                bundle2.setClassLoader(this.mHost.getContext().getClassLoader());
                this.mResults.put(str.substring(7), bundle2);
            }
        }
        HashMap<String, Bundle> hashMap = new HashMap<>();
        for (String str2 : bundle3.keySet()) {
            if (str2.startsWith("fragment_") && (bundle = bundle3.getBundle(str2)) != null) {
                bundle.setClassLoader(this.mHost.getContext().getClassLoader());
                hashMap.put(str2.substring(9), bundle);
            }
        }
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.restoreSaveState(hashMap);
        FragmentManagerState fragmentManagerState = (FragmentManagerState) bundle3.getParcelable("state");
        if (fragmentManagerState == null) {
            return;
        }
        fragmentStore.resetActiveFragments();
        Iterator<String> it = fragmentManagerState.mActive.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            fragmentLifecycleCallbacksDispatcher = this.mLifecycleCallbacksDispatcher;
            if (!hasNext) {
                break;
            }
            Bundle savedState = fragmentStore.setSavedState(it.next(), null);
            if (savedState != null) {
                Fragment findRetainedFragmentByWho = this.mNonConfig.findRetainedFragmentByWho(((FragmentState) savedState.getParcelable("state")).mWho);
                if (findRetainedFragmentByWho != null) {
                    if (isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + findRetainedFragmentByWho);
                    }
                    fragmentStateManager = new FragmentStateManager(fragmentLifecycleCallbacksDispatcher, fragmentStore, findRetainedFragmentByWho, savedState);
                } else {
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.getContext().getClassLoader(), getFragmentFactory(), savedState);
                }
                Fragment fragment = fragmentStateManager.getFragment();
                fragment.mSavedFragmentState = savedState;
                fragment.mFragmentManager = this;
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "restoreSaveState: active (" + fragment.mWho + "): " + fragment);
                }
                fragmentStateManager.restoreState(this.mHost.getContext().getClassLoader());
                fragmentStore.makeActive(fragmentStateManager);
                fragmentStateManager.setFragmentManagerState(this.mCurState);
            }
        }
        Iterator it2 = ((ArrayList) this.mNonConfig.getRetainedFragments()).iterator();
        while (it2.hasNext()) {
            Fragment fragment2 = (Fragment) it2.next();
            if (!fragmentStore.containsActiveFragment(fragment2.mWho)) {
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Discarding retained Fragment " + fragment2 + " that was not found in the set of active Fragments " + fragmentManagerState.mActive);
                }
                this.mNonConfig.removeRetainedFragment(fragment2);
                fragment2.mFragmentManager = this;
                FragmentStateManager fragmentStateManager2 = new FragmentStateManager(fragmentLifecycleCallbacksDispatcher, fragmentStore, fragment2);
                fragmentStateManager2.setFragmentManagerState(1);
                fragmentStateManager2.moveToExpectedState();
                fragment2.mRemoving = true;
                fragmentStateManager2.moveToExpectedState();
            }
        }
        fragmentStore.restoreAddedFragments(fragmentManagerState.mAdded);
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList<>(fragmentManagerState.mBackStack.length);
            int i = 0;
            while (true) {
                BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.mBackStack;
                if (i >= backStackRecordStateArr.length) {
                    break;
                }
                BackStackRecordState backStackRecordState = backStackRecordStateArr[i];
                backStackRecordState.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(this);
                int i2 = 0;
                int i3 = 0;
                while (i2 < backStackRecordState.mOps.length) {
                    FragmentTransaction.C0138Op c0138Op = new FragmentTransaction.C0138Op();
                    int i4 = i2 + 1;
                    c0138Op.mCmd = backStackRecordState.mOps[i2];
                    if (isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "Instantiate " + backStackRecord + " op #" + i3 + " base fragment #" + backStackRecordState.mOps[i4]);
                    }
                    c0138Op.mOldMaxState = Lifecycle.State.values()[backStackRecordState.mOldMaxLifecycleStates[i3]];
                    c0138Op.mCurrentMaxState = Lifecycle.State.values()[backStackRecordState.mCurrentMaxLifecycleStates[i3]];
                    int[] iArr = backStackRecordState.mOps;
                    int i5 = i4 + 1;
                    c0138Op.mFromExpandedOp = iArr[i4] != 0;
                    int i6 = i5 + 1;
                    int i7 = iArr[i5];
                    c0138Op.mEnterAnim = i7;
                    int i8 = i6 + 1;
                    int i9 = iArr[i6];
                    c0138Op.mExitAnim = i9;
                    int i10 = i8 + 1;
                    int i11 = iArr[i8];
                    c0138Op.mPopEnterAnim = i11;
                    int i12 = iArr[i10];
                    c0138Op.mPopExitAnim = i12;
                    backStackRecord.mEnterAnim = i7;
                    backStackRecord.mExitAnim = i9;
                    backStackRecord.mPopEnterAnim = i11;
                    backStackRecord.mPopExitAnim = i12;
                    backStackRecord.addOp(c0138Op);
                    i3++;
                    i2 = i10 + 1;
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
                for (int i13 = 0; i13 < backStackRecordState.mFragmentWhos.size(); i13++) {
                    String str3 = backStackRecordState.mFragmentWhos.get(i13);
                    if (str3 != null) {
                        backStackRecord.mOps.get(i13).mFragment = findActiveFragment(str3);
                    }
                }
                backStackRecord.bumpBackStackNesting(1);
                if (isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "restoreAllState: back stack #" + i + " (index " + backStackRecord.mIndex + "): " + backStackRecord);
                    PrintWriter printWriter = new PrintWriter(new LogWriter());
                    backStackRecord.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.mBackStack.add(backStackRecord);
                i++;
            }
        } else {
            this.mBackStack = null;
        }
        this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
        String str4 = fragmentManagerState.mPrimaryNavActiveWho;
        if (str4 != null) {
            Fragment findActiveFragment = findActiveFragment(str4);
            this.mPrimaryNav = findActiveFragment;
            dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment);
        }
        ArrayList<String> arrayList = fragmentManagerState.mBackStackStateKeys;
        if (arrayList != null) {
            for (int i14 = 0; i14 < arrayList.size(); i14++) {
                this.mBackStackStates.put(arrayList.get(i14), fragmentManagerState.mBackStackStates.get(i14));
            }
        }
        this.mLaunchedFragments = new ArrayDeque<>(fragmentManagerState.mLaunchedFragments);
    }

    final Bundle saveAllStateInternal() {
        BackStackRecordState[] backStackRecordStateArr;
        int size;
        Bundle bundle = new Bundle();
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forcePostponedExecutePendingOperations();
        }
        endAnimatingAwayFragments();
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.setIsStateSaved(true);
        FragmentStore fragmentStore = this.mFragmentStore;
        ArrayList<String> saveActiveFragments = fragmentStore.saveActiveFragments();
        HashMap<String, Bundle> allSavedState = fragmentStore.getAllSavedState();
        if (!allSavedState.isEmpty()) {
            ArrayList<String> saveAddedFragments = fragmentStore.saveAddedFragments();
            ArrayList<BackStackRecord> arrayList = this.mBackStack;
            if (arrayList == null || (size = arrayList.size()) <= 0) {
                backStackRecordStateArr = null;
            } else {
                backStackRecordStateArr = new BackStackRecordState[size];
                for (int i = 0; i < size; i++) {
                    backStackRecordStateArr[i] = new BackStackRecordState(this.mBackStack.get(i));
                    if (isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "saveAllState: adding back stack #" + i + ": " + this.mBackStack.get(i));
                    }
                }
            }
            FragmentManagerState fragmentManagerState = new FragmentManagerState();
            fragmentManagerState.mActive = saveActiveFragments;
            fragmentManagerState.mAdded = saveAddedFragments;
            fragmentManagerState.mBackStack = backStackRecordStateArr;
            fragmentManagerState.mBackStackIndex = this.mBackStackIndex.get();
            Fragment fragment = this.mPrimaryNav;
            if (fragment != null) {
                fragmentManagerState.mPrimaryNavActiveWho = fragment.mWho;
            }
            ArrayList<String> arrayList2 = fragmentManagerState.mBackStackStateKeys;
            Map<String, BackStackState> map = this.mBackStackStates;
            arrayList2.addAll(map.keySet());
            fragmentManagerState.mBackStackStates.addAll(map.values());
            fragmentManagerState.mLaunchedFragments = new ArrayList<>(this.mLaunchedFragments);
            bundle.putParcelable("state", fragmentManagerState);
            Map<String, Bundle> map2 = this.mResults;
            for (String str : map2.keySet()) {
                bundle.putBundle("result_" + str, map2.get(str));
            }
            for (String str2 : allSavedState.keySet()) {
                bundle.putBundle("fragment_" + str2, allSavedState.get(str2));
            }
        } else if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "saveAllState: no fragments!");
        }
        return bundle;
    }

    final void setExitAnimationOrder(Fragment fragment, boolean z) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || !(fragmentContainer instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) fragmentContainer).setDrawDisappearingViewsLast(!z);
    }

    final void setMaxLifecycle(Fragment fragment, Lifecycle.State state) {
        if (fragment.equals(findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this)) {
            fragment.mMaxState = state;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    final void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment == null || (fragment.equals(findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this))) {
            Fragment fragment2 = this.mPrimaryNav;
            this.mPrimaryNav = fragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragment2);
            dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
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
            FragmentHostCallback<?> fragmentHostCallback = this.mHost;
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
}
