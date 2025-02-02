package androidx.fragment.app;

import android.os.Bundle;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.helper.widget.LogJson$JsonWriter$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
final class FragmentStore {
    private FragmentManagerViewModel mNonConfig;
    private final ArrayList<Fragment> mAdded = new ArrayList<>();
    private final HashMap<String, FragmentStateManager> mActive = new HashMap<>();
    private final HashMap<String, Bundle> mSavedState = new HashMap<>();

    FragmentStore() {
    }

    final void addFragment(Fragment fragment) {
        if (this.mAdded.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        synchronized (this.mAdded) {
            this.mAdded.add(fragment);
        }
        fragment.mAdded = true;
    }

    final void burpActive() {
        this.mActive.values().removeAll(Collections.singleton(null));
    }

    final boolean containsActiveFragment(String str) {
        return this.mActive.get(str) != null;
    }

    final void dispatchStateChange(int i) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                fragmentStateManager.setFragmentManagerState(i);
            }
        }
    }

    final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2;
        String m1m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(str, "    ");
        HashMap<String, FragmentStateManager> hashMap = this.mActive;
        if (!hashMap.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : hashMap.values()) {
                printWriter.print(str);
                if (fragmentStateManager != null) {
                    Fragment fragment = fragmentStateManager.getFragment();
                    printWriter.println(fragment);
                    fragment.getClass();
                    printWriter.print(m1m);
                    printWriter.print("mFragmentId=#");
                    printWriter.print(Integer.toHexString(fragment.mFragmentId));
                    printWriter.print(" mContainerId=#");
                    printWriter.print(Integer.toHexString(fragment.mContainerId));
                    printWriter.print(" mTag=");
                    printWriter.println(fragment.mTag);
                    printWriter.print(m1m);
                    printWriter.print("mState=");
                    printWriter.print(fragment.mState);
                    printWriter.print(" mWho=");
                    printWriter.print(fragment.mWho);
                    printWriter.print(" mBackStackNesting=");
                    printWriter.println(fragment.mBackStackNesting);
                    printWriter.print(m1m);
                    printWriter.print("mAdded=");
                    printWriter.print(fragment.mAdded);
                    printWriter.print(" mRemoving=");
                    printWriter.print(fragment.mRemoving);
                    printWriter.print(" mFromLayout=");
                    printWriter.print(fragment.mFromLayout);
                    printWriter.print(" mInLayout=");
                    printWriter.println(fragment.mInLayout);
                    printWriter.print(m1m);
                    printWriter.print("mHidden=");
                    printWriter.print(fragment.mHidden);
                    printWriter.print(" mDetached=");
                    printWriter.print(fragment.mDetached);
                    printWriter.print(" mMenuVisible=");
                    printWriter.print(fragment.mMenuVisible);
                    printWriter.print(" mHasMenu=");
                    printWriter.println(false);
                    printWriter.print(m1m);
                    printWriter.print("mRetainInstance=");
                    printWriter.print(fragment.mRetainInstance);
                    printWriter.print(" mUserVisibleHint=");
                    printWriter.println(fragment.mUserVisibleHint);
                    if (fragment.mFragmentManager != null) {
                        printWriter.print(m1m);
                        printWriter.print("mFragmentManager=");
                        printWriter.println(fragment.mFragmentManager);
                    }
                    if (fragment.mHost != null) {
                        printWriter.print(m1m);
                        printWriter.print("mHost=");
                        printWriter.println(fragment.mHost);
                    }
                    if (fragment.mParentFragment != null) {
                        printWriter.print(m1m);
                        printWriter.print("mParentFragment=");
                        printWriter.println(fragment.mParentFragment);
                    }
                    if (fragment.mArguments != null) {
                        printWriter.print(m1m);
                        printWriter.print("mArguments=");
                        printWriter.println(fragment.mArguments);
                    }
                    if (fragment.mSavedFragmentState != null) {
                        printWriter.print(m1m);
                        printWriter.print("mSavedFragmentState=");
                        printWriter.println(fragment.mSavedFragmentState);
                    }
                    if (fragment.mSavedViewState != null) {
                        printWriter.print(m1m);
                        printWriter.print("mSavedViewState=");
                        printWriter.println(fragment.mSavedViewState);
                    }
                    if (fragment.mSavedViewRegistryState != null) {
                        printWriter.print(m1m);
                        printWriter.print("mSavedViewRegistryState=");
                        printWriter.println(fragment.mSavedViewRegistryState);
                    }
                    Object obj = fragment.mTarget;
                    if (obj == null) {
                        FragmentManager fragmentManager = fragment.mFragmentManager;
                        obj = (fragmentManager == null || (str2 = fragment.mTargetWho) == null) ? null : fragmentManager.findActiveFragment(str2);
                    }
                    if (obj != null) {
                        printWriter.print(m1m);
                        printWriter.print("mTarget=");
                        printWriter.print(obj);
                        printWriter.print(" mTargetRequestCode=");
                        printWriter.println(fragment.mTargetRequestCode);
                    }
                    printWriter.print(m1m);
                    printWriter.print("mPopDirection=");
                    Fragment.AnimationInfo animationInfo = fragment.mAnimationInfo;
                    printWriter.println(animationInfo == null ? false : animationInfo.mIsPop);
                    Fragment.AnimationInfo animationInfo2 = fragment.mAnimationInfo;
                    if ((animationInfo2 == null ? 0 : animationInfo2.mEnterAnim) != 0) {
                        printWriter.print(m1m);
                        printWriter.print("getEnterAnim=");
                        Fragment.AnimationInfo animationInfo3 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo3 == null ? 0 : animationInfo3.mEnterAnim);
                    }
                    Fragment.AnimationInfo animationInfo4 = fragment.mAnimationInfo;
                    if ((animationInfo4 == null ? 0 : animationInfo4.mExitAnim) != 0) {
                        printWriter.print(m1m);
                        printWriter.print("getExitAnim=");
                        Fragment.AnimationInfo animationInfo5 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo5 == null ? 0 : animationInfo5.mExitAnim);
                    }
                    Fragment.AnimationInfo animationInfo6 = fragment.mAnimationInfo;
                    if ((animationInfo6 == null ? 0 : animationInfo6.mPopEnterAnim) != 0) {
                        printWriter.print(m1m);
                        printWriter.print("getPopEnterAnim=");
                        Fragment.AnimationInfo animationInfo7 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo7 == null ? 0 : animationInfo7.mPopEnterAnim);
                    }
                    Fragment.AnimationInfo animationInfo8 = fragment.mAnimationInfo;
                    if ((animationInfo8 == null ? 0 : animationInfo8.mPopExitAnim) != 0) {
                        printWriter.print(m1m);
                        printWriter.print("getPopExitAnim=");
                        Fragment.AnimationInfo animationInfo9 = fragment.mAnimationInfo;
                        printWriter.println(animationInfo9 == null ? 0 : animationInfo9.mPopExitAnim);
                    }
                    if (fragment.mContainer != null) {
                        printWriter.print(m1m);
                        printWriter.print("mContainer=");
                        printWriter.println(fragment.mContainer);
                    }
                    FragmentHostCallback<?> fragmentHostCallback = fragment.mHost;
                    if ((fragmentHostCallback != null ? fragmentHostCallback.getContext() : null) != null) {
                        LoaderManager.getInstance(fragment).dump(m1m, fileDescriptor, printWriter, strArr);
                    }
                    printWriter.print(m1m);
                    printWriter.println("Child " + fragment.mChildFragmentManager + ":");
                    fragment.mChildFragmentManager.dump(AbstractResolvableFuture$$ExternalSyntheticOutline0.m1m(m1m, "  "), fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        ArrayList<Fragment> arrayList = this.mAdded;
        int size = arrayList.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size; i++) {
                Fragment fragment2 = arrayList.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment2.toString());
            }
        }
    }

    final Fragment findActiveFragment(String str) {
        FragmentStateManager fragmentStateManager = this.mActive.get(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager.getFragment();
        }
        return null;
    }

    final Fragment findFragmentById(int i) {
        ArrayList<Fragment> arrayList = this.mAdded;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Fragment fragment = arrayList.get(size);
            if (fragment != null && fragment.mFragmentId == i) {
                return fragment;
            }
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment2 = fragmentStateManager.getFragment();
                if (fragment2.mFragmentId == i) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    final Fragment findFragmentByTag(String str) {
        ArrayList<Fragment> arrayList = this.mAdded;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
                    if (fragmentStateManager != null) {
                        Fragment fragment = fragmentStateManager.getFragment();
                        if (str.equals(fragment.mTag)) {
                            return fragment;
                        }
                    }
                }
                return null;
            }
            Fragment fragment2 = arrayList.get(size);
            if (fragment2 != null && str.equals(fragment2.mTag)) {
                return fragment2;
            }
        }
    }

    final Fragment findFragmentByWho(String str) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment = fragmentStateManager.getFragment();
                if (!str.equals(fragment.mWho)) {
                    fragment = fragment.mChildFragmentManager.findFragmentByWho(str);
                }
                if (fragment != null) {
                    return fragment;
                }
            }
        }
        return null;
    }

    final List<FragmentStateManager> getActiveFragmentStateManagers() {
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager);
            }
        }
        return arrayList;
    }

    final List<Fragment> getActiveFragments() {
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager.getFragment());
            } else {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    final HashMap<String, Bundle> getAllSavedState() {
        return this.mSavedState;
    }

    final FragmentStateManager getFragmentStateManager(String str) {
        return this.mActive.get(str);
    }

    final List<Fragment> getFragments() {
        ArrayList arrayList;
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.mAdded) {
            arrayList = new ArrayList(this.mAdded);
        }
        return arrayList;
    }

    final FragmentManagerViewModel getNonConfig() {
        return this.mNonConfig;
    }

    final void makeActive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (containsActiveFragment(fragment.mWho)) {
            return;
        }
        this.mActive.put(fragment.mWho, fragmentStateManager);
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Added fragment to active set " + fragment);
        }
    }

    final void makeInactive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.getFragment();
        if (fragment.mRetainInstance) {
            this.mNonConfig.removeRetainedFragment(fragment);
        }
        if (this.mActive.put(fragment.mWho, null) != null && FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Removed fragment from active set " + fragment);
        }
    }

    final void moveToExpectedState() {
        HashMap<String, FragmentStateManager> hashMap;
        Iterator<Fragment> it = this.mAdded.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            hashMap = this.mActive;
            if (!hasNext) {
                break;
            }
            FragmentStateManager fragmentStateManager = hashMap.get(it.next().mWho);
            if (fragmentStateManager != null) {
                fragmentStateManager.moveToExpectedState();
            }
        }
        for (FragmentStateManager fragmentStateManager2 : hashMap.values()) {
            if (fragmentStateManager2 != null) {
                fragmentStateManager2.moveToExpectedState();
                Fragment fragment = fragmentStateManager2.getFragment();
                if (fragment.mRemoving && !fragment.isInBackStack()) {
                    makeInactive(fragmentStateManager2);
                }
            }
        }
    }

    final void removeFragment(Fragment fragment) {
        synchronized (this.mAdded) {
            this.mAdded.remove(fragment);
        }
        fragment.mAdded = false;
    }

    final void resetActiveFragments() {
        this.mActive.clear();
    }

    final void restoreAddedFragments(List<String> list) {
        this.mAdded.clear();
        if (list != null) {
            for (String str : list) {
                Fragment findActiveFragment = findActiveFragment(str);
                if (findActiveFragment == null) {
                    throw new IllegalStateException(LogJson$JsonWriter$$ExternalSyntheticOutline0.m4m("No instantiated fragment for (", str, ")"));
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "restoreSaveState: added (" + str + "): " + findActiveFragment);
                }
                addFragment(findActiveFragment);
            }
        }
    }

    final void restoreSaveState(HashMap<String, Bundle> hashMap) {
        HashMap<String, Bundle> hashMap2 = this.mSavedState;
        hashMap2.clear();
        hashMap2.putAll(hashMap);
    }

    final ArrayList<String> saveActiveFragments() {
        HashMap<String, FragmentStateManager> hashMap = this.mActive;
        ArrayList<String> arrayList = new ArrayList<>(hashMap.size());
        for (FragmentStateManager fragmentStateManager : hashMap.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment = fragmentStateManager.getFragment();
                setSavedState(fragment.mWho, fragmentStateManager.saveState());
                arrayList.add(fragment.mWho);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Saved state of " + fragment + ": " + fragment.mSavedFragmentState);
                }
            }
        }
        return arrayList;
    }

    final ArrayList<String> saveAddedFragments() {
        synchronized (this.mAdded) {
            if (this.mAdded.isEmpty()) {
                return null;
            }
            ArrayList<String> arrayList = new ArrayList<>(this.mAdded.size());
            Iterator<Fragment> it = this.mAdded.iterator();
            while (it.hasNext()) {
                Fragment next = it.next();
                arrayList.add(next.mWho);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "saveAllState: adding fragment (" + next.mWho + "): " + next);
                }
            }
            return arrayList;
        }
    }

    final void setNonConfig(FragmentManagerViewModel fragmentManagerViewModel) {
        this.mNonConfig = fragmentManagerViewModel;
    }

    final Bundle setSavedState(String str, Bundle bundle) {
        HashMap<String, Bundle> hashMap = this.mSavedState;
        return bundle != null ? hashMap.put(str, bundle) : hashMap.remove(str);
    }
}
