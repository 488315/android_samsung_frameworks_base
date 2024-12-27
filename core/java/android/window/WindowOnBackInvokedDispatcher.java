package android.window;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import android.util.TypedValue;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.ImeBackAnimationController;
import android.view.MotionEvent;

import com.android.internal.R;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WindowOnBackInvokedDispatcher implements OnBackInvokedDispatcher {
    private static final boolean ALWAYS_ENFORCE_PREDICTIVE_BACK;
    private static final boolean ENABLE_PREDICTIVE_BACK;
    private static final boolean PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE;
    private static final String TAG = "WindowOnBackDispatcher";
    private static String sOwnerTag;
    private float mBackSwipeLinearThreshold;
    private Checker mChecker;
    private final Handler mHandler;
    private ImeBackAnimationController mImeBackAnimationController;
    private ImeOnBackInvokedDispatcher mImeDispatcher;
    private float mNonLinearProgressFactor;
    private IWindow mWindow;
    private IWindowSession mWindowSession;
    public final BackTouchTracker mTouchTracker = new BackTouchTracker();
    public final BackProgressAnimator mProgressAnimator = new BackProgressAnimator();
    private final HashMap<OnBackInvokedCallback, Integer> mAllCallbacks = new HashMap<>();
    public final TreeMap<Integer, ArrayList<OnBackInvokedCallback>> mOnBackInvokedCallbacks =
            new TreeMap<>();
    private final Object mLock = new Object();

    static {
        ENABLE_PREDICTIVE_BACK =
                SystemProperties.getInt("persist.wm.debug.predictive_back", 1) != 0;
        ALWAYS_ENFORCE_PREDICTIVE_BACK =
                SystemProperties.getInt("persist.wm.debug.predictive_back_always_enforce", 0) != 0;
        PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE =
                SystemProperties.getInt(
                                "persist.wm.debug.predictive_back_fallback_window_attribute", 0)
                        != 0;
        sOwnerTag = null;
    }

    public WindowOnBackInvokedDispatcher(Context context, Looper looper) {
        this.mChecker = new Checker(context);
        this.mHandler = new Handler(looper);
    }

    public void onMotionEvent(MotionEvent ev) {
        if (!isBackGestureInProgress() || ev == null || ev.getAction() != 2) {
            return;
        }
        this.mTouchTracker.update(ev.getX(), ev.getY(), Float.NaN, Float.NaN);
        if (this.mTouchTracker.shouldUpdateStartLocation()) {
            this.mTouchTracker.updateStartLocation();
        }
        if (!this.mProgressAnimator.isBackAnimationInProgress()) {
            return;
        }
        BackMotionEvent backEvent = this.mTouchTracker.createProgressEvent();
        this.mProgressAnimator.onBackProgressed(backEvent);
    }

    public void setOwnerTag(String tag) {
        sOwnerTag = tag;
    }

    public void attachToWindow(
            IWindowSession windowSession,
            IWindow window,
            ImeBackAnimationController imeBackAnimationController) {
        synchronized (this.mLock) {
            this.mWindowSession = windowSession;
            this.mWindow = window;
            this.mImeBackAnimationController = imeBackAnimationController;
            if (!this.mAllCallbacks.isEmpty()) {
                setTopOnBackInvokedCallback(getTopCallback());
            }
        }
    }

    public void detachFromWindow() {
        synchronized (this.mLock) {
            clear();
            this.mWindow = null;
            this.mWindowSession = null;
            this.mImeBackAnimationController = null;
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int priority, OnBackInvokedCallback callback) {
        if (this.mChecker.checkApplicationCallbackRegistration(priority, callback)) {
            registerOnBackInvokedCallbackUnchecked(callback, priority);
        }
    }

    public void registerOnBackInvokedCallbackUnchecked(
            OnBackInvokedCallback callback, int priority) {
        synchronized (this.mLock) {
            if (this.mImeDispatcher != null) {
                this.mImeDispatcher.registerOnBackInvokedCallback(priority, callback);
                return;
            }
            if (callback instanceof ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback) {
                if (!isOnBackInvokedCallbackEnabled()) {
                    return;
                }
                if ((callback
                                instanceof
                                ImeOnBackInvokedDispatcher.DefaultImeOnBackAnimationCallback)
                        && this.mImeBackAnimationController != null) {
                    callback = this.mImeBackAnimationController;
                }
            }
            if (!this.mOnBackInvokedCallbacks.containsKey(Integer.valueOf(priority))) {
                this.mOnBackInvokedCallbacks.put(Integer.valueOf(priority), new ArrayList<>());
            }
            ArrayList<OnBackInvokedCallback> callbacks =
                    this.mOnBackInvokedCallbacks.get(Integer.valueOf(priority));
            if (this.mAllCallbacks.containsKey(callback)) {
                Integer prevPriority = this.mAllCallbacks.get(callback);
                this.mOnBackInvokedCallbacks.get(prevPriority).remove(callback);
            }
            OnBackInvokedCallback previousTopCallback = getTopCallback();
            callbacks.add(callback);
            this.mAllCallbacks.put(callback, Integer.valueOf(priority));
            if (previousTopCallback == null
                    || (previousTopCallback != callback
                            && this.mAllCallbacks.get(previousTopCallback).intValue()
                                    <= priority)) {
                setTopOnBackInvokedCallback(callback);
            }
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(OnBackInvokedCallback callback) {
        synchronized (this.mLock) {
            if (this.mImeDispatcher != null) {
                this.mImeDispatcher.unregisterOnBackInvokedCallback(callback);
                return;
            }
            if (callback instanceof ImeOnBackInvokedDispatcher.DefaultImeOnBackAnimationCallback) {
                callback = this.mImeBackAnimationController;
            }
            if (this.mAllCallbacks.containsKey(callback)) {
                OnBackInvokedCallback previousTopCallback = getTopCallback();
                Integer priority = this.mAllCallbacks.get(callback);
                ArrayList<OnBackInvokedCallback> callbacks =
                        this.mOnBackInvokedCallbacks.get(priority);
                callbacks.remove(callback);
                if (callbacks.isEmpty()) {
                    this.mOnBackInvokedCallbacks.remove(priority);
                }
                this.mAllCallbacks.remove(callback);
                if (previousTopCallback == callback) {
                    sendCancelledIfInProgress(callback);
                    setTopOnBackInvokedCallback(getTopCallback());
                }
            }
        }
    }

    public boolean isBackGestureInProgress() {
        boolean isActive;
        synchronized (this.mLock) {
            isActive = this.mTouchTracker.isActive();
        }
        return isActive;
    }

    private void sendCancelledIfInProgress(OnBackInvokedCallback callback) {
        boolean isInProgress = this.mProgressAnimator.isBackAnimationInProgress();
        if (isInProgress && (callback instanceof OnBackAnimationCallback)) {
            OnBackAnimationCallback animatedCallback = (OnBackAnimationCallback) callback;
            animatedCallback.onBackCancelled();
        } else {
            Log.w(
                    TAG,
                    "sendCancelIfRunning: isInProgress=" + isInProgress + " callback=" + callback);
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerSystemOnBackInvokedCallback(OnBackInvokedCallback callback) {
        registerOnBackInvokedCallbackUnchecked(callback, -1);
    }

    public void clear() {
        synchronized (this.mLock) {
            if (this.mImeDispatcher != null) {
                this.mImeDispatcher.clear();
                this.mImeDispatcher = null;
            }
            if (!this.mAllCallbacks.isEmpty()) {
                OnBackInvokedCallback topCallback = getTopCallback();
                if (topCallback != null) {
                    sendCancelledIfInProgress(topCallback);
                } else {
                    Log.e(TAG, "There is no topCallback, even if mAllCallbacks is not empty");
                }
                setTopOnBackInvokedCallback(null);
            }
            Handler handler = this.mHandler;
            final BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
            Objects.requireNonNull(backProgressAnimator);
            handler.post(
                    new Runnable() { // from class:
                                     // android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackProgressAnimator.this.reset();
                        }
                    });
            this.mAllCallbacks.clear();
            this.mOnBackInvokedCallbacks.clear();
        }
    }

    private void setTopOnBackInvokedCallback(OnBackInvokedCallback callback) {
        if (this.mWindowSession == null || this.mWindow == null) {
            return;
        }
        OnBackInvokedCallbackInfo callbackInfo = null;
        if (callback != null) {
            try {
                int priority = this.mAllCallbacks.get(callback).intValue();
                IOnBackInvokedCallback iCallback =
                        new OnBackInvokedCallbackWrapper(
                                callback,
                                this.mTouchTracker,
                                this.mProgressAnimator,
                                this.mHandler);
                callbackInfo =
                        new OnBackInvokedCallbackInfo(
                                iCallback, priority, callback instanceof OnBackAnimationCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to set OnBackInvokedCallback to WM. Error: " + e);
                return;
            }
        }
        this.mWindowSession.setOnBackInvokedCallbackInfo(this.mWindow, callbackInfo);
    }

    public OnBackInvokedCallback getTopCallback() {
        synchronized (this.mLock) {
            if (this.mAllCallbacks.isEmpty()) {
                return null;
            }
            for (Integer priority : this.mOnBackInvokedCallbacks.descendingKeySet()) {
                ArrayList<OnBackInvokedCallback> callbacks =
                        this.mOnBackInvokedCallbacks.get(priority);
                if (!callbacks.isEmpty()) {
                    return callbacks.get(callbacks.size() - 1);
                }
            }
            return null;
        }
    }

    public void updateContext(Context context) {
        this.mChecker = new Checker(context);
        Resources res = context.getResources();
        this.mBackSwipeLinearThreshold =
                res.getDimension(R.dimen.navigation_edge_action_progress_threshold);
        TypedValue typedValue = new TypedValue();
        res.getValue(R.dimen.back_progress_non_linear_factor, typedValue, true);
        this.mNonLinearProgressFactor = typedValue.getFloat();
        onConfigurationChanged(context.getResources().getConfiguration());
    }

    public void onConfigurationChanged(Configuration configuration) {
        float maxDistance = configuration.windowConfiguration.getMaxBounds().width();
        float linearDistance = Math.min(maxDistance, this.mBackSwipeLinearThreshold);
        this.mTouchTracker.setProgressThresholds(
                linearDistance, maxDistance, this.mNonLinearProgressFactor);
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return isOnBackInvokedCallbackEnabled(this.mChecker.getContext());
    }

    public void dump(String prefix, final PrintWriter writer) {
        final String innerPrefix = prefix + "    ";
        writer.println(prefix + "WindowOnBackDispatcher:");
        synchronized (this.mLock) {
            if (this.mAllCallbacks.isEmpty()) {
                writer.println(prefix + "<None>");
                return;
            }
            writer.println(innerPrefix + "Top Callback: " + getTopCallback());
            writer.println(innerPrefix + "Callbacks: ");
            this.mAllCallbacks.forEach(
                    new BiConsumer() { // from class:
                                       // android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda2
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            writer.println(
                                    innerPrefix
                                            + "  Callback: "
                                            + ((OnBackInvokedCallback) obj)
                                            + " Priority="
                                            + ((Integer) obj2));
                        }
                    });
        }
    }

    static class OnBackInvokedCallbackWrapper extends IOnBackInvokedCallback.Stub {
        private final WeakReference<OnBackInvokedCallback> mCallback;
        private final Handler mHandler;
        private final BackProgressAnimator mProgressAnimator;
        private final BackTouchTracker mTouchTracker;

        OnBackInvokedCallbackWrapper(
                OnBackInvokedCallback callback,
                BackTouchTracker touchTracker,
                BackProgressAnimator progressAnimator,
                Handler handler) {
            this.mCallback = new WeakReference<>(callback);
            this.mTouchTracker = touchTracker;
            this.mProgressAnimator = progressAnimator;
            this.mHandler = handler;
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(final BackMotionEvent backEvent) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this
                                    .lambda$onBackStarted$0(backEvent);
                        }
                    });
        }

        public /* synthetic */ void lambda$onBackStarted$0(BackMotionEvent backEvent) {
            final OnBackAnimationCallback callback = getBackAnimationCallback();
            if (callback != null && this.mProgressAnimator.isBackAnimationInProgress()) {
                this.mProgressAnimator.reset();
            }
            this.mTouchTracker.setState(BackTouchTracker.TouchTrackerState.ACTIVE);
            this.mTouchTracker.setShouldUpdateStartLocation(true);
            this.mTouchTracker.setGestureStartLocation(
                    backEvent.getTouchX(), backEvent.getTouchY(), backEvent.getSwipeEdge());
            if (callback != null) {
                callback.onBackStarted(BackEvent.fromBackMotionEvent(backEvent));
                BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
                Objects.requireNonNull(callback);
                backProgressAnimator.onBackStarted(
                        backEvent,
                        new BackProgressAnimator
                                .ProgressCallback() { // from class:
                                                      // android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda5
                            @Override // android.window.BackProgressAnimator.ProgressCallback
                            public final void onProgressUpdate(BackEvent backEvent2) {
                                OnBackAnimationCallback.this.onBackProgressed(backEvent2);
                            }
                        });
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(final BackMotionEvent backEvent) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this
                                    .lambda$onBackProgressed$1(backEvent);
                        }
                    });
        }

        public /* synthetic */ void lambda$onBackProgressed$1(BackMotionEvent backEvent) {
            if (getBackAnimationCallback() != null) {
                this.mProgressAnimator.onBackProgressed(backEvent);
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this
                                    .lambda$onBackCancelled$2();
                        }
                    });
        }

        public /* synthetic */ void lambda$onBackCancelled$2() {
            final OnBackAnimationCallback callback = getBackAnimationCallback();
            this.mTouchTracker.reset();
            if (callback == null) {
                return;
            }
            BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
            Objects.requireNonNull(callback);
            backProgressAnimator.onBackCancelled(
                    new Runnable() { // from class:
                                     // android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            OnBackAnimationCallback.this.onBackCancelled();
                        }
                    });
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() throws RemoteException {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper.this
                                    .lambda$onBackInvoked$3();
                        }
                    });
        }

        public /* synthetic */ void lambda$onBackInvoked$3() {
            this.mTouchTracker.reset();
            boolean isInProgress = this.mProgressAnimator.isBackAnimationInProgress();
            OnBackInvokedCallback callback = this.mCallback.get();
            if (callback == null) {
                this.mProgressAnimator.reset();
                Log.d(
                        WindowOnBackInvokedDispatcher.TAG,
                        "Trying to call onBackInvoked() on a null callback reference.");
                return;
            }
            if ((callback instanceof OnBackAnimationCallback) && !isInProgress) {
                Log.w(
                        WindowOnBackInvokedDispatcher.TAG,
                        "ProgressAnimator was not in progress, skip onBackInvoked().");
                return;
            }
            OnBackAnimationCallback animationCallback = getBackAnimationCallback();
            if (animationCallback != null) {
                BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
                Objects.requireNonNull(callback);
                backProgressAnimator.onBackInvoked(
                        new ImeOnBackInvokedDispatcher$ImeOnBackInvokedCallbackWrapper$$ExternalSyntheticLambda1(
                                callback));
            } else {
                this.mProgressAnimator.reset();
                callback.onBackInvoked();
            }
            Log.d(
                    WindowOnBackInvokedDispatcher.TAG,
                    "onBackInvoked, owner="
                            + WindowOnBackInvokedDispatcher.sOwnerTag
                            + ", callback="
                            + callback);
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setTriggerBack(boolean triggerBack) throws RemoteException {
            this.mTouchTracker.setTriggerBack(triggerBack);
        }

        private OnBackAnimationCallback getBackAnimationCallback() {
            OnBackInvokedCallback callback = this.mCallback.get();
            if (callback instanceof OnBackAnimationCallback) {
                return (OnBackAnimationCallback) callback;
            }
            return null;
        }
    }

    public static boolean isOnBackInvokedCallbackEnabled(final Context context) {
        while ((context instanceof ContextWrapper) && !(context instanceof Activity)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        ActivityInfo activityInfo =
                context instanceof Activity ? ((Activity) context).getActivityInfo() : null;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return isOnBackInvokedCallbackEnabled(
                activityInfo,
                applicationInfo,
                new Supplier() { // from class:
                                 // android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return WindowOnBackInvokedDispatcher
                                .lambda$isOnBackInvokedCallbackEnabled$1(Context.this);
                    }
                });
    }

    static /* synthetic */ Context lambda$isOnBackInvokedCallbackEnabled$1(
            Context originalContext) {
        return originalContext;
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void setImeOnBackInvokedDispatcher(ImeOnBackInvokedDispatcher imeDispatcher) {
        this.mImeDispatcher = imeDispatcher;
        this.mImeDispatcher.setHandler(this.mHandler);
    }

    public boolean hasImeOnBackInvokedDispatcher() {
        return this.mImeDispatcher != null;
    }

    public static class Checker {
        private WeakReference<Context> mContext;

        public Checker(Context context) {
            this.mContext = new WeakReference<>(context);
        }

        public boolean checkApplicationCallbackRegistration(
                int priority, OnBackInvokedCallback callback) {
            if (!WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(getContext())
                    && !(callback instanceof CompatOnBackInvokedCallback)) {
                Log.w(
                        WindowOnBackInvokedDispatcher.TAG,
                        "OnBackInvokedCallback is not enabled for the application.\n"
                            + "Set 'android:enableOnBackInvokedCallback=\"true\"' in the"
                            + " application manifest.");
                return false;
            }
            if (priority < 0) {
                throw new IllegalArgumentException(
                        "Application registered OnBackInvokedCallback cannot have negative"
                            + " priority. Priority: "
                                + priority);
            }
            Objects.requireNonNull(callback);
            return true;
        }

        public Context getContext() {
            return this.mContext.get();
        }
    }

    public static boolean isOnBackInvokedCallbackEnabled(
            ActivityInfo activityInfo,
            ApplicationInfo applicationInfo,
            Supplier<Context> contextSupplier) {
        if (!ENABLE_PREDICTIVE_BACK) {
            return false;
        }
        if (ALWAYS_ENFORCE_PREDICTIVE_BACK) {
            return true;
        }
        if (activityInfo != null && activityInfo.hasOnBackInvokedCallbackEnabled()) {
            return activityInfo.isOnBackInvokedCallbackEnabled();
        }
        boolean requestsPredictiveBack = applicationInfo.isOnBackInvokedCallbackEnabled();
        if (requestsPredictiveBack) {
            return true;
        }
        if (PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE) {
            Context context = contextSupplier.get();
            boolean windowSwipeToDismiss = true;
            if (context != null) {
                TypedArray array = context.obtainStyledAttributes(new int[] {16843763});
                if (array.getIndexCount() > 0) {
                    windowSwipeToDismiss = array.getBoolean(0, true);
                }
                array.recycle();
            }
            return windowSwipeToDismiss;
        }
        return requestsPredictiveBack;
    }
}
