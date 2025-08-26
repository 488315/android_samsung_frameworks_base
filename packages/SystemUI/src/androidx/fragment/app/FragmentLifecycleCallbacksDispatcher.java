package androidx.fragment.app;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public final class FragmentLifecycleCallbacksDispatcher {
    public final FragmentManager fragmentManager;
    public final CopyOnWriteArrayList lifecycleCallbacks = new CopyOnWriteArrayList();

    public final class FragmentLifecycleCallbacksHolder {
        public final FragmentManager.FragmentLifecycleCallbacks callback;
        public final boolean recursive;

        public FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
            this.callback = fragmentLifecycleCallbacks;
            this.recursive = z;
        }
    }

    public FragmentLifecycleCallbacksDispatcher(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public final void dispatchOnFragmentActivityCreated(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentAttached(Fragment fragment, boolean z) {
        FragmentManager fragmentManager = this.fragmentManager;
        Context context = fragmentManager.mHost.context;
        Fragment fragment2 = fragmentManager.mParent;
        if (fragment2 != null) {
            fragment2.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentAttached(fragment, true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.onFragmentAttached(fragment);
            }
        }
    }

    public final void dispatchOnFragmentCreated(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentCreated(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentDestroyed(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentDestroyed(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentDetached(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentDetached(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentPaused(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentPaused(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentPreAttached(boolean z) {
        FragmentManager fragmentManager = this.fragmentManager;
        Context context = fragmentManager.mHost.context;
        Fragment fragment = fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentPreAttached(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentPreCreated(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentPreCreated(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentResumed(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentResumed(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentSaveInstanceState(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentSaveInstanceState(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentStarted(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentStarted(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentStopped(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentStopped(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentViewCreated(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }

    public final void dispatchOnFragmentViewDestroyed(boolean z) {
        Fragment fragment = this.fragmentManager.mParent;
        if (fragment != null) {
            fragment.getParentFragmentManager().mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(true);
        }
        Iterator it = this.lifecycleCallbacks.iterator();
        while (it.hasNext()) {
            FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder) it.next();
            if (!z || fragmentLifecycleCallbacksHolder.recursive) {
                fragmentLifecycleCallbacksHolder.callback.getClass();
            }
        }
    }
}
