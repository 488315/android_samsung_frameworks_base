package androidx.fragment.app;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class FragmentStore {
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
                Fragment fragmentFindFragmentByWho = fragmentStateManager.mFragment;
                if (!str.equals(fragmentFindFragmentByWho.mWho)) {
                    fragmentFindFragmentByWho = fragmentFindFragmentByWho.mChildFragmentManager.mFragmentStore.findFragmentByWho(str);
                }
                if (fragmentFindFragmentByWho != null) {
                    return fragmentFindFragmentByWho;
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
            return Collections.EMPTY_LIST;
        }
        synchronized (this.mAdded) {
            arrayList = new ArrayList(this.mAdded);
        }
        return arrayList;
    }

    public final void makeActive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.mFragment;
        if (this.mActive.get(fragment.mWho) != null) {
            return;
        }
        this.mActive.put(fragment.mWho, fragmentStateManager);
        Log.d("FragmentManager", this + " put " + fragment + " to Active Fragments, mActive size: " + this.mActive.size());
        if (FragmentManager.isLoggingEnabled(2)) {
            fragment.toString();
        }
    }

    public final void makeInactive(FragmentStateManager fragmentStateManager) {
        Fragment fragment = fragmentStateManager.mFragment;
        if (fragment.mRetainInstance) {
            this.mNonConfig.removeRetainedFragment(fragment);
        }
        if (this.mActive.get(fragment.mWho) != fragmentStateManager) {
            return;
        }
        FragmentStateManager fragmentStateManager2 = (FragmentStateManager) this.mActive.put(fragment.mWho, null);
        Log.d("FragmentManager", this + "put null to Active Fragments, mActive size: " + this.mActive.size() + ", fragment: " + fragment);
        if (fragmentStateManager2 != null && FragmentManager.isLoggingEnabled(2)) {
            fragment.toString();
        }
    }

    public final Bundle setSavedState(Bundle bundle, String str) {
        return bundle != null ? (Bundle) this.mSavedState.put(str, bundle) : (Bundle) this.mSavedState.remove(str);
    }
}
