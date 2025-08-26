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
import android.view.ViewRootImpl;
import android.window.BackProgressAnimator;
import android.window.BackTouchTracker;
import android.window.IOnBackInvokedCallback;
import android.window.ImeOnBackInvokedDispatcher;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class WindowOnBackInvokedDispatcher implements OnBackInvokedDispatcher {
    private static final boolean ALWAYS_ENFORCE_PREDICTIVE_BACK;
    private static final boolean ENABLE_PREDICTIVE_BACK;
    private static final boolean PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE;
    private static final String TAG = "WindowOnBackDispatcher";
    private float mBackSwipeLinearThreshold;
    private Checker mChecker;
    private final Handler mHandler;
    private ImeBackAnimationController mImeBackAnimationController;
    private ImeOnBackInvokedDispatcher mImeDispatcher;
    private float mNonLinearProgressFactor;
    private ViewRootImpl mViewRoot;
    private IWindow mWindow;
    private IWindowSession mWindowSession;
    public final BackTouchTracker mTouchTracker = new BackTouchTracker();
    public final BackProgressAnimator mProgressAnimator = new BackProgressAnimator();
    private final HashMap<OnBackInvokedCallback, Integer> mAllCallbacks = new HashMap<>();
    public final TreeMap<Integer, ArrayList<OnBackInvokedCallback>> mOnBackInvokedCallbacks = new TreeMap<>();
    public OnBackInvokedCallback mSystemNavigationObserverCallback = null;
    private final Object mLock = new Object();

    static /* synthetic */ Context lambda$isOnBackInvokedCallbackEnabled$1(Context context) {
        return context;
    }

    static {
        ENABLE_PREDICTIVE_BACK = SystemProperties.getInt("persist.wm.debug.predictive_back", 1) != 0;
        ALWAYS_ENFORCE_PREDICTIVE_BACK = SystemProperties.getInt("persist.wm.debug.predictive_back_always_enforce", 0) != 0;
        PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE = SystemProperties.getInt("persist.wm.debug.predictive_back_fallback_window_attribute", 0) != 0;
    }

    public WindowOnBackInvokedDispatcher(Context context, Looper looper) {
        this.mChecker = new Checker(context);
        this.mHandler = new Handler(looper);
    }

    public void onMotionEvent(MotionEvent motionEvent) {
        if (isBackGestureInProgress() && motionEvent != null && motionEvent.getAction() == 2) {
            this.mTouchTracker.update(motionEvent.getX(), motionEvent.getY());
            if (this.mTouchTracker.shouldUpdateStartLocation()) {
                this.mTouchTracker.updateStartLocation();
            }
            if (this.mProgressAnimator.isBackAnimationInProgress()) {
                this.mProgressAnimator.onBackProgressed(this.mTouchTracker.createProgressEvent());
            }
        }
    }

    public void attachToWindow(IWindowSession iWindowSession, IWindow iWindow, ViewRootImpl viewRootImpl, ImeBackAnimationController imeBackAnimationController) {
        synchronized (this.mLock) {
            this.mWindowSession = iWindowSession;
            this.mWindow = iWindow;
            this.mViewRoot = viewRootImpl;
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
            this.mViewRoot = null;
            this.mImeBackAnimationController = null;
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int i, OnBackInvokedCallback onBackInvokedCallback) {
        if (this.mChecker.checkApplicationCallbackRegistration(i, onBackInvokedCallback)) {
            registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, i);
        }
    }

    private void registerSystemNavigationObserverCallback(OnBackInvokedCallback onBackInvokedCallback) {
        synchronized (this.mLock) {
            if (this.mAllCallbacks.containsKey(onBackInvokedCallback)) {
                removeCallbackInternal(onBackInvokedCallback);
            }
            this.mSystemNavigationObserverCallback = onBackInvokedCallback;
        }
    }

    public void registerOnBackInvokedCallbackUnchecked(OnBackInvokedCallback onBackInvokedCallback, int i) {
        ImeBackAnimationController imeBackAnimationController;
        synchronized (this.mLock) {
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = this.mImeDispatcher;
            if (imeOnBackInvokedDispatcher != null) {
                imeOnBackInvokedDispatcher.registerOnBackInvokedCallback(i, onBackInvokedCallback);
                return;
            }
            if (Flags.predictiveBackPrioritySystemNavigationObserver() && Flags.predictiveBackSystemOverrideCallback() && i == -2 && (onBackInvokedCallback instanceof SystemOverrideOnBackInvokedCallback)) {
                Log.e(TAG, "System override callbacks cannot be registered to NAVIGATION_OBSERVER");
                return;
            }
            if (Flags.predictiveBackPrioritySystemNavigationObserver() && i == -2) {
                registerSystemNavigationObserverCallback(onBackInvokedCallback);
                return;
            }
            if ((onBackInvokedCallback instanceof ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback) && (onBackInvokedCallback instanceof ImeOnBackInvokedDispatcher.DefaultImeOnBackAnimationCallback) && (imeBackAnimationController = this.mImeBackAnimationController) != null) {
                onBackInvokedCallback = imeBackAnimationController;
            }
            if (!this.mOnBackInvokedCallbacks.containsKey(Integer.valueOf(i))) {
                this.mOnBackInvokedCallbacks.put(Integer.valueOf(i), new ArrayList<>());
            }
            ArrayList<OnBackInvokedCallback> arrayList = this.mOnBackInvokedCallbacks.get(Integer.valueOf(i));
            if (this.mAllCallbacks.containsKey(onBackInvokedCallback)) {
                this.mOnBackInvokedCallbacks.get(this.mAllCallbacks.get(onBackInvokedCallback)).remove(onBackInvokedCallback);
            }
            if (this.mSystemNavigationObserverCallback == onBackInvokedCallback) {
                this.mSystemNavigationObserverCallback = null;
            }
            OnBackInvokedCallback topCallback = getTopCallback();
            arrayList.add(onBackInvokedCallback);
            this.mAllCallbacks.put(onBackInvokedCallback, Integer.valueOf(i));
            if (topCallback == null || (topCallback != onBackInvokedCallback && this.mAllCallbacks.get(topCallback).intValue() <= i)) {
                setTopOnBackInvokedCallback(onBackInvokedCallback);
            }
        }
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback) {
        synchronized (this.mLock) {
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = this.mImeDispatcher;
            if (imeOnBackInvokedDispatcher != null) {
                imeOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
                return;
            }
            if (this.mSystemNavigationObserverCallback == onBackInvokedCallback) {
                this.mSystemNavigationObserverCallback = null;
                return;
            }
            if (onBackInvokedCallback instanceof ImeOnBackInvokedDispatcher.DefaultImeOnBackAnimationCallback) {
                onBackInvokedCallback = this.mImeBackAnimationController;
            }
            if (this.mAllCallbacks.containsKey(onBackInvokedCallback)) {
                removeCallbackInternal(onBackInvokedCallback);
            }
        }
    }

    private void removeCallbackInternal(OnBackInvokedCallback onBackInvokedCallback) {
        OnBackInvokedCallback topCallback = getTopCallback();
        Integer num = this.mAllCallbacks.get(onBackInvokedCallback);
        ArrayList<OnBackInvokedCallback> arrayList = this.mOnBackInvokedCallbacks.get(num);
        arrayList.remove(onBackInvokedCallback);
        if (arrayList.isEmpty()) {
            this.mOnBackInvokedCallbacks.remove(num);
        }
        this.mAllCallbacks.remove(onBackInvokedCallback);
        if (topCallback == onBackInvokedCallback) {
            this.mProgressAnimator.removeOnBackCancelledFinishCallback();
            this.mProgressAnimator.removeOnBackInvokedFinishCallback();
            sendCancelledIfInProgress(onBackInvokedCallback);
            Handler handler = this.mHandler;
            BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
            Objects.requireNonNull(backProgressAnimator);
            handler.post(new WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda3(backProgressAnimator));
            setTopOnBackInvokedCallback(getTopCallback());
        }
    }

    public boolean isBackGestureInProgress() {
        boolean zIsActive;
        synchronized (this.mLock) {
            zIsActive = this.mTouchTracker.isActive();
        }
        return zIsActive;
    }

    private void sendCancelledIfInProgress(OnBackInvokedCallback onBackInvokedCallback) {
        boolean zIsBackAnimationInProgress = this.mProgressAnimator.isBackAnimationInProgress();
        if (zIsBackAnimationInProgress && (onBackInvokedCallback instanceof OnBackAnimationCallback)) {
            ((OnBackAnimationCallback) onBackInvokedCallback).onBackCancelled();
            return;
        }
        Log.w(TAG, "sendCancelIfRunning: isInProgress=" + zIsBackAnimationInProgress + " callback=" + onBackInvokedCallback);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerSystemOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback) {
        registerOnBackInvokedCallbackUnchecked(onBackInvokedCallback, -1);
    }

    public void clear() {
        synchronized (this.mLock) {
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = this.mImeDispatcher;
            if (imeOnBackInvokedDispatcher != null) {
                imeOnBackInvokedDispatcher.clear();
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
            BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
            Objects.requireNonNull(backProgressAnimator);
            handler.post(new WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda3(backProgressAnimator));
            this.mAllCallbacks.clear();
            this.mOnBackInvokedCallbacks.clear();
            this.mSystemNavigationObserverCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean callOnKeyPreIme() {
        if (this.mViewRoot == null || isOnBackInvokedCallbackEnabled()) {
            return false;
        }
        return this.mViewRoot.injectBackKeyEvents(true);
    }

    public void tryInvokeSystemNavigationObserverCallback() {
        OnBackInvokedCallback topCallback = getTopCallback();
        Integer orDefault = this.mAllCallbacks.getOrDefault(topCallback, null);
        boolean z = topCallback instanceof SystemOverrideOnBackInvokedCallback;
        if ((orDefault == null || orDefault.intValue() != -1) && !z) {
            return;
        }
        invokeSystemNavigationObserverCallback();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeSystemNavigationObserverCallback() {
        OnBackInvokedCallback onBackInvokedCallback = this.mSystemNavigationObserverCallback;
        if (onBackInvokedCallback != null) {
            onBackInvokedCallback.onBackInvoked();
        }
    }

    private void setTopOnBackInvokedCallback(OnBackInvokedCallback onBackInvokedCallback) {
        OnBackInvokedCallbackInfo onBackInvokedCallbackInfo;
        if (this.mWindowSession == null || this.mWindow == null) {
            return;
        }
        if (onBackInvokedCallback != null) {
            try {
                int iIntValue = this.mAllCallbacks.get(onBackInvokedCallback).intValue();
                int iOverrideBehavior = onBackInvokedCallback instanceof SystemOverrideOnBackInvokedCallback ? ((SystemOverrideOnBackInvokedCallback) onBackInvokedCallback).overrideBehavior() : 0;
                onBackInvokedCallbackInfo = new OnBackInvokedCallbackInfo(new OnBackInvokedCallbackWrapper(onBackInvokedCallback, this.mTouchTracker, this.mProgressAnimator, this.mHandler, new BooleanSupplier() { // from class: android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda0
                    @Override // java.util.function.BooleanSupplier
                    public final boolean getAsBoolean() {
                        return this.f$0.callOnKeyPreIme();
                    }
                }, new Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.invokeSystemNavigationObserverCallback();
                    }
                }, iIntValue == -1 || iOverrideBehavior != 0), iIntValue, onBackInvokedCallback instanceof OnBackAnimationCallback, iOverrideBehavior);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to set OnBackInvokedCallback to WM. Error: " + e);
                return;
            }
        } else {
            onBackInvokedCallbackInfo = null;
        }
        this.mWindowSession.setOnBackInvokedCallbackInfo(this.mWindow, onBackInvokedCallbackInfo);
    }

    public OnBackInvokedCallback getTopCallback() {
        synchronized (this.mLock) {
            if (this.mAllCallbacks.isEmpty()) {
                return null;
            }
            Iterator<Integer> it = this.mOnBackInvokedCallbacks.descendingKeySet().iterator();
            while (it.hasNext()) {
                ArrayList<OnBackInvokedCallback> arrayList = this.mOnBackInvokedCallbacks.get(it.next());
                if (!arrayList.isEmpty()) {
                    return arrayList.get(arrayList.size() - 1);
                }
            }
            return null;
        }
    }

    public void updateContext(Context context) throws Resources.NotFoundException {
        this.mChecker = new Checker(context);
        Resources resources = context.getResources();
        this.mBackSwipeLinearThreshold = resources.getDimension(R.dimen.navigation_edge_action_progress_threshold);
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.back_progress_non_linear_factor, typedValue, true);
        this.mNonLinearProgressFactor = typedValue.getFloat();
        onConfigurationChanged(context.getResources().getConfiguration());
    }

    public void onConfigurationChanged(Configuration configuration) {
        float fWidth = configuration.windowConfiguration.getMaxBounds().width();
        this.mTouchTracker.setProgressThresholds(Math.min(fWidth, this.mBackSwipeLinearThreshold), fWidth, this.mNonLinearProgressFactor);
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        Context context = this.mChecker.getContext();
        if (context == null) {
            Log.w(TAG, "OnBackInvokedCallback is disabled, host context is removed!");
            return false;
        }
        return isOnBackInvokedCallbackEnabled(context);
    }

    public void dump(String str, final PrintWriter printWriter) {
        final String str2 = str + "    ";
        printWriter.println(str + "WindowOnBackDispatcher:");
        synchronized (this.mLock) {
            if (this.mAllCallbacks.isEmpty()) {
                printWriter.println(str + "<None>");
                return;
            }
            printWriter.println(str2 + "Top Callback: " + getTopCallback());
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("Callbacks: ");
            printWriter.println(sb.toString());
            this.mAllCallbacks.forEach(new BiConsumer() { // from class: android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    printWriter.println(str2 + "  Callback: " + ((OnBackInvokedCallback) obj) + " Priority=" + ((Integer) obj2));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnBackInvokedCallbackWrapper extends IOnBackInvokedCallback.Stub {
        private final WeakReference<OnBackInvokedCallback> mCallback;
        private final Handler mHandler;
        private final boolean mIsSystemCallback;
        private final BooleanSupplier mOnKeyPreIme;
        private final BackProgressAnimator mProgressAnimator;
        private final Runnable mSystemNavigationObserverCallbackRunnable;
        private final BackTouchTracker mTouchTracker;

        @Override // android.window.IOnBackInvokedCallback
        public void setHandoffHandler(IBackAnimationHandoffHandler iBackAnimationHandoffHandler) {
        }

        OnBackInvokedCallbackWrapper(OnBackInvokedCallback onBackInvokedCallback, BackTouchTracker backTouchTracker, BackProgressAnimator backProgressAnimator, Handler handler, BooleanSupplier booleanSupplier, Runnable runnable, boolean z) {
            this.mCallback = new WeakReference<>(onBackInvokedCallback);
            this.mTouchTracker = backTouchTracker;
            this.mProgressAnimator = backProgressAnimator;
            this.mHandler = handler;
            this.mOnKeyPreIme = booleanSupplier;
            this.mSystemNavigationObserverCallbackRunnable = runnable;
            this.mIsSystemCallback = z;
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(final BackMotionEvent backMotionEvent) {
            this.mHandler.post(new Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBackStarted$0(backMotionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackStarted$0(BackMotionEvent backMotionEvent) {
            final OnBackAnimationCallback backAnimationCallback = getBackAnimationCallback();
            if (backAnimationCallback != null && this.mProgressAnimator.isBackAnimationInProgress()) {
                this.mProgressAnimator.reset();
            }
            this.mTouchTracker.setState(BackTouchTracker.TouchTrackerState.ACTIVE);
            this.mTouchTracker.setShouldUpdateStartLocation(true);
            this.mTouchTracker.setGestureStartLocation(backMotionEvent.getTouchX(), backMotionEvent.getTouchY(), backMotionEvent.getSwipeEdge());
            if (backAnimationCallback != null) {
                backAnimationCallback.onBackStarted(BackEvent.fromBackMotionEvent(backMotionEvent));
                BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
                Objects.requireNonNull(backAnimationCallback);
                backProgressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda6
                    @Override // android.window.BackProgressAnimator.ProgressCallback
                    public final void onProgressUpdate(BackEvent backEvent) {
                        backAnimationCallback.onBackProgressed(backEvent);
                    }
                });
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(final BackMotionEvent backMotionEvent) {
            this.mHandler.post(new Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBackProgressed$1(backMotionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackProgressed$1(BackMotionEvent backMotionEvent) {
            if (getBackAnimationCallback() != null) {
                this.mProgressAnimator.onBackProgressed(backMotionEvent);
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() {
            this.mHandler.post(new Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBackCancelled$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackCancelled$2() {
            OnBackAnimationCallback backAnimationCallback = getBackAnimationCallback();
            this.mTouchTracker.reset();
            if (backAnimationCallback == null) {
                return;
            }
            BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
            Objects.requireNonNull(backAnimationCallback);
            backProgressAnimator.onBackCancelled(new WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda4(backAnimationCallback));
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() throws RemoteException {
            this.mHandler.post(new Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBackInvoked$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackInvoked$4() {
            this.mTouchTracker.reset();
            if (consumedByOnKeyPreIme()) {
                return;
            }
            boolean zIsBackAnimationInProgress = this.mProgressAnimator.isBackAnimationInProgress();
            final OnBackInvokedCallback onBackInvokedCallback = this.mCallback.get();
            if (onBackInvokedCallback == null) {
                this.mProgressAnimator.reset();
                Log.d(WindowOnBackInvokedDispatcher.TAG, "Trying to call onBackInvoked() on a null callback reference.");
                return;
            }
            if ((onBackInvokedCallback instanceof OnBackAnimationCallback) && !zIsBackAnimationInProgress) {
                Log.w(WindowOnBackInvokedDispatcher.TAG, "ProgressAnimator was not in progress, skip onBackInvoked().");
                return;
            }
            if (getBackAnimationCallback() != null && !(onBackInvokedCallback instanceof ImeBackAnimationController) && !Flags.predictiveBackTimestampApi()) {
                this.mProgressAnimator.onBackInvoked(new Runnable() { // from class: android.window.WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onBackInvoked$3(onBackInvokedCallback);
                    }
                });
            } else {
                this.mProgressAnimator.reset();
                if (this.mIsSystemCallback) {
                    this.mSystemNavigationObserverCallbackRunnable.run();
                }
                onBackInvokedCallback.onBackInvoked();
            }
            if (CoreRune.FW_PREDICTIVE_BACK_ANIM_LOG) {
                Log.d(WindowOnBackInvokedDispatcher.TAG, "onBackInvoked, callback=" + onBackInvokedCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackInvoked$3(OnBackInvokedCallback onBackInvokedCallback) {
            if (this.mIsSystemCallback) {
                this.mSystemNavigationObserverCallbackRunnable.run();
            }
            onBackInvokedCallback.onBackInvoked();
        }

        private boolean consumedByOnKeyPreIme() {
            OnBackInvokedCallback onBackInvokedCallback = this.mCallback.get();
            if (!(onBackInvokedCallback instanceof ImeBackAnimationController) && !(onBackInvokedCallback instanceof ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback)) {
                return false;
            }
            try {
                if (!this.mOnKeyPreIme.getAsBoolean()) {
                    return false;
                }
                OnBackAnimationCallback backAnimationCallback = getBackAnimationCallback();
                if (backAnimationCallback == null) {
                    return true;
                }
                BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
                Objects.requireNonNull(backAnimationCallback);
                backProgressAnimator.onBackCancelled(new WindowOnBackInvokedDispatcher$OnBackInvokedCallbackWrapper$$ExternalSyntheticLambda4(backAnimationCallback));
                return true;
            } catch (Exception e) {
                Log.d(WindowOnBackInvokedDispatcher.TAG, "Failed to call onKeyPreIme", e);
                return false;
            }
        }

        @Override // android.window.IOnBackInvokedCallback
        public void setTriggerBack(boolean z) throws RemoteException {
            this.mTouchTracker.setTriggerBack(z);
        }

        private OnBackAnimationCallback getBackAnimationCallback() {
            OnBackInvokedCallback onBackInvokedCallback = this.mCallback.get();
            if (onBackInvokedCallback instanceof OnBackAnimationCallback) {
                return (OnBackAnimationCallback) onBackInvokedCallback;
            }
            return null;
        }
    }

    public static boolean isOnBackInvokedCallbackEnabled(final Context context) {
        Context baseContext = context;
        while ((baseContext instanceof ContextWrapper) && !(baseContext instanceof Activity)) {
            baseContext = ((ContextWrapper) baseContext).getBaseContext();
        }
        return isOnBackInvokedCallbackEnabled(baseContext instanceof Activity ? ((Activity) baseContext).getActivityInfo() : null, baseContext.getApplicationInfo(), new Supplier() { // from class: android.window.WindowOnBackInvokedDispatcher$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return WindowOnBackInvokedDispatcher.lambda$isOnBackInvokedCallbackEnabled$1(context);
            }
        });
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void setImeOnBackInvokedDispatcher(ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        this.mImeDispatcher = imeOnBackInvokedDispatcher;
        imeOnBackInvokedDispatcher.setHandler(this.mHandler);
    }

    public boolean hasImeOnBackInvokedDispatcher() {
        return this.mImeDispatcher != null;
    }

    public static class Checker {
        private WeakReference<Context> mContext;

        public Checker(Context context) {
            this.mContext = new WeakReference<>(context);
        }

        public boolean checkApplicationCallbackRegistration(int i, OnBackInvokedCallback onBackInvokedCallback) {
            Context context = getContext();
            if (context == null) {
                Log.w(WindowOnBackInvokedDispatcher.TAG, "OnBackInvokedCallback is disabled, host context is removed!");
                return false;
            }
            if (!WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(context) && !(onBackInvokedCallback instanceof CompatOnBackInvokedCallback)) {
                Log.w(WindowOnBackInvokedDispatcher.TAG, "OnBackInvokedCallback is not enabled for the application.\nSet 'android:enableOnBackInvokedCallback=\"true\"' in the application manifest.");
                return false;
            }
            if (Flags.predictiveBackPrioritySystemNavigationObserver()) {
                if (i < 0 && i != -2) {
                    throw new IllegalArgumentException("Application registered OnBackInvokedCallback cannot have negative priority. Priority: " + i);
                }
            } else if (i < 0) {
                throw new IllegalArgumentException("Application registered OnBackInvokedCallback cannot have negative priority. Priority: " + i);
            }
            Objects.requireNonNull(onBackInvokedCallback);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Context getContext() {
            return this.mContext.get();
        }
    }

    public static boolean isOnBackInvokedCallbackEnabled(ActivityInfo activityInfo, ApplicationInfo applicationInfo, Supplier<Context> supplier) {
        if (!ENABLE_PREDICTIVE_BACK) {
            return false;
        }
        if (ALWAYS_ENFORCE_PREDICTIVE_BACK) {
            return true;
        }
        if (activityInfo != null && activityInfo.hasOnBackInvokedCallbackEnabled()) {
            return activityInfo.isOnBackInvokedCallbackEnabled();
        }
        boolean zIsOnBackInvokedCallbackEnabled = applicationInfo.isOnBackInvokedCallbackEnabled();
        if (zIsOnBackInvokedCallbackEnabled) {
            return true;
        }
        if (!PREDICTIVE_BACK_FALLBACK_WINDOW_ATTRIBUTE) {
            return zIsOnBackInvokedCallbackEnabled;
        }
        Context context = supplier.get();
        if (context != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843763});
            z = typedArrayObtainStyledAttributes.getIndexCount() > 0 ? typedArrayObtainStyledAttributes.getBoolean(0, true) : true;
            typedArrayObtainStyledAttributes.recycle();
        }
        return z;
    }
}
