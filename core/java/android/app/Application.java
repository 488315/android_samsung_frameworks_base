package android.app;

import android.app.ActivityThread;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.ComponentCallbacksController;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.p009os.Bundle;
import android.util.Log;
import android.view.autofill.AutofillManager;
import android.view.autofill.Helper;
import com.samsung.android.ims.options.SemCapabilities;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Application extends ContextWrapper implements ComponentCallbacks2 {
    private static final String TAG = "Application";
    private ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks;
    private ArrayList<OnProvideAssistDataListener> mAssistCallbacks;
    private final ComponentCallbacksController mCallbacksController;
    private int mFlipfont;
    public LoadedApk mLoadedApk;

    public interface OnProvideAssistDataListener {
        void onProvideAssistData(Activity activity, Bundle bundle);
    }

    public interface ActivityLifecycleCallbacks {
        void onActivityCreated(Activity activity, Bundle bundle);

        void onActivityDestroyed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityResumed(Activity activity);

        void onActivitySaveInstanceState(Activity activity, Bundle bundle);

        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);

        default void onActivityPreCreated(Activity activity, Bundle savedInstanceState) {
        }

        default void onActivityPostCreated(Activity activity, Bundle savedInstanceState) {
        }

        default void onActivityPreStarted(Activity activity) {
        }

        default void onActivityPostStarted(Activity activity) {
        }

        default void onActivityPreResumed(Activity activity) {
        }

        default void onActivityPostResumed(Activity activity) {
        }

        default void onActivityPrePaused(Activity activity) {
        }

        default void onActivityPostPaused(Activity activity) {
        }

        default void onActivityPreStopped(Activity activity) {
        }

        default void onActivityPostStopped(Activity activity) {
        }

        default void onActivityPreSaveInstanceState(Activity activity, Bundle outState) {
        }

        default void onActivityPostSaveInstanceState(Activity activity, Bundle outState) {
        }

        default void onActivityPreDestroyed(Activity activity) {
        }

        default void onActivityPostDestroyed(Activity activity) {
        }

        default void onActivityConfigurationChanged(Activity activity) {
        }
    }

    public Application() {
        super(null);
        this.mActivityLifecycleCallbacks = new ArrayList<>();
        this.mAssistCallbacks = null;
        this.mCallbacksController = new ComponentCallbacksController();
        this.mFlipfont = 0;
    }

    private String getLoadedApkInfo() {
        if (this.mLoadedApk == null) {
            return SemCapabilities.FEATURE_TAG_NULL;
        }
        return this.mLoadedApk + "/pkg=" + this.mLoadedApk.mPackageName;
    }

    public void onCreate() {
        Typeface.setFlipFonts();
    }

    public void onTerminate() {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig != null && newConfig.FlipFont > 0 && this.mFlipfont != newConfig.FlipFont) {
            Typeface.setFlipFonts();
            this.mFlipfont = newConfig.FlipFont;
        }
        this.mCallbacksController.dispatchConfigurationChanged(newConfig);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCallbacksController.dispatchLowMemory();
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        this.mCallbacksController.dispatchTrimMemory(level);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        this.mCallbacksController.registerCallbacks(callback);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        this.mCallbacksController.unregisterCallbacks(callback);
    }

    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(callback);
        }
    }

    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        synchronized (this) {
            if (this.mAssistCallbacks == null) {
                this.mAssistCallbacks = new ArrayList<>();
            }
            this.mAssistCallbacks.add(callback);
        }
    }

    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener callback) {
        synchronized (this) {
            ArrayList<OnProvideAssistDataListener> arrayList = this.mAssistCallbacks;
            if (arrayList != null) {
                arrayList.remove(callback);
            }
        }
    }

    public static String getProcessName() {
        return ActivityThread.currentProcessName();
    }

    final void attach(Context context) {
        attachBaseContext(context);
        this.mLoadedApk = ContextImpl.getImpl(context).mPackageInfo;
    }

    void dispatchActivityPreCreated(Activity activity, Bundle savedInstanceState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreCreated(activity, savedInstanceState);
            }
        }
    }

    void dispatchActivityCreated(Activity activity, Bundle savedInstanceState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    void dispatchActivityPostCreated(Activity activity, Bundle savedInstanceState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostCreated(activity, savedInstanceState);
            }
        }
    }

    void dispatchActivityPreStarted(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreStarted(activity);
            }
        }
    }

    void dispatchActivityStarted(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityStarted(activity);
            }
        }
    }

    void dispatchActivityPostStarted(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostStarted(activity);
            }
        }
    }

    void dispatchActivityPreResumed(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreResumed(activity);
            }
        }
    }

    void dispatchActivityResumed(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityResumed(activity);
            }
        }
    }

    void dispatchActivityPostResumed(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostResumed(activity);
            }
        }
    }

    void dispatchActivityPrePaused(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPrePaused(activity);
            }
        }
    }

    void dispatchActivityPaused(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPaused(activity);
            }
        }
    }

    void dispatchActivityPostPaused(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostPaused(activity);
            }
        }
    }

    void dispatchActivityPreStopped(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreStopped(activity);
            }
        }
    }

    void dispatchActivityStopped(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityStopped(activity);
            }
        }
    }

    void dispatchActivityPostStopped(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostStopped(activity);
            }
        }
    }

    void dispatchActivityPreSaveInstanceState(Activity activity, Bundle outState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreSaveInstanceState(activity, outState);
            }
        }
    }

    void dispatchActivitySaveInstanceState(Activity activity, Bundle outState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    void dispatchActivityPostSaveInstanceState(Activity activity, Bundle outState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostSaveInstanceState(activity, outState);
            }
        }
    }

    void dispatchActivityPreDestroyed(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreDestroyed(activity);
            }
        }
    }

    void dispatchActivityDestroyed(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityDestroyed(activity);
            }
        }
    }

    void dispatchActivityPostDestroyed(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostDestroyed(activity);
            }
        }
    }

    void dispatchActivityConfigurationChanged(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityConfigurationChanged(activity);
            }
        }
    }

    private Object[] collectActivityLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (this.mActivityLifecycleCallbacks) {
            if (this.mActivityLifecycleCallbacks.size() > 0) {
                callbacks = this.mActivityLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }

    void dispatchOnProvideAssistData(Activity activity, Bundle data) {
        synchronized (this) {
            ArrayList<OnProvideAssistDataListener> arrayList = this.mAssistCallbacks;
            if (arrayList == null) {
                return;
            }
            Object[] callbacks = arrayList.toArray();
            if (callbacks != null) {
                for (Object obj : callbacks) {
                    ((OnProvideAssistDataListener) obj).onProvideAssistData(activity, data);
                }
            }
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AutofillManager.AutofillClient getAutofillClient() {
        Activity activity;
        AutofillManager.AutofillClient client = super.getAutofillClient();
        if (client != null) {
            return client;
        }
        if (Helper.sVerbose) {
            Log.m100v(TAG, "getAutofillClient(): null on super, trying to find activity thread");
        }
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        if (activityThread == null) {
            return null;
        }
        int activityCount = activityThread.mActivities.size();
        for (int i = 0; i < activityCount; i++) {
            ActivityThread.ActivityClientRecord record = activityThread.mActivities.valueAt(i);
            if (record != null && (activity = record.activity) != null && activity.getWindow().getDecorView().hasFocus()) {
                if (Helper.sVerbose) {
                    Log.m100v(TAG, "getAutofillClient(): found activity for " + this + ": " + activity);
                }
                return activity.getAutofillClient();
            }
        }
        if (Helper.sVerbose) {
            Log.m100v(TAG, "getAutofillClient(): none of the " + activityCount + " activities on " + this + " have focus");
        }
        return null;
    }
}
