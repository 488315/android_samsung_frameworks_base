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
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.BackEventCompat;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistry$register$3;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerViewModel;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.SpecialEffectsController;
import androidx.fragment.app.strictmode.FragmentStrictMode;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FragmentManager {
    public final ArrayList mBackStackChangeListeners;
    public FragmentContainer mContainer;
    public ArrayList mCreatedMenus;
    public int mCurState;
    public final AnonymousClass4 mDefaultSpecialEffectsControllerFactory;
    public boolean mDestroyed;
    public final AnonymousClass5 mExecCommit;
    public boolean mExecutingActions;
    public FragmentFactory mFragmentFactory;
    public boolean mHavePendingDeferredStart;
    public FragmentHostCallback mHost;
    public final AnonymousClass3 mHostFragmentFactory;
    public ArrayDeque mLaunchedFragments;
    public final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher;
    public final AnonymousClass2 mMenuProvider;
    public boolean mNeedMenuInvalidate;
    public FragmentManagerViewModel mNonConfig;
    public final CopyOnWriteArrayList mOnAttachListeners;
    public OnBackPressedDispatcher mOnBackPressedDispatcher;
    public final FragmentManager$$ExternalSyntheticLambda1 mOnConfigurationChangedListener;
    public final FragmentManager$$ExternalSyntheticLambda1 mOnMultiWindowModeChangedListener;
    public final FragmentManager$$ExternalSyntheticLambda1 mOnPictureInPictureModeChangedListener;
    public final FragmentManager$$ExternalSyntheticLambda1 mOnTrimMemoryListener;
    public Fragment mParent;
    public Fragment mPrimaryNav;
    public ActivityResultRegistry$register$3 mRequestPermissions;
    public ActivityResultRegistry$register$3 mStartActivityForResult;
    public ActivityResultRegistry$register$3 mStartIntentSenderForResult;
    public boolean mStateSaved;
    public boolean mStopped;
    public ArrayList mTmpAddedFragments;
    public ArrayList mTmpIsPop;
    public ArrayList mTmpRecords;
    public final ArrayList mPendingActions = new ArrayList();
    public final FragmentStore mFragmentStore = new FragmentStore();
    public ArrayList mBackStack = new ArrayList();
    public final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    public BackStackRecord mTransitioningOp = null;
    public boolean mHandlingTransitioningOp = false;
    public final AnonymousClass1 mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackCancelled() {
            boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
            final FragmentManager fragmentManager = FragmentManager.this;
            if (isLoggingEnabled) {
                Log.d("FragmentManager", "handleOnBackCancelled. PREDICTIVE_BACK = true fragment manager " + fragmentManager);
            }
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "cancelBackStackTransition for transition " + fragmentManager.mTransitioningOp);
            }
            BackStackRecord backStackRecord = fragmentManager.mTransitioningOp;
            if (backStackRecord != null) {
                backStackRecord.mCommitted = false;
                Runnable runnable = new Runnable() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it = FragmentManager.this.mBackStackChangeListeners.iterator();
                        if (it.hasNext()) {
                            throw FragmentManager$$ExternalSyntheticOutline0.m(it);
                        }
                    }
                };
                if (backStackRecord.mCommitRunnables == null) {
                    backStackRecord.mCommitRunnables = new ArrayList();
                }
                backStackRecord.mCommitRunnables.add(runnable);
                fragmentManager.mTransitioningOp.commitInternal(false, true);
                fragmentManager.mHandlingTransitioningOp = true;
                fragmentManager.execPendingActions(true);
                fragmentManager.forcePostponedTransactions();
                fragmentManager.mHandlingTransitioningOp = false;
                fragmentManager.mTransitioningOp = null;
            }
        }

        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackPressed() {
            boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
            FragmentManager fragmentManager = FragmentManager.this;
            if (isLoggingEnabled) {
                Log.d("FragmentManager", "handleOnBackPressed. PREDICTIVE_BACK = true fragment manager " + fragmentManager);
            }
            fragmentManager.mHandlingTransitioningOp = true;
            fragmentManager.execPendingActions(true);
            int i = 0;
            fragmentManager.mHandlingTransitioningOp = false;
            BackStackRecord backStackRecord = fragmentManager.mTransitioningOp;
            AnonymousClass1 anonymousClass1 = fragmentManager.mOnBackPressedCallback;
            if (backStackRecord == null) {
                if (anonymousClass1.isEnabled) {
                    if (FragmentManager.isLoggingEnabled(3)) {
                        Log.d("FragmentManager", "Calling popBackStackImmediate via onBackPressed callback");
                    }
                    fragmentManager.popBackStackImmediate();
                    return;
                } else {
                    if (FragmentManager.isLoggingEnabled(3)) {
                        Log.d("FragmentManager", "Calling onBackPressed via onBackPressed callback");
                    }
                    fragmentManager.mOnBackPressedDispatcher.onBackPressed();
                    return;
                }
            }
            if (!fragmentManager.mBackStackChangeListeners.isEmpty()) {
                LinkedHashSet linkedHashSet = new LinkedHashSet(FragmentManager.fragmentsFromRecord(fragmentManager.mTransitioningOp));
                ArrayList arrayList = fragmentManager.mBackStackChangeListeners;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    if (obj != null) {
                        throw new ClassCastException();
                    }
                    Iterator it = linkedHashSet.iterator();
                    if (it.hasNext()) {
                        throw null;
                    }
                }
            }
            ArrayList arrayList2 = fragmentManager.mTransitioningOp.mOps;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                Fragment fragment = ((FragmentTransaction.Op) obj2).mFragment;
                if (fragment != null) {
                    fragment.mTransitioning = false;
                }
            }
            Iterator it2 = ((HashSet) fragmentManager.collectChangedControllers(new ArrayList(Collections.singletonList(fragmentManager.mTransitioningOp)), 0, 1)).iterator();
            while (it2.hasNext()) {
                SpecialEffectsController specialEffectsController = (SpecialEffectsController) it2.next();
                if (FragmentManager.isLoggingEnabled(3)) {
                    specialEffectsController.getClass();
                    Log.d("FragmentManager", "SpecialEffectsController: Completing Back ");
                }
                specialEffectsController.processStart(specialEffectsController.runningOperations);
                specialEffectsController.commitEffects$fragment_release(specialEffectsController.runningOperations);
            }
            ArrayList arrayList3 = fragmentManager.mTransitioningOp.mOps;
            int size3 = arrayList3.size();
            while (i < size3) {
                Object obj3 = arrayList3.get(i);
                i++;
                Fragment fragment2 = ((FragmentTransaction.Op) obj3).mFragment;
                if (fragment2 != null && fragment2.mContainer == null) {
                    fragmentManager.createOrGetFragmentStateManager(fragment2).moveToExpectedState();
                }
            }
            fragmentManager.mTransitioningOp = null;
            fragmentManager.updateOnBackPressedCallbackEnabled();
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "Op is being set to null");
                Log.d("FragmentManager", "OnBackPressedCallback enabled=" + anonymousClass1.isEnabled + " for  FragmentManager " + fragmentManager);
            }
        }

        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackProgressed(BackEventCompat backEventCompat) {
            boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(2);
            FragmentManager fragmentManager = FragmentManager.this;
            if (isLoggingEnabled) {
                Objects.toString(fragmentManager);
            }
            if (fragmentManager.mTransitioningOp != null) {
                Iterator it = ((HashSet) fragmentManager.collectChangedControllers(new ArrayList(Collections.singletonList(fragmentManager.mTransitioningOp)), 0, 1)).iterator();
                while (it.hasNext()) {
                    SpecialEffectsController specialEffectsController = (SpecialEffectsController) it.next();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        specialEffectsController.getClass();
                        float f = backEventCompat.progress;
                    }
                    List list = specialEffectsController.runningOperations;
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = (ArrayList) list;
                    int size = arrayList2.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList2.get(i);
                        i++;
                        CollectionsKt__MutableCollectionsKt.addAll(((SpecialEffectsController.Operation) obj).effects, arrayList);
                    }
                    List list2 = CollectionsKt___CollectionsKt.toList(CollectionsKt___CollectionsKt.toSet(arrayList));
                    int size2 = list2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ((SpecialEffectsController.Effect) list2.get(i2)).onProgress(backEventCompat);
                    }
                }
                Iterator it2 = fragmentManager.mBackStackChangeListeners.iterator();
                if (it2.hasNext()) {
                    throw FragmentManager$$ExternalSyntheticOutline0.m(it2);
                }
            }
        }

        @Override // androidx.activity.OnBackPressedCallback
        public final void handleOnBackStarted() {
            boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(3);
            FragmentManager fragmentManager = FragmentManager.this;
            if (isLoggingEnabled) {
                Log.d("FragmentManager", "handleOnBackStarted. PREDICTIVE_BACK = true fragment manager " + fragmentManager);
            }
            fragmentManager.endAnimatingAwayFragments();
            fragmentManager.enqueueAction(fragmentManager.new PrepareBackStackTransitionState(), false);
        }
    };
    public final AtomicInteger mBackStackIndex = new AtomicInteger();
    public final Map mBackStackStates = Collections.synchronizedMap(new HashMap());
    public final Map mResults = Collections.synchronizedMap(new HashMap());

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.fragment.app.FragmentManager$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FragmentIntentSenderContract extends ActivityResultContract {
        @Override // androidx.activity.result.contract.ActivityResultContract
        public final Object parseResult(int i, Intent intent) {
            return new ActivityResult(i, intent);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OpGenerator {
        boolean generateOps(ArrayList arrayList, ArrayList arrayList2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PopBackStackState implements OpGenerator {
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
            if (fragment == null || this.mId >= 0 || this.mName != null || !fragment.getChildFragmentManager().popBackStackImmediate(-1, 0)) {
                return FragmentManager.this.popBackStackState(arrayList, arrayList2, this.mName, this.mId, this.mFlags);
            }
            return false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PrepareBackStackTransitionState implements OpGenerator {
        public PrepareBackStackTransitionState() {
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public final boolean generateOps(ArrayList arrayList, ArrayList arrayList2) {
            ArrayList arrayList3;
            ArrayList arrayList4;
            boolean popBackStackState;
            boolean isLoggingEnabled = FragmentManager.isLoggingEnabled(2);
            FragmentManager fragmentManager = FragmentManager.this;
            if (isLoggingEnabled) {
                Objects.toString(fragmentManager.mPendingActions);
            }
            int i = 0;
            if (fragmentManager.mBackStack.isEmpty()) {
                Log.i("FragmentManager", "Ignoring call to start back stack pop because the back stack is empty.");
                arrayList3 = arrayList;
                arrayList4 = arrayList2;
                popBackStackState = false;
            } else {
                BackStackRecord backStackRecord = (BackStackRecord) AlertController$$ExternalSyntheticOutline0.m(fragmentManager.mBackStack, 1);
                fragmentManager.mTransitioningOp = backStackRecord;
                ArrayList arrayList5 = backStackRecord.mOps;
                int size = arrayList5.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList5.get(i2);
                    i2++;
                    Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                    if (fragment != null) {
                        fragment.mTransitioning = true;
                    }
                }
                arrayList3 = arrayList;
                arrayList4 = arrayList2;
                popBackStackState = fragmentManager.popBackStackState(arrayList3, arrayList4, null, -1, 0);
            }
            if (!fragmentManager.mBackStackChangeListeners.isEmpty() && arrayList3.size() > 0) {
                ((Boolean) arrayList4.get(arrayList3.size() - 1)).getClass();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                int size2 = arrayList3.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList3.get(i3);
                    i3++;
                    linkedHashSet.addAll(FragmentManager.fragmentsFromRecord((BackStackRecord) obj2));
                }
                ArrayList arrayList6 = fragmentManager.mBackStackChangeListeners;
                int size3 = arrayList6.size();
                while (i < size3) {
                    Object obj3 = arrayList6.get(i);
                    i++;
                    if (obj3 != null) {
                        throw new ClassCastException();
                    }
                    Iterator it = linkedHashSet.iterator();
                    if (it.hasNext()) {
                        throw null;
                    }
                }
            }
            return popBackStackState;
        }
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.fragment.app.FragmentManager$3] */
    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.fragment.app.FragmentManager$4] */
    /* JADX WARN: Type inference failed for: r0v8, types: [androidx.fragment.app.FragmentManager$5] */
    /* JADX WARN: Type inference failed for: r1v10, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v12, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v13, types: [androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v0, types: [androidx.fragment.app.FragmentManager$1] */
    public FragmentManager() {
        Collections.synchronizedMap(new HashMap());
        this.mBackStackChangeListeners = new ArrayList();
        this.mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
        this.mOnAttachListeners = new CopyOnWriteArrayList();
        final int i = 0;
        this.mOnConfigurationChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                switch (i) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        FragmentManager fragmentManager = this.f$0;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        FragmentManager fragmentManager2 = this.f$0;
                        if (fragmentManager2.isParentAdded() && num.intValue() == 80) {
                            fragmentManager2.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        FragmentManager fragmentManager3 = this.f$0;
                        if (fragmentManager3.isParentAdded()) {
                            boolean z = multiWindowModeChangedInfo.isInMultiWindowMode;
                            fragmentManager3.dispatchMultiWindowModeChanged(false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        FragmentManager fragmentManager4 = this.f$0;
                        if (fragmentManager4.isParentAdded()) {
                            boolean z2 = pictureInPictureModeChangedInfo.isInPictureInPictureMode;
                            fragmentManager4.dispatchPictureInPictureModeChanged(false);
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mOnTrimMemoryListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                switch (i2) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        FragmentManager fragmentManager = this.f$0;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        FragmentManager fragmentManager2 = this.f$0;
                        if (fragmentManager2.isParentAdded() && num.intValue() == 80) {
                            fragmentManager2.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        FragmentManager fragmentManager3 = this.f$0;
                        if (fragmentManager3.isParentAdded()) {
                            boolean z = multiWindowModeChangedInfo.isInMultiWindowMode;
                            fragmentManager3.dispatchMultiWindowModeChanged(false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        FragmentManager fragmentManager4 = this.f$0;
                        if (fragmentManager4.isParentAdded()) {
                            boolean z2 = pictureInPictureModeChangedInfo.isInPictureInPictureMode;
                            fragmentManager4.dispatchPictureInPictureModeChanged(false);
                            break;
                        }
                        break;
                }
            }
        };
        final int i3 = 2;
        this.mOnMultiWindowModeChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                switch (i3) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        FragmentManager fragmentManager = this.f$0;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        FragmentManager fragmentManager2 = this.f$0;
                        if (fragmentManager2.isParentAdded() && num.intValue() == 80) {
                            fragmentManager2.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        FragmentManager fragmentManager3 = this.f$0;
                        if (fragmentManager3.isParentAdded()) {
                            boolean z = multiWindowModeChangedInfo.isInMultiWindowMode;
                            fragmentManager3.dispatchMultiWindowModeChanged(false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        FragmentManager fragmentManager4 = this.f$0;
                        if (fragmentManager4.isParentAdded()) {
                            boolean z2 = pictureInPictureModeChangedInfo.isInPictureInPictureMode;
                            fragmentManager4.dispatchPictureInPictureModeChanged(false);
                            break;
                        }
                        break;
                }
            }
        };
        final int i4 = 3;
        this.mOnPictureInPictureModeChangedListener = new Consumer(this) { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda1
            public final /* synthetic */ FragmentManager f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                switch (i4) {
                    case 0:
                        Configuration configuration = (Configuration) obj;
                        FragmentManager fragmentManager = this.f$0;
                        if (fragmentManager.isParentAdded()) {
                            fragmentManager.dispatchConfigurationChanged(false, configuration);
                            break;
                        }
                        break;
                    case 1:
                        Integer num = (Integer) obj;
                        FragmentManager fragmentManager2 = this.f$0;
                        if (fragmentManager2.isParentAdded() && num.intValue() == 80) {
                            fragmentManager2.dispatchLowMemory(false);
                            break;
                        }
                        break;
                    case 2:
                        MultiWindowModeChangedInfo multiWindowModeChangedInfo = (MultiWindowModeChangedInfo) obj;
                        FragmentManager fragmentManager3 = this.f$0;
                        if (fragmentManager3.isParentAdded()) {
                            boolean z = multiWindowModeChangedInfo.isInMultiWindowMode;
                            fragmentManager3.dispatchMultiWindowModeChanged(false);
                            break;
                        }
                        break;
                    default:
                        PictureInPictureModeChangedInfo pictureInPictureModeChangedInfo = (PictureInPictureModeChangedInfo) obj;
                        FragmentManager fragmentManager4 = this.f$0;
                        if (fragmentManager4.isParentAdded()) {
                            boolean z2 = pictureInPictureModeChangedInfo.isInPictureInPictureMode;
                            fragmentManager4.dispatchPictureInPictureModeChanged(false);
                            break;
                        }
                        break;
                }
            }
        };
        this.mMenuProvider = new AnonymousClass2();
        this.mCurState = -1;
        this.mFragmentFactory = null;
        this.mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
            @Override // androidx.fragment.app.FragmentFactory
            public final Fragment instantiate(ClassLoader classLoader, String str) {
                FragmentHostCallback fragmentHostCallback = FragmentManager.this.mHost;
                Context context = fragmentHostCallback.context;
                fragmentHostCallback.getClass();
                try {
                    Class[] clsArr = new Class[0];
                    return (Fragment) FragmentFactory.loadFragmentClass(context.getClassLoader(), str).getConstructor(null).newInstance(null);
                } catch (IllegalAccessException e) {
                    throw new Fragment.InstantiationException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e);
                } catch (InstantiationException e2) {
                    throw new Fragment.InstantiationException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": make sure class name exists, is public, and has an empty constructor that is public"), e2);
                } catch (NoSuchMethodException e3) {
                    throw new Fragment.InstantiationException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": could not find Fragment constructor"), e3);
                } catch (InvocationTargetException e4) {
                    throw new Fragment.InstantiationException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unable to instantiate fragment ", str, ": calling Fragment constructor caused an exception"), e4);
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

    public static Set fragmentsFromRecord(BackStackRecord backStackRecord) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < backStackRecord.mOps.size(); i++) {
            Fragment fragment = ((FragmentTransaction.Op) backStackRecord.mOps.get(i)).mFragment;
            if (fragment != null && backStackRecord.mAddToBackStack) {
                hashSet.add(fragment);
            }
        }
        return hashSet;
    }

    public static boolean isLoggingEnabled(int i) {
        return Log.isLoggable("FragmentManager", i);
    }

    public static boolean isMenuAvailable(Fragment fragment) {
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            return true;
        }
        ArrayList arrayList = (ArrayList) fragment.mChildFragmentManager.mFragmentStore.getActiveFragments();
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Fragment fragment2 = (Fragment) obj;
            if (fragment2 != null) {
                z = isMenuAvailable(fragment2);
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public static boolean isParentMenuVisible(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        if (fragment.mMenuVisible) {
            return fragment.mFragmentManager == null || isParentMenuVisible(fragment.mParentFragment);
        }
        return false;
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
        if (fragment != null) {
            this.mOnAttachListeners.add(new FragmentOnAttachListener(this) { // from class: androidx.fragment.app.FragmentManager.7
                @Override // androidx.fragment.app.FragmentOnAttachListener
                public final void onAttachFragment$1() {
                    fragment.getClass();
                }
            });
        } else if (fragmentHostCallback instanceof FragmentOnAttachListener) {
            this.mOnAttachListeners.add((FragmentOnAttachListener) fragmentHostCallback);
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
            FragmentManagerViewModel fragmentManagerViewModel2 = (FragmentManagerViewModel) fragmentManagerViewModel.mChildNonConfigs.get(fragment.mWho);
            if (fragmentManagerViewModel2 == null) {
                fragmentManagerViewModel2 = new FragmentManagerViewModel(fragmentManagerViewModel.mStateAutomaticallySaved);
                fragmentManagerViewModel.mChildNonConfigs.put(fragment.mWho, fragmentManagerViewModel2);
            }
            this.mNonConfig = fragmentManagerViewModel2;
        } else if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            ViewModelStore viewModelStore = ((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore();
            FragmentManagerViewModel.AnonymousClass1 anonymousClass1 = FragmentManagerViewModel.FACTORY;
            this.mNonConfig = (FragmentManagerViewModel) new ViewModelProvider(viewModelStore, FragmentManagerViewModel.FACTORY).get(FragmentManagerViewModel.class);
        } else {
            this.mNonConfig = new FragmentManagerViewModel(false);
        }
        FragmentManagerViewModel fragmentManagerViewModel3 = this.mNonConfig;
        fragmentManagerViewModel3.mIsStateSaved = this.mStateSaved || this.mStopped;
        this.mFragmentStore.mNonConfig = fragmentManagerViewModel3;
        Object obj = this.mHost;
        if ((obj instanceof SavedStateRegistryOwner) && fragment == null) {
            SavedStateRegistry savedStateRegistry = ((SavedStateRegistryOwner) obj).getSavedStateRegistry();
            final FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) this;
            savedStateRegistry.registerSavedStateProvider("android:support:fragments", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda5
                @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
                public final Bundle saveState() {
                    return FragmentManagerImpl.this.saveAllStateInternal();
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
            String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("FragmentManager:", fragment != null ? TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), fragment.mWho, ":") : "");
            this.mStartActivityForResult = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "StartActivityForResult"), new ActivityResultContract() { // from class: androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                public final class Companion {
                    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                        this();
                    }

                    private Companion() {
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
                    LaunchedFragmentInfo launchedFragmentInfo = (LaunchedFragmentInfo) fragmentManager.mLaunchedFragments.pollLast();
                    if (launchedFragmentInfo == null) {
                        Log.w("FragmentManager", "No Activities were started for result for " + this);
                        return;
                    }
                    String str = launchedFragmentInfo.mWho;
                    Fragment findFragmentByWho = fragmentManager.mFragmentStore.findFragmentByWho(str);
                    if (findFragmentByWho == null) {
                        MotionLayout$$ExternalSyntheticOutline0.m("Activity result delivered for unknown Fragment ", str, "FragmentManager");
                        return;
                    }
                    int i = activityResult.resultCode;
                    Intent intent = activityResult.data;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFragmentByWho.toString();
                        Objects.toString(intent);
                    }
                }
            });
            this.mStartIntentSenderForResult = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "StartIntentSenderForResult"), new FragmentIntentSenderContract(), new ActivityResultCallback() { // from class: androidx.fragment.app.FragmentManager.9
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
                        MotionLayout$$ExternalSyntheticOutline0.m("Intent Sender result delivered for unknown Fragment ", str, "FragmentManager");
                        return;
                    }
                    int i = activityResult.resultCode;
                    Intent intent = activityResult.data;
                    if (FragmentManager.isLoggingEnabled(2)) {
                        findFragmentByWho.toString();
                        Objects.toString(intent);
                    }
                }
            });
            this.mRequestPermissions = activityResultRegistry.register(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "RequestPermissions"), new ActivityResultContract() { // from class: androidx.activity.result.contract.ActivityResultContracts$RequestMultiplePermissions

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                public final class Companion {
                    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                        this();
                    }

                    private Companion() {
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
                            MotionLayout$$ExternalSyntheticOutline0.m("Permission request result delivered for unknown Fragment ", str, "FragmentManager");
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
        Object defaultSpecialEffectsController;
        HashSet hashSet = new HashSet();
        ArrayList arrayList = (ArrayList) this.mFragmentStore.getActiveFragmentStateManagers();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ViewGroup viewGroup = ((FragmentStateManager) obj).mFragment.mContainer;
            if (viewGroup != null) {
                AnonymousClass4 specialEffectsControllerFactory = getSpecialEffectsControllerFactory();
                SpecialEffectsController.Companion.getClass();
                Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
                if (tag instanceof SpecialEffectsController) {
                    defaultSpecialEffectsController = (SpecialEffectsController) tag;
                } else {
                    specialEffectsControllerFactory.getClass();
                    defaultSpecialEffectsController = new DefaultSpecialEffectsController(viewGroup);
                    viewGroup.setTag(R.id.special_effects_controller_view_tag, defaultSpecialEffectsController);
                }
                hashSet.add(defaultSpecialEffectsController);
            }
        }
        return hashSet;
    }

    public final Set collectChangedControllers(ArrayList arrayList, int i, int i2) {
        ViewGroup viewGroup;
        HashSet hashSet = new HashSet();
        while (i < i2) {
            ArrayList arrayList2 = ((BackStackRecord) arrayList.get(i)).mOps;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                if (fragment != null && (viewGroup = fragment.mContainer) != null) {
                    hashSet.add(SpecialEffectsController.getOrCreateController(viewGroup, this));
                }
            }
            i++;
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
        fragmentStateManager2.restoreState(this.mHost.context.getClassLoader());
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
        if (this.mCurState >= 1) {
            for (Fragment fragment : this.mFragmentStore.getFragments()) {
                if (fragment != null) {
                    if (!fragment.mHidden ? fragment.mChildFragmentManager.dispatchContextItemSelected() : false) {
                        return true;
                    }
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
        endAnimatingAwayFragments();
        FragmentHostCallback fragmentHostCallback = this.mHost;
        boolean z2 = fragmentHostCallback instanceof ViewModelStoreOwner;
        FragmentStore fragmentStore = this.mFragmentStore;
        if (z2) {
            z = fragmentStore.mNonConfig.mHasBeenCleared;
        } else {
            Context context = fragmentHostCallback.context;
            if (context instanceof Activity) {
                z = true ^ ((Activity) context).isChangingConfigurations();
            }
        }
        if (z) {
            Iterator it = this.mBackStackStates.values().iterator();
            while (it.hasNext()) {
                Iterator it2 = ((BackStackState) it.next()).mFragments.iterator();
                while (it2.hasNext()) {
                    fragmentStore.mNonConfig.clearNonConfigState((String) it2.next(), false);
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
            remove();
            this.mOnBackPressedDispatcher = null;
        }
        ActivityResultRegistry$register$3 activityResultRegistry$register$3 = this.mStartActivityForResult;
        if (activityResultRegistry$register$3 != null) {
            activityResultRegistry$register$3.unregister();
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
                fragment.mCalled = true;
                if (z) {
                    fragment.mChildFragmentManager.dispatchLowMemory(true);
                }
            }
        }
    }

    public final void dispatchMultiWindowModeChanged(boolean z) {
        if (z && (this.mHost instanceof OnMultiWindowModeChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchMultiWindowModeChanged() on host. Host implements OnMultiWindowModeChangedProvider and automatically dispatches multi-window mode changes to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && z) {
                fragment.mChildFragmentManager.dispatchMultiWindowModeChanged(true);
            }
        }
    }

    public final void dispatchOnHiddenChanged() {
        ArrayList arrayList = (ArrayList) this.mFragmentStore.getActiveFragments();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Fragment fragment = (Fragment) obj;
            if (fragment != null) {
                fragment.isHidden();
                fragment.mChildFragmentManager.dispatchOnHiddenChanged();
            }
        }
    }

    public final boolean dispatchOptionsItemSelected(MenuItem menuItem) {
        if (this.mCurState >= 1) {
            for (Fragment fragment : this.mFragmentStore.getFragments()) {
                if (fragment != null) {
                    if (!fragment.mHidden ? (fragment.mHasMenu && fragment.mMenuVisible && fragment.onOptionsItemSelected(menuItem)) ? true : fragment.mChildFragmentManager.dispatchOptionsItemSelected(menuItem) : false) {
                        return true;
                    }
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
        if (fragment != null) {
            if (fragment.equals(this.mFragmentStore.findActiveFragment(fragment.mWho))) {
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
        }
    }

    public final void dispatchPictureInPictureModeChanged(boolean z) {
        if (z && (this.mHost instanceof OnPictureInPictureModeChangedProvider)) {
            throwException(new IllegalStateException("Do not call dispatchPictureInPictureModeChanged() on host. Host implements OnPictureInPictureModeChangedProvider and automatically dispatches picture-in-picture mode changes to fragments."));
            throw null;
        }
        for (Fragment fragment : this.mFragmentStore.getFragments()) {
            if (fragment != null && z) {
                fragment.mChildFragmentManager.dispatchPictureInPictureModeChanged(true);
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

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "    ");
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.getClass();
        String str2 = str + "    ";
        if (!fragmentStore.mActive.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : fragmentStore.mActive.values()) {
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
                        new LoaderManagerImpl(fragment, fragment.getViewModelStore()).dump(printWriter, str2);
                    }
                    printWriter.print(str2);
                    printWriter.println("Child " + fragment.mChildFragmentManager + ":");
                    fragment.mChildFragmentManager.dump(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "  "), fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        int size2 = fragmentStore.mAdded.size();
        if (size2 > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size2; i++) {
                Fragment fragment2 = (Fragment) fragmentStore.mAdded.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
        ArrayList arrayList = this.mCreatedMenus;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i2 = 0; i2 < size; i2++) {
                Fragment fragment3 = (Fragment) this.mCreatedMenus.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment3.toString());
            }
        }
        int size3 = this.mBackStack.size();
        if (size3 > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i3 = 0; i3 < size3; i3++) {
                BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(printWriter, m, true);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            try {
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
            } catch (Throwable th) {
                throw th;
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

    public final void endAnimatingAwayFragments() {
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            ((SpecialEffectsController) it.next()).forceCompleteAllOperations();
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
            if (this.mStateSaved || this.mStopped) {
                throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
            }
        }
        synchronized (this.mPendingActions) {
            try {
                if (this.mHost == null) {
                    if (!z) {
                        throw new IllegalStateException("Activity has been destroyed");
                    }
                } else {
                    this.mPendingActions.add(opGenerator);
                    scheduleCommit();
                }
            } catch (Throwable th) {
                throw th;
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
        if (Looper.myLooper() != this.mHost.handler.getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z && (this.mStateSaved || this.mStopped)) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList();
            this.mTmpIsPop = new ArrayList();
        }
    }

    public final boolean execPendingActions(boolean z) {
        boolean z2;
        BackStackRecord backStackRecord;
        ensureExecReady(z);
        if (!this.mHandlingTransitioningOp && (backStackRecord = this.mTransitioningOp) != null) {
            backStackRecord.mCommitted = false;
            if (isLoggingEnabled(3)) {
                Log.d("FragmentManager", "Reversing mTransitioningOp " + this.mTransitioningOp + " as part of execPendingActions for actions " + this.mPendingActions);
            }
            this.mTransitioningOp.commitInternal(false, false);
            this.mPendingActions.add(0, this.mTransitioningOp);
            ArrayList arrayList = this.mTransitioningOp.mOps;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Fragment fragment = ((FragmentTransaction.Op) obj).mFragment;
                if (fragment != null) {
                    fragment.mTransitioning = false;
                }
            }
            this.mTransitioningOp = null;
        }
        boolean z3 = false;
        while (true) {
            ArrayList arrayList2 = this.mTmpRecords;
            ArrayList arrayList3 = this.mTmpIsPop;
            synchronized (this.mPendingActions) {
                if (this.mPendingActions.isEmpty()) {
                    z2 = false;
                } else {
                    try {
                        int size2 = this.mPendingActions.size();
                        z2 = false;
                        for (int i2 = 0; i2 < size2; i2++) {
                            z2 |= ((OpGenerator) this.mPendingActions.get(i2)).generateOps(arrayList2, arrayList3);
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
        this.mFragmentStore.mActive.values().removeAll(Collections.singleton(null));
        return z3;
    }

    public final void executeOpsTogether(ArrayList arrayList, ArrayList arrayList2, int i, int i2) {
        Object obj;
        boolean z;
        int i3;
        boolean z2;
        int i4;
        int i5;
        boolean z3;
        boolean z4;
        int i6;
        boolean z5;
        boolean z6;
        int i7 = i;
        int i8 = 1;
        boolean z7 = ((BackStackRecord) arrayList.get(i7)).mReorderingAllowed;
        ArrayList arrayList3 = this.mTmpAddedFragments;
        if (arrayList3 == null) {
            this.mTmpAddedFragments = new ArrayList();
        } else {
            arrayList3.clear();
        }
        ArrayList arrayList4 = this.mTmpAddedFragments;
        FragmentStore fragmentStore = this.mFragmentStore;
        arrayList4.addAll(fragmentStore.getFragments());
        Fragment fragment = this.mPrimaryNav;
        int i9 = i7;
        boolean z8 = false;
        while (i9 < i2) {
            BackStackRecord backStackRecord = (BackStackRecord) arrayList.get(i9);
            if (((Boolean) arrayList2.get(i9)).booleanValue()) {
                z = z7;
                i3 = i9;
                z2 = z8;
                int i10 = i8;
                ArrayList arrayList5 = this.mTmpAddedFragments;
                int size = backStackRecord.mOps.size() - i10;
                while (size >= 0) {
                    FragmentTransaction.Op op = (FragmentTransaction.Op) backStackRecord.mOps.get(size);
                    int i11 = op.mCmd;
                    if (i11 != i10) {
                        if (i11 != 3) {
                            switch (i11) {
                                case 8:
                                    fragment = null;
                                    break;
                                case 9:
                                    fragment = op.mFragment;
                                    break;
                                case 10:
                                    op.mCurrentMaxState = op.mOldMaxState;
                                    break;
                            }
                            size--;
                            i10 = 1;
                        }
                        arrayList5.add(op.mFragment);
                        size--;
                        i10 = 1;
                    }
                    arrayList5.remove(op.mFragment);
                    size--;
                    i10 = 1;
                }
            } else {
                ArrayList arrayList6 = this.mTmpAddedFragments;
                int i12 = 0;
                while (i12 < backStackRecord.mOps.size()) {
                    FragmentTransaction.Op op2 = (FragmentTransaction.Op) backStackRecord.mOps.get(i12);
                    int i13 = op2.mCmd;
                    if (i13 != i8) {
                        if (i13 == 2) {
                            z3 = z7;
                            i4 = i9;
                            Fragment fragment2 = op2.mFragment;
                            int i14 = fragment2.mContainerId;
                            int size2 = arrayList6.size() - 1;
                            boolean z9 = false;
                            while (size2 >= 0) {
                                int i15 = size2;
                                Fragment fragment3 = (Fragment) arrayList6.get(size2);
                                boolean z10 = z8;
                                if (fragment3.mContainerId != i14) {
                                    i6 = i14;
                                } else if (fragment3 == fragment2) {
                                    i6 = i14;
                                    z9 = true;
                                } else {
                                    if (fragment3 == fragment) {
                                        i6 = i14;
                                        z5 = z9;
                                        z6 = true;
                                        backStackRecord.mOps.add(i12, new FragmentTransaction.Op(9, fragment3, true));
                                        i12++;
                                        fragment = null;
                                    } else {
                                        i6 = i14;
                                        z5 = z9;
                                        z6 = true;
                                    }
                                    FragmentTransaction.Op op3 = new FragmentTransaction.Op(3, fragment3, z6);
                                    op3.mEnterAnim = op2.mEnterAnim;
                                    op3.mPopEnterAnim = op2.mPopEnterAnim;
                                    op3.mExitAnim = op2.mExitAnim;
                                    op3.mPopExitAnim = op2.mPopExitAnim;
                                    backStackRecord.mOps.add(i12, op3);
                                    arrayList6.remove(fragment3);
                                    i12++;
                                    z9 = z5;
                                }
                                size2 = i15 - 1;
                                z8 = z10;
                                i14 = i6;
                            }
                            z4 = z8;
                            i5 = 1;
                            if (z9) {
                                backStackRecord.mOps.remove(i12);
                                i12--;
                            } else {
                                op2.mCmd = 1;
                                op2.mFromExpandedOp = true;
                                arrayList6.add(fragment2);
                            }
                        } else if (i13 == 3 || i13 == 6) {
                            z3 = z7;
                            i4 = i9;
                            arrayList6.remove(op2.mFragment);
                            Fragment fragment4 = op2.mFragment;
                            if (fragment4 == fragment) {
                                backStackRecord.mOps.add(i12, new FragmentTransaction.Op(9, fragment4));
                                i12++;
                                z4 = z8;
                                i5 = 1;
                                fragment = null;
                            } else {
                                z4 = z8;
                                i5 = 1;
                            }
                        } else if (i13 != 7) {
                            if (i13 != 8) {
                                z3 = z7;
                                i4 = i9;
                            } else {
                                z3 = z7;
                                i4 = i9;
                                backStackRecord.mOps.add(i12, new FragmentTransaction.Op(9, fragment, true));
                                op2.mFromExpandedOp = true;
                                i12++;
                                fragment = op2.mFragment;
                            }
                            z4 = z8;
                            i5 = 1;
                        } else {
                            i4 = i9;
                            i5 = 1;
                        }
                        i12 += i5;
                        i8 = i5;
                        z7 = z3;
                        i9 = i4;
                        z8 = z4;
                    } else {
                        i4 = i9;
                        i5 = i8;
                    }
                    z3 = z7;
                    z4 = z8;
                    arrayList6.add(op2.mFragment);
                    i12 += i5;
                    i8 = i5;
                    z7 = z3;
                    i9 = i4;
                    z8 = z4;
                }
                z = z7;
                i3 = i9;
                z2 = z8;
            }
            z8 = z2 || backStackRecord.mAddToBackStack;
            i8 = 1;
            z7 = z;
            i9 = i3 + 1;
        }
        int i16 = i8;
        boolean z11 = z7;
        boolean z12 = z8;
        int i17 = -1;
        this.mTmpAddedFragments.clear();
        if (!z11 && this.mCurState >= i16) {
            int i18 = i7;
            while (i18 < i2) {
                ArrayList arrayList7 = ((BackStackRecord) arrayList.get(i18)).mOps;
                int size3 = arrayList7.size();
                int i19 = 0;
                while (i19 < size3) {
                    Object obj2 = arrayList7.get(i19);
                    i19 += i16;
                    Fragment fragment5 = ((FragmentTransaction.Op) obj2).mFragment;
                    if (fragment5 != null && fragment5.mFragmentManager != null) {
                        fragmentStore.makeActive(createOrGetFragmentStateManager(fragment5));
                    }
                    i16 = 1;
                }
                i18++;
                i16 = 1;
            }
        }
        int i20 = i7;
        while (i20 < i2) {
            BackStackRecord backStackRecord2 = (BackStackRecord) arrayList.get(i20);
            if (((Boolean) arrayList2.get(i20)).booleanValue()) {
                backStackRecord2.bumpBackStackNesting(i17);
                for (int size4 = backStackRecord2.mOps.size() - 1; size4 >= 0; size4--) {
                    FragmentTransaction.Op op4 = (FragmentTransaction.Op) backStackRecord2.mOps.get(size4);
                    Fragment fragment6 = op4.mFragment;
                    if (fragment6 != null) {
                        if (fragment6.mAnimationInfo != null) {
                            fragment6.ensureAnimationInfo().mIsPop = true;
                        }
                        int i21 = backStackRecord2.mTransition;
                        int i22 = 8194;
                        int i23 = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND;
                        if (i21 != 4097) {
                            if (i21 != 8194) {
                                i22 = PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_WRONG_STATE;
                                i23 = 8197;
                                if (i21 != 8197) {
                                    if (i21 == 4099) {
                                        i22 = 4099;
                                    } else if (i21 != 4100) {
                                        i22 = 0;
                                    }
                                }
                            }
                            i22 = i23;
                        }
                        if (fragment6.mAnimationInfo != null || i22 != 0) {
                            fragment6.ensureAnimationInfo();
                            fragment6.mAnimationInfo.mNextTransition = i22;
                        }
                        ArrayList arrayList8 = backStackRecord2.mSharedElementTargetNames;
                        ArrayList arrayList9 = backStackRecord2.mSharedElementSourceNames;
                        fragment6.ensureAnimationInfo();
                        Fragment.AnimationInfo animationInfo = fragment6.mAnimationInfo;
                        animationInfo.mSharedElementSourceNames = arrayList8;
                        animationInfo.mSharedElementTargetNames = arrayList9;
                    }
                    int i24 = op4.mCmd;
                    FragmentManager fragmentManager = backStackRecord2.mManager;
                    switch (i24) {
                        case 1:
                            fragment6.setAnimations(op4.mEnterAnim, op4.mExitAnim, op4.mPopEnterAnim, op4.mPopExitAnim);
                            fragmentManager.setExitAnimationOrder(fragment6, true);
                            fragmentManager.removeFragment(fragment6);
                        case 2:
                        default:
                            throw new IllegalArgumentException("Unknown cmd: " + op4.mCmd);
                        case 3:
                            fragment6.setAnimations(op4.mEnterAnim, op4.mExitAnim, op4.mPopEnterAnim, op4.mPopExitAnim);
                            fragmentManager.addFragment(fragment6);
                        case 4:
                            fragment6.setAnimations(op4.mEnterAnim, op4.mExitAnim, op4.mPopEnterAnim, op4.mPopExitAnim);
                            fragmentManager.getClass();
                            if (isLoggingEnabled(2)) {
                                Objects.toString(fragment6);
                            }
                            if (fragment6.mHidden) {
                                fragment6.mHidden = false;
                                fragment6.mHiddenChanged = !fragment6.mHiddenChanged;
                            }
                        case 5:
                            fragment6.setAnimations(op4.mEnterAnim, op4.mExitAnim, op4.mPopEnterAnim, op4.mPopExitAnim);
                            fragmentManager.setExitAnimationOrder(fragment6, true);
                            if (isLoggingEnabled(2)) {
                                Objects.toString(fragment6);
                            }
                            if (!fragment6.mHidden) {
                                fragment6.mHidden = true;
                                fragment6.mHiddenChanged = !fragment6.mHiddenChanged;
                                fragmentManager.setVisibleRemovingFragment(fragment6);
                            }
                        case 6:
                            fragment6.setAnimations(op4.mEnterAnim, op4.mExitAnim, op4.mPopEnterAnim, op4.mPopExitAnim);
                            fragmentManager.attachFragment(fragment6);
                        case 7:
                            fragment6.setAnimations(op4.mEnterAnim, op4.mExitAnim, op4.mPopEnterAnim, op4.mPopExitAnim);
                            fragmentManager.setExitAnimationOrder(fragment6, true);
                            fragmentManager.detachFragment(fragment6);
                        case 8:
                            fragmentManager.setPrimaryNavigationFragment(null);
                        case 9:
                            fragmentManager.setPrimaryNavigationFragment(fragment6);
                        case 10:
                            fragmentManager.setMaxLifecycle(fragment6, op4.mOldMaxState);
                    }
                }
            } else {
                backStackRecord2.bumpBackStackNesting(1);
                int size5 = backStackRecord2.mOps.size();
                for (int i25 = 0; i25 < size5; i25++) {
                    FragmentTransaction.Op op5 = (FragmentTransaction.Op) backStackRecord2.mOps.get(i25);
                    Fragment fragment7 = op5.mFragment;
                    if (fragment7 != null) {
                        if (fragment7.mAnimationInfo != null) {
                            fragment7.ensureAnimationInfo().mIsPop = false;
                        }
                        int i26 = backStackRecord2.mTransition;
                        if (fragment7.mAnimationInfo != null || i26 != 0) {
                            fragment7.ensureAnimationInfo();
                            fragment7.mAnimationInfo.mNextTransition = i26;
                        }
                        ArrayList arrayList10 = backStackRecord2.mSharedElementSourceNames;
                        ArrayList arrayList11 = backStackRecord2.mSharedElementTargetNames;
                        fragment7.ensureAnimationInfo();
                        Fragment.AnimationInfo animationInfo2 = fragment7.mAnimationInfo;
                        animationInfo2.mSharedElementSourceNames = arrayList10;
                        animationInfo2.mSharedElementTargetNames = arrayList11;
                    }
                    int i27 = op5.mCmd;
                    FragmentManager fragmentManager2 = backStackRecord2.mManager;
                    switch (i27) {
                        case 1:
                            fragment7.setAnimations(op5.mEnterAnim, op5.mExitAnim, op5.mPopEnterAnim, op5.mPopExitAnim);
                            fragmentManager2.setExitAnimationOrder(fragment7, false);
                            fragmentManager2.addFragment(fragment7);
                        case 2:
                        default:
                            throw new IllegalArgumentException("Unknown cmd: " + op5.mCmd);
                        case 3:
                            fragment7.setAnimations(op5.mEnterAnim, op5.mExitAnim, op5.mPopEnterAnim, op5.mPopExitAnim);
                            fragmentManager2.removeFragment(fragment7);
                        case 4:
                            fragment7.setAnimations(op5.mEnterAnim, op5.mExitAnim, op5.mPopEnterAnim, op5.mPopExitAnim);
                            fragmentManager2.getClass();
                            if (isLoggingEnabled(2)) {
                                Objects.toString(fragment7);
                            }
                            if (!fragment7.mHidden) {
                                fragment7.mHidden = true;
                                fragment7.mHiddenChanged = !fragment7.mHiddenChanged;
                                fragmentManager2.setVisibleRemovingFragment(fragment7);
                            }
                        case 5:
                            fragment7.setAnimations(op5.mEnterAnim, op5.mExitAnim, op5.mPopEnterAnim, op5.mPopExitAnim);
                            fragmentManager2.setExitAnimationOrder(fragment7, false);
                            if (isLoggingEnabled(2)) {
                                Objects.toString(fragment7);
                            }
                            if (fragment7.mHidden) {
                                fragment7.mHidden = false;
                                fragment7.mHiddenChanged = !fragment7.mHiddenChanged;
                            }
                        case 6:
                            fragment7.setAnimations(op5.mEnterAnim, op5.mExitAnim, op5.mPopEnterAnim, op5.mPopExitAnim);
                            fragmentManager2.detachFragment(fragment7);
                        case 7:
                            fragment7.setAnimations(op5.mEnterAnim, op5.mExitAnim, op5.mPopEnterAnim, op5.mPopExitAnim);
                            fragmentManager2.setExitAnimationOrder(fragment7, false);
                            fragmentManager2.attachFragment(fragment7);
                        case 8:
                            fragmentManager2.setPrimaryNavigationFragment(fragment7);
                        case 9:
                            fragmentManager2.setPrimaryNavigationFragment(null);
                        case 10:
                            fragmentManager2.setMaxLifecycle(fragment7, op5.mCurrentMaxState);
                    }
                }
            }
            i20++;
            i17 = -1;
        }
        boolean booleanValue = ((Boolean) arrayList2.get(i2 - 1)).booleanValue();
        if (z12 && !this.mBackStackChangeListeners.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            int size6 = arrayList.size();
            int i28 = 0;
            while (i28 < size6) {
                Object obj3 = arrayList.get(i28);
                i28++;
                linkedHashSet.addAll(fragmentsFromRecord((BackStackRecord) obj3));
            }
            if (this.mTransitioningOp == null) {
                ArrayList arrayList12 = this.mBackStackChangeListeners;
                int size7 = arrayList12.size();
                int i29 = 0;
                while (i29 < size7) {
                    Object obj4 = arrayList12.get(i29);
                    i29++;
                    if (obj4 != null) {
                        throw new ClassCastException();
                    }
                    Iterator it = linkedHashSet.iterator();
                    if (it.hasNext()) {
                        throw null;
                    }
                }
                ArrayList arrayList13 = this.mBackStackChangeListeners;
                int size8 = arrayList13.size();
                int i30 = 0;
                while (i30 < size8) {
                    Object obj5 = arrayList13.get(i30);
                    i30++;
                    if (obj5 != null) {
                        throw new ClassCastException();
                    }
                    Iterator it2 = linkedHashSet.iterator();
                    if (it2.hasNext()) {
                        throw null;
                    }
                }
            }
        }
        for (int i31 = i7; i31 < i2; i31++) {
            BackStackRecord backStackRecord3 = (BackStackRecord) arrayList.get(i31);
            if (booleanValue) {
                for (int size9 = backStackRecord3.mOps.size() - 1; size9 >= 0; size9--) {
                    Fragment fragment8 = ((FragmentTransaction.Op) backStackRecord3.mOps.get(size9)).mFragment;
                    if (fragment8 != null) {
                        createOrGetFragmentStateManager(fragment8).moveToExpectedState();
                    }
                }
            } else {
                ArrayList arrayList14 = backStackRecord3.mOps;
                int size10 = arrayList14.size();
                int i32 = 0;
                while (i32 < size10) {
                    Object obj6 = arrayList14.get(i32);
                    i32++;
                    Fragment fragment9 = ((FragmentTransaction.Op) obj6).mFragment;
                    if (fragment9 != null) {
                        createOrGetFragmentStateManager(fragment9).moveToExpectedState();
                    }
                }
            }
        }
        moveToState(this.mCurState, true);
        Iterator it3 = ((HashSet) collectChangedControllers(arrayList, i7, i2)).iterator();
        while (it3.hasNext()) {
            SpecialEffectsController specialEffectsController = (SpecialEffectsController) it3.next();
            specialEffectsController.operationDirectionIsPop = booleanValue;
            synchronized (specialEffectsController.pendingOperations) {
                try {
                    specialEffectsController.updateFinalState();
                    ArrayList arrayList15 = (ArrayList) specialEffectsController.pendingOperations;
                    ListIterator listIterator = arrayList15.listIterator(arrayList15.size());
                    while (true) {
                        if (listIterator.hasPrevious()) {
                            Object previous = listIterator.previous();
                            SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) previous;
                            SpecialEffectsController.Operation.State.Companion companion = SpecialEffectsController.Operation.State.Companion;
                            View view = operation.fragment.mView;
                            companion.getClass();
                            SpecialEffectsController.Operation.State asOperationState = SpecialEffectsController.Operation.State.Companion.asOperationState(view);
                            SpecialEffectsController.Operation.State state = operation.finalState;
                            SpecialEffectsController.Operation.State state2 = SpecialEffectsController.Operation.State.VISIBLE;
                            if (state == state2 && asOperationState != state2) {
                                obj = previous;
                            }
                        } else {
                            obj = null;
                        }
                    }
                    specialEffectsController.isContainerPostponed = false;
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            specialEffectsController.executePendingOperations();
        }
        while (i7 < i2) {
            BackStackRecord backStackRecord4 = (BackStackRecord) arrayList.get(i7);
            if (((Boolean) arrayList2.get(i7)).booleanValue() && backStackRecord4.mIndex >= 0) {
                backStackRecord4.mIndex = -1;
            }
            if (backStackRecord4.mCommitRunnables != null) {
                for (int i33 = 0; i33 < backStackRecord4.mCommitRunnables.size(); i33++) {
                    ((Runnable) backStackRecord4.mCommitRunnables.get(i33)).run();
                }
                backStackRecord4.mCommitRunnables = null;
            }
            i7++;
        }
        if (!z12 || this.mBackStackChangeListeners.size() <= 0) {
            return;
        }
        this.mBackStackChangeListeners.get(0).getClass();
        throw new ClassCastException();
    }

    public final Fragment findFragmentById(int i) {
        FragmentStore fragmentStore = this.mFragmentStore;
        for (int size = fragmentStore.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) fragmentStore.mAdded.get(size);
            if (fragment != null && fragment.mFragmentId == i) {
                return fragment;
            }
        }
        for (FragmentStateManager fragmentStateManager : fragmentStore.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.mFragment;
                if (fragment2.mFragmentId == i) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public final Fragment findFragmentByTag(String str) {
        FragmentStore fragmentStore = this.mFragmentStore;
        for (int size = fragmentStore.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = (Fragment) fragmentStore.mAdded.get(size);
            if (fragment != null && str.equals(fragment.mTag)) {
                return fragment;
            }
        }
        for (FragmentStateManager fragmentStateManager : fragmentStore.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.mFragment;
                if (str.equals(fragment2.mTag)) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public final void forcePostponedTransactions() {
        Iterator it = ((HashSet) collectAllSpecialEffectsController()).iterator();
        while (it.hasNext()) {
            SpecialEffectsController specialEffectsController = (SpecialEffectsController) it.next();
            if (specialEffectsController.isContainerPostponed) {
                specialEffectsController.isContainerPostponed = false;
                specialEffectsController.executePendingOperations();
            }
        }
    }

    public final ViewGroup getFragmentContainer(Fragment fragment) {
        ViewGroup viewGroup = fragment.mContainer;
        if (viewGroup != null) {
            return viewGroup;
        }
        if (fragment.mContainerId <= 0 || !this.mContainer.onHasView()) {
            return null;
        }
        View onFindViewById = this.mContainer.onFindViewById(fragment.mContainerId);
        if (onFindViewById instanceof ViewGroup) {
            return (ViewGroup) onFindViewById;
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

    public final AnonymousClass4 getSpecialEffectsControllerFactory() {
        Fragment fragment = this.mParent;
        return fragment != null ? fragment.mFragmentManager.getSpecialEffectsControllerFactory() : this.mDefaultSpecialEffectsControllerFactory;
    }

    public final boolean isParentAdded() {
        Fragment fragment = this.mParent;
        if (fragment == null) {
            return true;
        }
        return fragment.mHost != null && fragment.mAdded && fragment.getParentFragmentManager().isParentAdded();
    }

    public final void moveToState(int i, boolean z) {
        FragmentHostCallback fragmentHostCallback;
        if (this.mHost == null && i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.mCurState) {
            this.mCurState = i;
            FragmentStore fragmentStore = this.mFragmentStore;
            ArrayList arrayList = fragmentStore.mAdded;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                FragmentStateManager fragmentStateManager = (FragmentStateManager) fragmentStore.mActive.get(((Fragment) obj).mWho);
                if (fragmentStateManager != null) {
                    fragmentStateManager.moveToExpectedState();
                }
            }
            int size2 = fragmentStore.mActive.size();
            for (FragmentStateManager fragmentStateManager2 : fragmentStore.mActive.values()) {
                if (fragmentStateManager2 != null) {
                    fragmentStateManager2.moveToExpectedState();
                    Fragment fragment = fragmentStateManager2.mFragment;
                    if (fragment.mRemoving && !fragment.isInBackStack()) {
                        fragmentStore.makeInactive(fragmentStateManager2);
                    }
                }
                if (size2 != fragmentStore.mActive.size()) {
                    Log.d("FragmentManager", fragmentStore + "[enhanced for loop] expected Active size is " + size2 + ", but " + fragmentStore.mActive.size());
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
        int i3 = -1;
        if (!this.mBackStack.isEmpty()) {
            if (str != null || i >= 0) {
                int size = this.mBackStack.size() - 1;
                while (size >= 0) {
                    BackStackRecord backStackRecord = (BackStackRecord) this.mBackStack.get(size);
                    if ((str != null && str.equals(backStackRecord.mName)) || (i >= 0 && i == backStackRecord.mIndex)) {
                        break;
                    }
                    size--;
                }
                if (size < 0) {
                    i3 = size;
                } else if (z) {
                    i3 = size;
                    while (i3 > 0) {
                        BackStackRecord backStackRecord2 = (BackStackRecord) this.mBackStack.get(i3 - 1);
                        if ((str == null || !str.equals(backStackRecord2.mName)) && (i < 0 || i != backStackRecord2.mIndex)) {
                            break;
                        }
                        i3--;
                    }
                } else if (size != this.mBackStack.size() - 1) {
                    i3 = size + 1;
                }
            } else {
                i3 = z ? 0 : this.mBackStack.size() - 1;
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
        boolean isInBackStack = fragment.isInBackStack();
        if (fragment.mDetached && isInBackStack) {
            return;
        }
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
        int i;
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher;
        FragmentStateManager fragmentStateManager;
        Bundle bundle;
        Bundle bundle2;
        Bundle bundle3;
        Bundle bundle4 = (Bundle) parcelable;
        for (String str : bundle4.keySet()) {
            if (str.startsWith("result_") && (bundle3 = bundle4.getBundle(str)) != null) {
                bundle3.setClassLoader(this.mHost.context.getClassLoader());
                this.mResults.put(str.substring(7), bundle3);
            }
        }
        HashMap hashMap = new HashMap();
        for (String str2 : bundle4.keySet()) {
            if (str2.startsWith("fragment_") && (bundle2 = bundle4.getBundle(str2)) != null) {
                bundle2.setClassLoader(this.mHost.context.getClassLoader());
                hashMap.put(str2.substring(9), bundle2);
            }
        }
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.mSavedState.clear();
        fragmentStore.mSavedState.putAll(hashMap);
        FragmentManagerState fragmentManagerState = (FragmentManagerState) bundle4.getParcelable("state");
        if (fragmentManagerState == null) {
            return;
        }
        fragmentStore.mActive.clear();
        Log.d("FragmentManager", fragmentStore + " clear Active Fragments: " + fragmentStore.mActive + ", mActive size: " + fragmentStore.mActive.size());
        ArrayList arrayList = fragmentManagerState.mActive;
        int size = arrayList.size();
        int i2 = 0;
        while (true) {
            i = 2;
            fragmentLifecycleCallbacksDispatcher = this.mLifecycleCallbacksDispatcher;
            if (i2 >= size) {
                break;
            }
            Object obj = arrayList.get(i2);
            i2++;
            Bundle savedState = fragmentStore.setSavedState(null, (String) obj);
            if (savedState != null) {
                Fragment fragment = (Fragment) this.mNonConfig.mRetainedFragments.get(((FragmentState) savedState.getParcelable("state")).mWho);
                if (fragment != null) {
                    if (isLoggingEnabled(2)) {
                        fragment.toString();
                    }
                    fragmentStateManager = new FragmentStateManager(fragmentLifecycleCallbacksDispatcher, fragmentStore, fragment, savedState);
                    bundle = savedState;
                } else {
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.context.getClassLoader(), getFragmentFactory(), savedState);
                    bundle = savedState;
                }
                Fragment fragment2 = fragmentStateManager.mFragment;
                fragment2.mSavedFragmentState = bundle;
                fragment2.mFragmentManager = this;
                if (isLoggingEnabled(2)) {
                    fragment2.toString();
                }
                fragmentStateManager.restoreState(this.mHost.context.getClassLoader());
                fragmentStore.makeActive(fragmentStateManager);
                fragmentStateManager.mFragmentManagerState = this.mCurState;
            }
        }
        FragmentManagerViewModel fragmentManagerViewModel = this.mNonConfig;
        fragmentManagerViewModel.getClass();
        ArrayList arrayList2 = new ArrayList(fragmentManagerViewModel.mRetainedFragments.values());
        int size2 = arrayList2.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            Fragment fragment3 = (Fragment) obj2;
            if (fragmentStore.mActive.get(fragment3.mWho) == null) {
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
        ArrayList arrayList3 = fragmentManagerState.mAdded;
        fragmentStore.mAdded.clear();
        if (arrayList3 != null) {
            int size3 = arrayList3.size();
            int i4 = 0;
            while (i4 < size3) {
                Object obj3 = arrayList3.get(i4);
                i4++;
                String str3 = (String) obj3;
                Fragment findActiveFragment = fragmentStore.findActiveFragment(str3);
                if (findActiveFragment == null) {
                    throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("No instantiated fragment for (", str3, ")"));
                }
                if (isLoggingEnabled(2)) {
                    findActiveFragment.toString();
                }
                fragmentStore.addFragment(findActiveFragment);
            }
        }
        if (fragmentManagerState.mBackStack != null) {
            this.mBackStack = new ArrayList(fragmentManagerState.mBackStack.length);
            int i5 = 0;
            while (true) {
                BackStackRecordState[] backStackRecordStateArr = fragmentManagerState.mBackStack;
                if (i5 >= backStackRecordStateArr.length) {
                    break;
                }
                BackStackRecordState backStackRecordState = backStackRecordStateArr[i5];
                backStackRecordState.getClass();
                BackStackRecord backStackRecord = new BackStackRecord(this);
                int i6 = 0;
                int i7 = 0;
                while (i6 < backStackRecordState.mOps.length) {
                    FragmentTransaction.Op op = new FragmentTransaction.Op();
                    int i8 = i6 + 1;
                    op.mCmd = backStackRecordState.mOps[i6];
                    if (isLoggingEnabled(i)) {
                        Objects.toString(backStackRecord);
                        int i9 = backStackRecordState.mOps[i8];
                    }
                    op.mOldMaxState = Lifecycle.State.values()[backStackRecordState.mOldMaxLifecycleStates[i7]];
                    op.mCurrentMaxState = Lifecycle.State.values()[backStackRecordState.mCurrentMaxLifecycleStates[i7]];
                    int[] iArr = backStackRecordState.mOps;
                    int i10 = i6 + 2;
                    op.mFromExpandedOp = iArr[i8] != 0;
                    int i11 = iArr[i10];
                    op.mEnterAnim = i11;
                    int i12 = iArr[i6 + 3];
                    op.mExitAnim = i12;
                    int i13 = i6 + 5;
                    int i14 = i;
                    int i15 = iArr[i6 + 4];
                    op.mPopEnterAnim = i15;
                    i6 += 6;
                    int i16 = iArr[i13];
                    op.mPopExitAnim = i16;
                    backStackRecord.mEnterAnim = i11;
                    backStackRecord.mExitAnim = i12;
                    backStackRecord.mPopEnterAnim = i15;
                    backStackRecord.mPopExitAnim = i16;
                    backStackRecord.addOp(op);
                    i7++;
                    i = i14;
                }
                int i17 = i;
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
                for (int i18 = 0; i18 < backStackRecordState.mFragmentWhos.size(); i18++) {
                    String str4 = (String) backStackRecordState.mFragmentWhos.get(i18);
                    if (str4 != null) {
                        ((FragmentTransaction.Op) backStackRecord.mOps.get(i18)).mFragment = fragmentStore.findActiveFragment(str4);
                    }
                }
                backStackRecord.bumpBackStackNesting(1);
                if (isLoggingEnabled(i17)) {
                    backStackRecord.toString();
                    PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
                    backStackRecord.dump(printWriter, "  ", false);
                    printWriter.close();
                }
                this.mBackStack.add(backStackRecord);
                i5++;
                i = i17;
            }
        } else {
            this.mBackStack = new ArrayList();
        }
        this.mBackStackIndex.set(fragmentManagerState.mBackStackIndex);
        String str5 = fragmentManagerState.mPrimaryNavActiveWho;
        if (str5 != null) {
            Fragment findActiveFragment2 = fragmentStore.findActiveFragment(str5);
            this.mPrimaryNav = findActiveFragment2;
            dispatchParentPrimaryNavigationFragmentChanged(findActiveFragment2);
        }
        ArrayList arrayList4 = fragmentManagerState.mBackStackStateKeys;
        if (arrayList4 != null) {
            for (int i19 = 0; i19 < arrayList4.size(); i19++) {
                this.mBackStackStates.put((String) arrayList4.get(i19), (BackStackState) fragmentManagerState.mBackStackStates.get(i19));
            }
        }
        this.mLaunchedFragments = new ArrayDeque(fragmentManagerState.mLaunchedFragments);
    }

    public final Bundle saveAllStateInternal() {
        int i;
        BackStackRecordState[] backStackRecordStateArr;
        ArrayList arrayList;
        Bundle bundle;
        Bundle bundle2 = new Bundle();
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        execPendingActions(true);
        this.mStateSaved = true;
        this.mNonConfig.mIsStateSaved = true;
        FragmentStore fragmentStore = this.mFragmentStore;
        fragmentStore.getClass();
        ArrayList arrayList2 = new ArrayList(fragmentStore.mActive.size());
        for (FragmentStateManager fragmentStateManager : fragmentStore.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment = fragmentStateManager.mFragment;
                String str = fragment.mWho;
                fragmentStateManager.getClass();
                Bundle bundle3 = new Bundle();
                Fragment fragment2 = fragmentStateManager.mFragment;
                if (fragment2.mState == -1 && (bundle = fragment2.mSavedFragmentState) != null) {
                    bundle3.putAll(bundle);
                }
                bundle3.putParcelable("state", new FragmentState(fragment2));
                if (fragment2.mState > -1) {
                    Bundle bundle4 = new Bundle();
                    fragment2.onSaveInstanceState(bundle4);
                    if (!bundle4.isEmpty()) {
                        bundle3.putBundle("savedInstanceState", bundle4);
                    }
                    fragmentStateManager.mDispatcher.dispatchOnFragmentSaveInstanceState(false);
                    Bundle bundle5 = new Bundle();
                    fragment2.mSavedStateRegistryController.performSave(bundle5);
                    if (!bundle5.isEmpty()) {
                        bundle3.putBundle("registryState", bundle5);
                    }
                    Bundle saveAllStateInternal = fragment2.mChildFragmentManager.saveAllStateInternal();
                    if (!saveAllStateInternal.isEmpty()) {
                        bundle3.putBundle("childFragmentManager", saveAllStateInternal);
                    }
                    if (fragment2.mView != null) {
                        fragmentStateManager.saveViewState();
                    }
                    SparseArray<? extends Parcelable> sparseArray = fragment2.mSavedViewState;
                    if (sparseArray != null) {
                        bundle3.putSparseParcelableArray("viewState", sparseArray);
                    }
                    Bundle bundle6 = fragment2.mSavedViewRegistryState;
                    if (bundle6 != null) {
                        bundle3.putBundle("viewRegistryState", bundle6);
                    }
                }
                Bundle bundle7 = fragment2.mArguments;
                if (bundle7 != null) {
                    bundle3.putBundle("arguments", bundle7);
                }
                fragmentStore.setSavedState(bundle3, str);
                arrayList2.add(fragment.mWho);
                if (isLoggingEnabled(2)) {
                    fragment.toString();
                    Objects.toString(fragment.mSavedFragmentState);
                }
            }
        }
        HashMap hashMap = this.mFragmentStore.mSavedState;
        if (hashMap.isEmpty()) {
            return bundle2;
        }
        FragmentStore fragmentStore2 = this.mFragmentStore;
        synchronized (fragmentStore2.mAdded) {
            try {
                backStackRecordStateArr = null;
                if (fragmentStore2.mAdded.isEmpty()) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList(fragmentStore2.mAdded.size());
                    ArrayList arrayList3 = fragmentStore2.mAdded;
                    int size = arrayList3.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList3.get(i2);
                        i2++;
                        Fragment fragment3 = (Fragment) obj;
                        arrayList.add(fragment3.mWho);
                        if (isLoggingEnabled(2)) {
                            fragment3.toString();
                        }
                    }
                }
            } finally {
            }
        }
        int size2 = this.mBackStack.size();
        if (size2 > 0) {
            backStackRecordStateArr = new BackStackRecordState[size2];
            for (i = 0; i < size2; i++) {
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
        Fragment fragment4 = this.mPrimaryNav;
        if (fragment4 != null) {
            fragmentManagerState.mPrimaryNavActiveWho = fragment4.mWho;
        }
        fragmentManagerState.mBackStackStateKeys.addAll(this.mBackStackStates.keySet());
        fragmentManagerState.mBackStackStates.addAll(this.mBackStackStates.values());
        fragmentManagerState.mLaunchedFragments = new ArrayList(this.mLaunchedFragments);
        bundle2.putParcelable("state", fragmentManagerState);
        for (String str2 : this.mResults.keySet()) {
            bundle2.putBundle(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("result_", str2), (Bundle) this.mResults.get(str2));
        }
        for (String str3 : hashMap.keySet()) {
            bundle2.putBundle(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("fragment_", str3), (Bundle) hashMap.get(str3));
        }
        return bundle2;
    }

    public final void scheduleCommit() {
        synchronized (this.mPendingActions) {
            try {
                if (this.mPendingActions.size() == 1) {
                    this.mHost.handler.removeCallbacks(this.mExecCommit);
                    this.mHost.handler.post(this.mExecCommit);
                    updateOnBackPressedCallbackEnabled();
                }
            } catch (Throwable th) {
                throw th;
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
        if (fragment.equals(this.mFragmentStore.findActiveFragment(fragment.mWho)) && (fragment.mHost == null || fragment.mFragmentManager == this)) {
            fragment.mMaxState = state;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    public final void setPrimaryNavigationFragment(Fragment fragment) {
        if (fragment != null) {
            if (!fragment.equals(this.mFragmentStore.findActiveFragment(fragment.mWho)) || (fragment.mHost != null && fragment.mFragmentManager != this)) {
                throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
            }
        }
        Fragment fragment2 = this.mPrimaryNav;
        this.mPrimaryNav = fragment;
        dispatchParentPrimaryNavigationFragmentChanged(fragment2);
        dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
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
        ArrayList arrayList = (ArrayList) this.mFragmentStore.getActiveFragmentStateManagers();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            FragmentStateManager fragmentStateManager = (FragmentStateManager) obj;
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

    /* JADX WARN: Type inference failed for: r2v6, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.FunctionReferenceImpl] */
    /* JADX WARN: Type inference failed for: r5v3, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.FunctionReferenceImpl] */
    public final void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            try {
                if (!this.mPendingActions.isEmpty()) {
                    AnonymousClass1 anonymousClass1 = this.mOnBackPressedCallback;
                    anonymousClass1.isEnabled = true;
                    ?? r2 = anonymousClass1.enabledChangedCallback;
                    if (r2 != 0) {
                        r2.invoke();
                    }
                    if (isLoggingEnabled(3)) {
                        Log.d("FragmentManager", "FragmentManager " + this + " enabling OnBackPressedCallback, caused by non-empty pending actions");
                    }
                    return;
                }
                boolean z = this.mBackStack.size() + (this.mTransitioningOp != null ? 1 : 0) > 0 && isPrimaryNavigation(this.mParent);
                if (isLoggingEnabled(3)) {
                    Log.d("FragmentManager", "OnBackPressedCallback for FragmentManager " + this + " enabled state is " + z);
                }
                AnonymousClass1 anonymousClass12 = this.mOnBackPressedCallback;
                anonymousClass12.isEnabled = z;
                ?? r5 = anonymousClass12.enabledChangedCallback;
                if (r5 != 0) {
                    r5.invoke();
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
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
        this.mFragmentStore.mActive.values().removeAll(Collections.singleton(null));
        return popBackStackState;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LaunchedFragmentInfo implements Parcelable {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class FragmentLifecycleCallbacks {
        public void onFragmentAttached(Fragment fragment) {
        }
    }
}
