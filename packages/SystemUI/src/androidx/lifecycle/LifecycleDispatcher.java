package androidx.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import androidx.lifecycle.ReportFragment;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class LifecycleDispatcher {
    public static final AtomicBoolean initialized;

    public final class DispatcherActivityCallback extends EmptyActivityLifecycleCallbacks {
        @Override // androidx.lifecycle.EmptyActivityLifecycleCallbacks, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
            ReportFragment.Companion.getClass();
            ReportFragment.Companion.injectIfNeededIn(activity);
        }
    }

    static {
        new LifecycleDispatcher();
        initialized = new AtomicBoolean(false);
    }

    private LifecycleDispatcher() {
    }
}
