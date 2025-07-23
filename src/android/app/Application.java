package android.app;

import android.app.ActivityThread;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.ComponentCallbacksController;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.autofill.AutofillManager;
import android.view.autofill.Helper;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Application extends ContextWrapper implements ComponentCallbacks2 {
    private static final String TAG = "Application";
    private ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks;
    private ArrayList<OnProvideAssistDataListener> mAssistCallbacks;
    private final ComponentCallbacksController mCallbacksController;
    private int mFlipfont;
    public LoadedApk mLoadedApk;

    public interface ActivityLifecycleCallbacks {
        default void onActivityConfigurationChanged(Activity activity) {
        }

        void onActivityCreated(Activity activity, Bundle bundle);

        void onActivityDestroyed(Activity activity);

        void onActivityPaused(Activity activity);

        default void onActivityPostCreated(Activity activity, Bundle bundle) {
        }

        default void onActivityPostDestroyed(Activity activity) {
        }

        default void onActivityPostPaused(Activity activity) {
        }

        default void onActivityPostResumed(Activity activity) {
        }

        default void onActivityPostSaveInstanceState(Activity activity, Bundle bundle) {
        }

        default void onActivityPostStarted(Activity activity) {
        }

        default void onActivityPostStopped(Activity activity) {
        }

        default void onActivityPreCreated(Activity activity, Bundle bundle) {
        }

        default void onActivityPreDestroyed(Activity activity) {
        }

        default void onActivityPrePaused(Activity activity) {
        }

        default void onActivityPreResumed(Activity activity) {
        }

        default void onActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
        }

        default void onActivityPreStarted(Activity activity) {
        }

        default void onActivityPreStopped(Activity activity) {
        }

        void onActivityResumed(Activity activity);

        void onActivitySaveInstanceState(Activity activity, Bundle bundle);

        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);
    }

    public interface OnProvideAssistDataListener {
        void onProvideAssistData(Activity activity, Bundle bundle);
    }

    public void onTerminate() {
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
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        return this.mLoadedApk + "/pkg=" + this.mLoadedApk.mPackageName;
    }

    public void onCreate() {
        Typeface.setFlipFonts();
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration != null && configuration.FlipFont > 0 && this.mFlipfont != configuration.FlipFont) {
            Typeface.setFlipFonts();
            this.mFlipfont = configuration.FlipFont;
        }
        this.mCallbacksController.dispatchConfigurationChanged(configuration);
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCallbacksController.dispatchLowMemory();
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCallbacksController.dispatchTrimMemory(i);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.registerCallbacks(componentCallbacks);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {
        this.mCallbacksController.unregisterCallbacks(componentCallbacks);
    }

    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
        }
    }

    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
        }
    }

    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener onProvideAssistDataListener) {
        synchronized (this) {
            if (this.mAssistCallbacks == null) {
                this.mAssistCallbacks = new ArrayList<>();
            }
            this.mAssistCallbacks.add(onProvideAssistDataListener);
        }
    }

    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener onProvideAssistDataListener) {
        synchronized (this) {
            ArrayList<OnProvideAssistDataListener> arrayList = this.mAssistCallbacks;
            if (arrayList != null) {
                arrayList.remove(onProvideAssistDataListener);
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

    void dispatchActivityPreCreated(Activity activity, Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityCreated(Activity activity, Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityPostCreated(Activity activity, Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityPreStarted(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreStarted(activity);
            }
        }
    }

    void dispatchActivityStarted(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityStarted(activity);
            }
        }
    }

    void dispatchActivityPostStarted(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostStarted(activity);
            }
        }
    }

    void dispatchActivityPreResumed(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreResumed(activity);
            }
        }
    }

    void dispatchActivityResumed(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityResumed(activity);
            }
        }
    }

    void dispatchActivityPostResumed(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostResumed(activity);
            }
        }
    }

    void dispatchActivityPrePaused(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPrePaused(activity);
            }
        }
    }

    void dispatchActivityPaused(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPaused(activity);
            }
        }
    }

    void dispatchActivityPostPaused(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostPaused(activity);
            }
        }
    }

    void dispatchActivityPreStopped(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreStopped(activity);
            }
        }
    }

    void dispatchActivityStopped(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityStopped(activity);
            }
        }
    }

    void dispatchActivityPostStopped(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostStopped(activity);
            }
        }
    }

    void dispatchActivityPreSaveInstanceState(Activity activity, Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreSaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivityPostSaveInstanceState(Activity activity, Bundle bundle) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostSaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivityPreDestroyed(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPreDestroyed(activity);
            }
        }
    }

    void dispatchActivityDestroyed(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityDestroyed(activity);
            }
        }
    }

    void dispatchActivityPostDestroyed(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPostDestroyed(activity);
            }
        }
    }

    void dispatchActivityConfigurationChanged(Activity activity) {
        Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (Object obj : collectActivityLifecycleCallbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityConfigurationChanged(activity);
            }
        }
    }

    private Object[] collectActivityLifecycleCallbacks() {
        Object[] array;
        synchronized (this.mActivityLifecycleCallbacks) {
            array = this.mActivityLifecycleCallbacks.size() > 0 ? this.mActivityLifecycleCallbacks.toArray() : null;
        }
        return array;
    }

    void dispatchOnProvideAssistData(Activity activity, Bundle bundle) {
        synchronized (this) {
            ArrayList<OnProvideAssistDataListener> arrayList = this.mAssistCallbacks;
            if (arrayList == null) {
                return;
            }
            Object[] array = arrayList.toArray();
            if (array != null) {
                for (Object obj : array) {
                    ((OnProvideAssistDataListener) obj).onProvideAssistData(activity, bundle);
                }
            }
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public AutofillManager.AutofillClient getAutofillClient() {
        Activity activity;
        AutofillManager.AutofillClient autofillClient = super.getAutofillClient();
        if (autofillClient != null) {
            return autofillClient;
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "getAutofillClient(): null on super, trying to find activity thread");
        }
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        if (currentActivityThread == null) {
            return null;
        }
        int size = currentActivityThread.mActivities.size();
        for (int i = 0; i < size; i++) {
            ActivityThread.ActivityClientRecord valueAt = currentActivityThread.mActivities.valueAt(i);
            if (valueAt != null && (activity = valueAt.activity) != null) {
                if (valueAt.isTopResumedActivity) {
                    if (Helper.sVerbose) {
                        Log.v(TAG, "getAutofillClient(): found top resumed activity for " + this + ": " + activity);
                    }
                    return activity.getAutofillClient();
                }
                if (activity.getWindow().getDecorView().hasFocus()) {
                    if (Helper.sVerbose) {
                        Log.v(TAG, "getAutofillClient(): found focused activity for " + this + ": " + activity);
                    }
                    return activity.getAutofillClient();
                }
            }
        }
        if (Helper.sVerbose) {
            Log.v(TAG, "getAutofillClient(): none of the " + size + " activities on " + this + " are top resumed nor have focus");
        }
        return null;
    }
}
