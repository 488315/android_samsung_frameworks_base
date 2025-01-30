package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MenuHostHelper {
    public final Runnable mOnInvalidateMenuCallback;
    public final CopyOnWriteArrayList mMenuProviders = new CopyOnWriteArrayList();
    public final Map mProviderToLifecycleContainers = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LifecycleContainer {
        public final Lifecycle mLifecycle;
        public LifecycleEventObserver mObserver;

        public LifecycleContainer(Lifecycle lifecycle, LifecycleEventObserver lifecycleEventObserver) {
            this.mLifecycle = lifecycle;
            this.mObserver = lifecycleEventObserver;
            lifecycle.addObserver(lifecycleEventObserver);
        }
    }

    public MenuHostHelper(Runnable runnable) {
        this.mOnInvalidateMenuCallback = runnable;
    }

    public final boolean onMenuItemSelected(MenuItem menuItem) {
        Iterator it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            if (FragmentManager.this.dispatchOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public final void onPrepareMenu(Menu menu) {
        Iterator it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            FragmentManager.this.dispatchPrepareOptionsMenu(menu);
        }
    }

    public final void removeMenuProvider(FragmentManager.C02322 c02322) {
        this.mMenuProviders.remove(c02322);
        LifecycleContainer lifecycleContainer = (LifecycleContainer) ((HashMap) this.mProviderToLifecycleContainers).remove(c02322);
        if (lifecycleContainer != null) {
            lifecycleContainer.mLifecycle.removeObserver(lifecycleContainer.mObserver);
            lifecycleContainer.mObserver = null;
        }
        this.mOnInvalidateMenuCallback.run();
    }
}
