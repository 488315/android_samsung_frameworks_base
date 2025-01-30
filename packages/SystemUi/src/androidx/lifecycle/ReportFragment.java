package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ReportFragment extends Fragment {
    public ProcessLifecycleOwner.C03042 mProcessListener;

    /* JADX WARN: Multi-variable type inference failed */
    public static void dispatch(Activity activity, Lifecycle.Event event) {
        if (activity instanceof LifecycleOwner) {
            LifecycleRegistry lifecycle = ((LifecycleOwner) activity).getLifecycle();
            if (lifecycle instanceof LifecycleRegistry) {
                lifecycle.handleLifecycleEvent(event);
            }
        }
    }

    public static void injectIfNeededIn(Activity activity) {
        LifecycleCallbacks.registerIn(activity);
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
            fragmentManager.beginTransaction().add(new ReportFragment(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
            fragmentManager.executePendingTransactions();
        }
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
        this.mProcessListener = null;
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        ProcessLifecycleOwner.C03042 c03042 = this.mProcessListener;
        if (c03042 != null) {
            ProcessLifecycleOwner.this.activityResumed();
        }
        Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        ProcessLifecycleOwner.C03042 c03042 = this.mProcessListener;
        if (c03042 != null) {
            ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
            int i = processLifecycleOwner.mStartedCounter + 1;
            processLifecycleOwner.mStartedCounter = i;
            if (i == 1 && processLifecycleOwner.mStopSent) {
                processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                processLifecycleOwner.mStopSent = false;
            }
        }
        Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public static void registerIn(Activity activity) {
            activity.registerActivityLifecycleCallbacks(new LifecycleCallbacks());
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostCreated(Activity activity, Bundle bundle) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_CREATE);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostResumed(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_RESUME);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostStarted(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_START);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreDestroyed(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_DESTROY);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPrePaused(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_PAUSE);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreStopped(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_STOP);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }
    }
}
