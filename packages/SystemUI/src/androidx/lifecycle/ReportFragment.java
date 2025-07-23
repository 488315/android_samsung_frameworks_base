package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ReportFragment extends Fragment {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static void dispatch$lifecycle_runtime_release(Activity activity, Lifecycle.Event event) {
            if (activity instanceof LifecycleOwner) {
                Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
                if (lifecycle instanceof LifecycleRegistry) {
                    ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
                }
            }
        }

        public static void injectIfNeededIn(Activity activity) {
            LifecycleCallbacks.Companion.getClass();
            activity.registerActivityLifecycleCallbacks(new LifecycleCallbacks());
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
                fragmentManager.beginTransaction().add(new ReportFragment(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
                fragmentManager.executePendingTransactions();
            }
        }

        private Companion() {
        }
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        Lifecycle.Event.Companion companion = Lifecycle.Event.Companion;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public static final Companion Companion = new Companion(null);

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public static final void registerIn(Activity activity) {
            Companion.getClass();
            activity.registerActivityLifecycleCallbacks(new LifecycleCallbacks());
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostCreated(Activity activity, Bundle bundle) {
            Companion companion = ReportFragment.Companion;
            Lifecycle.Event event = Lifecycle.Event.ON_CREATE;
            companion.getClass();
            Companion.dispatch$lifecycle_runtime_release(activity, event);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostResumed(Activity activity) {
            Companion companion = ReportFragment.Companion;
            Lifecycle.Event event = Lifecycle.Event.ON_RESUME;
            companion.getClass();
            Companion.dispatch$lifecycle_runtime_release(activity, event);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPostStarted(Activity activity) {
            Companion companion = ReportFragment.Companion;
            Lifecycle.Event event = Lifecycle.Event.ON_START;
            companion.getClass();
            Companion.dispatch$lifecycle_runtime_release(activity, event);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreDestroyed(Activity activity) {
            Companion companion = ReportFragment.Companion;
            Lifecycle.Event event = Lifecycle.Event.ON_DESTROY;
            companion.getClass();
            Companion.dispatch$lifecycle_runtime_release(activity, event);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPrePaused(Activity activity) {
            Companion companion = ReportFragment.Companion;
            Lifecycle.Event event = Lifecycle.Event.ON_PAUSE;
            companion.getClass();
            Companion.dispatch$lifecycle_runtime_release(activity, event);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPreStopped(Activity activity) {
            Companion companion = ReportFragment.Companion;
            Lifecycle.Event event = Lifecycle.Event.ON_STOP;
            companion.getClass();
            Companion.dispatch$lifecycle_runtime_release(activity, event);
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
