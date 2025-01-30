package androidx.fragment.app;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FragmentStore {
    public FragmentManagerViewModel mNonConfig;
    public final ArrayList mAdded = new ArrayList();
    public final HashMap mActive = new HashMap();
    public final HashMap mSavedState = new HashMap();

    public final void addFragment(Fragment fragment) {
        if (this.mAdded.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        synchronized (this.mAdded) {
            this.mAdded.add(fragment);
        }
        fragment.mAdded = true;
    }

    public final Fragment findActiveFragment(String str) {
        FragmentStateManager fragmentStateManager = (FragmentStateManager) this.mActive.get(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager.mFragment;
        }
        return null;
    }

    public final Fragment findFragmentByWho(String str) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragment = fragmentStateManager.mFragment;
                if (!str.equals(fragment.mWho)) {
                    fragment = fragment.mChildFragmentManager.mFragmentStore.findFragmentByWho(str);
                }
                if (fragment != null) {
                    return fragment;
                }
            }
        }
        return null;
    }

    public final List getActiveFragmentStateManagers() {
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager);
            }
        }
        return arrayList;
    }

    public final List getActiveFragments() {
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager.mFragment);
            } else {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    public final List getFragments() {
        ArrayList arrayList;
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.mAdded) {
            arrayList = new ArrayList(this.mAdded);
        }
        return arrayList;
    }

    public final void makeActive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.mFragment;
        String str = fragment.mWho;
        HashMap hashMap = this.mActive;
        if (hashMap.get(str) != null) {
            return;
        }
        hashMap.put(fragment.mWho, fragmentStateManager);
        Log.d("FragmentManager", this + " put " + fragment + " to Active Fragments, mActive size: " + hashMap.size());
        if (FragmentManager.isLoggingEnabled(2)) {
            fragment.toString();
        }
    }

    public final void makeInactive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.mFragment;
        if (fragment.mRetainInstance) {
            this.mNonConfig.removeRetainedFragment(fragment);
        }
        HashMap hashMap = this.mActive;
        FragmentStateManager fragmentStateManager2 = (FragmentStateManager) hashMap.put(fragment.mWho, null);
        Log.d("FragmentManager", this + "put null to Active Fragments, mActive size: " + hashMap.size() + ", fragment: " + fragment);
        if (fragmentStateManager2 != null && FragmentManager.isLoggingEnabled(2)) {
            fragment.toString();
        }
    }

    public final FragmentState setSavedState(String str, FragmentState fragmentState) {
        HashMap hashMap = this.mSavedState;
        return fragmentState != null ? (FragmentState) hashMap.put(str, fragmentState) : (FragmentState) hashMap.remove(str);
    }
}
