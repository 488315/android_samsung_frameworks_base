package androidx.core.view;

import android.view.Menu;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MenuHostHelper {
    public final Runnable mOnInvalidateMenuCallback;
    public final CopyOnWriteArrayList mMenuProviders = new CopyOnWriteArrayList();
    public final Map mProviderToLifecycleContainers = new HashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LifecycleContainer {
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

    public final void onPrepareMenu(Menu menu) {
        Iterator it = this.mMenuProviders.iterator();
        while (it.hasNext()) {
            FragmentManager.this.dispatchPrepareOptionsMenu(menu);
        }
    }

    public final void removeMenuProvider(FragmentManager.AnonymousClass2 anonymousClass2) {
        this.mMenuProviders.remove(anonymousClass2);
        LifecycleContainer lifecycleContainer = (LifecycleContainer) ((HashMap) this.mProviderToLifecycleContainers).remove(anonymousClass2);
        if (lifecycleContainer != null) {
            lifecycleContainer.mLifecycle.removeObserver(lifecycleContainer.mObserver);
            lifecycleContainer.mObserver = null;
        }
        this.mOnInvalidateMenuCallback.run();
    }
}
