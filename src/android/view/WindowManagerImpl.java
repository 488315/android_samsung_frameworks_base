package android.view;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Region;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.window.ITaskFpsCallback;
import android.window.InputTransferToken;
import android.window.TaskFpsCallback;
import android.window.TrustedPresentationThresholds;
import android.window.WindowMetricsController;
import android.window.WindowProvider;
import android.window.WindowProviderService;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes4.dex */
public class WindowManagerImpl implements WindowManager {
    private static final String TAG = "WindowManager";
    public final Context mContext;
    private IBinder mDefaultToken;
    private final WindowManagerGlobal mGlobal;
    private final ArrayList<OnFpsCallbackListenerProxy> mOnFpsCallbackListenerProxies;
    private final Window mParentWindow;
    private final IBinder mWindowContextToken;
    private final WindowMetricsController mWindowMetricsController;

    public WindowManagerImpl(Context context) {
        this(context, null, null);
    }

    public WindowManagerImpl(Context context, Window window, IBinder iBinder) {
        this.mGlobal = WindowManagerGlobal.getInstance();
        this.mOnFpsCallbackListenerProxies = new ArrayList<>();
        this.mContext = context;
        this.mParentWindow = window;
        this.mWindowContextToken = iBinder;
        this.mWindowMetricsController = new WindowMetricsController(context);
    }

    public WindowManagerImpl createLocalWindowManager(Window window) {
        return new WindowManagerImpl(this.mContext, window, this.mWindowContextToken);
    }

    public WindowManagerImpl createPresentationWindowManager(Context context) {
        return new WindowManagerImpl(context, this.mParentWindow, this.mWindowContextToken);
    }

    public static WindowManager createWindowContextWindowManager(Context context) {
        return new WindowManagerImpl(context, null, context.getWindowContextToken());
    }

    public void setDefaultToken(IBinder iBinder) {
        this.mDefaultToken = iBinder;
    }

    @Override // android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        applyTokens(layoutParams);
        this.mGlobal.addView(view, layoutParams, this.mContext.getDisplayNoVerify(), this.mParentWindow, this.mContext.getUserId());
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
        applyTokens(layoutParams);
        this.mGlobal.updateViewLayout(view, layoutParams);
    }

    private void applyTokens(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
        assertWindowContextTypeMatches(layoutParams2.type);
        if (this.mDefaultToken != null && this.mParentWindow == null && layoutParams2.token == null) {
            layoutParams2.token = this.mDefaultToken;
        }
        layoutParams2.mWindowContextToken = this.mWindowContextToken;
    }

    private void assertWindowContextTypeMatches(int i) {
        Object obj = this.mContext;
        if (obj instanceof WindowProvider) {
            if (i < 1000 || i > 1999) {
                WindowProvider windowProvider = (WindowProvider) obj;
                if (windowProvider.getWindowType() == i) {
                    return;
                }
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Window type mismatch. Window Context's window type is " + windowProvider.getWindowType() + ", while LayoutParams' type is set to " + i + ". Please create another Window Context via createWindowContext(getDisplay(), " + i + ", null) to add window with type:" + i);
                if (!WindowProviderService.isWindowProviderService(windowProvider.getWindowContextOptions())) {
                    throw illegalArgumentException;
                }
                StrictMode.onIncorrectContextUsed("WindowContext's window type must match type in WindowManager.LayoutParams", illegalArgumentException);
            }
        }
    }

    @Override // android.view.ViewManager
    public void removeView(View view) {
        this.mGlobal.removeView(view, false);
    }

    @Override // android.view.WindowManager
    public void removeViewImmediate(View view) {
        this.mGlobal.removeView(view, true);
    }

    @Override // android.view.WindowManager
    public void requestAppKeyboardShortcuts(final WindowManager.KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().requestAppKeyboardShortcuts(new IResultReceiver.Stub(this) { // from class: android.view.WindowManagerImpl.1
                @Override // com.android.internal.os.IResultReceiver
                public void send(int i2, Bundle bundle) throws RemoteException {
                    keyboardShortcutsReceiver.onKeyboardShortcutsReceived(bundle.getParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, KeyboardShortcutGroup.class));
                }
            }, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public KeyboardShortcutGroup getApplicationLaunchKeyboardShortcuts(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().getApplicationLaunchKeyboardShortcuts(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public void requestImeKeyboardShortcuts(final WindowManager.KeyboardShortcutsReceiver keyboardShortcutsReceiver, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().requestImeKeyboardShortcuts(new IResultReceiver.Stub(this) { // from class: android.view.WindowManagerImpl.2
                @Override // com.android.internal.os.IResultReceiver
                public void send(int i2, Bundle bundle) throws RemoteException {
                    keyboardShortcutsReceiver.onKeyboardShortcutsReceived(bundle.getParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, KeyboardShortcutGroup.class));
                }
            }, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public Display getDefaultDisplay() {
        return this.mContext.getDisplayNoVerify();
    }

    @Override // android.view.WindowManager
    public Region getCurrentImeTouchRegion() {
        try {
            return WindowManagerGlobal.getWindowManagerService().getCurrentImeTouchRegion();
        } catch (RemoteException unused) {
            return null;
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowWithInsecureKeyguard(int i, boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowWithInsecureKeyguard(i, z);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowSystemDecors(int i, boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowSystemDecors(i, z);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.view.WindowManager
    public boolean shouldShowSystemDecors(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().shouldShowSystemDecors(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public boolean isEligibleForDesktopMode(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().isEligibleForDesktopMode(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void setDisplayImePolicy(int i, int i2) {
        try {
            WindowManagerGlobal.getWindowManagerService().setDisplayImePolicy(i, i2);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.view.WindowManager
    public int getDisplayImePolicy(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().getDisplayImePolicy(i);
        } catch (RemoteException unused) {
            return 1;
        }
    }

    @Override // android.view.WindowManager
    public boolean isGlobalKey(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().isGlobalKey(i);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public WindowMetrics getCurrentWindowMetrics() {
        return this.mWindowMetricsController.getCurrentWindowMetrics();
    }

    @Override // android.view.WindowManager
    public WindowMetrics getMaximumWindowMetrics() {
        return this.mWindowMetricsController.getMaximumWindowMetrics();
    }

    @Override // android.view.WindowManager
    public Set<WindowMetrics> getPossibleMaximumWindowMetrics(int i) {
        return this.mWindowMetricsController.getPossibleMaximumWindowMetrics(i);
    }

    @Override // android.view.WindowManager
    public void holdLock(IBinder iBinder, int i) {
        try {
            WindowManagerGlobal.getWindowManagerService().holdLock(iBinder, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public boolean isCrossWindowBlurEnabled() {
        return CrossWindowBlurListeners.getInstance().isCrossWindowBlurEnabled();
    }

    @Override // android.view.WindowManager
    public void addCrossWindowBlurEnabledListener(Consumer<Boolean> consumer) {
        addCrossWindowBlurEnabledListener(this.mContext.getMainExecutor(), consumer);
    }

    @Override // android.view.WindowManager
    public void addCrossWindowBlurEnabledListener(Executor executor, Consumer<Boolean> consumer) {
        CrossWindowBlurListeners.getInstance().addListener(executor, consumer);
    }

    @Override // android.view.WindowManager
    public void removeCrossWindowBlurEnabledListener(Consumer<Boolean> consumer) {
        CrossWindowBlurListeners.getInstance().removeListener(consumer);
    }

    @Override // android.view.WindowManager
    public void addProposedRotationListener(Executor executor, IntConsumer intConsumer) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(intConsumer, "listener must not be null");
        IBinder token = Context.getToken(this.mContext);
        if (token == null) {
            throw new UnsupportedOperationException("The context of this window manager instance must be a UI context, e.g. an Activity or a Context created by Context#createWindowContext()");
        }
        this.mGlobal.registerProposedRotationListener(token, executor, intConsumer);
    }

    @Override // android.view.WindowManager
    public void removeProposedRotationListener(IntConsumer intConsumer) {
        this.mGlobal.unregisterProposedRotationListener(Context.getToken(this.mContext), intConsumer);
    }

    @Override // android.view.WindowManager
    public boolean isTaskSnapshotSupported() {
        try {
            return WindowManagerGlobal.getWindowManagerService().isTaskSnapshotSupported();
        } catch (RemoteException unused) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void registerTaskFpsCallback(int i, Executor executor, TaskFpsCallback taskFpsCallback) {
        OnFpsCallbackListenerProxy onFpsCallbackListenerProxy = new OnFpsCallbackListenerProxy(executor, taskFpsCallback);
        try {
            WindowManagerGlobal.getWindowManagerService().registerTaskFpsCallback(i, onFpsCallbackListenerProxy);
            synchronized (this.mOnFpsCallbackListenerProxies) {
                this.mOnFpsCallbackListenerProxies.add(onFpsCallbackListenerProxy);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public void unregisterTaskFpsCallback(TaskFpsCallback taskFpsCallback) {
        synchronized (this.mOnFpsCallbackListenerProxies) {
            Iterator<OnFpsCallbackListenerProxy> it = this.mOnFpsCallbackListenerProxies.iterator();
            while (it.hasNext()) {
                OnFpsCallbackListenerProxy next = it.next();
                if (next.mCallback == taskFpsCallback) {
                    try {
                        WindowManagerGlobal.getWindowManagerService().unregisterTaskFpsCallback(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class OnFpsCallbackListenerProxy extends ITaskFpsCallback.Stub {
        private final TaskFpsCallback mCallback;
        private final Executor mExecutor;

        private OnFpsCallbackListenerProxy(Executor executor, TaskFpsCallback taskFpsCallback) {
            this.mExecutor = executor;
            this.mCallback = taskFpsCallback;
        }

        @Override // android.window.ITaskFpsCallback
        public void onFpsReported(final float f) {
            this.mExecutor.execute(new Runnable() { // from class: android.view.WindowManagerImpl$OnFpsCallbackListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerImpl.OnFpsCallbackListenerProxy.this.lambda$onFpsReported$0(f);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFpsReported$0(float f) {
            this.mCallback.onFpsReported(f);
        }
    }

    @Override // android.view.WindowManager
    public Bitmap snapshotTaskForRecents(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().snapshotTaskForRecents(i);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    @Override // android.view.WindowManager
    public IBinder getDefaultToken() {
        return this.mDefaultToken;
    }

    @Override // android.view.WindowManager
    public List<ComponentName> notifyScreenshotListeners(int i) {
        try {
            return List.copyOf(WindowManagerGlobal.getWindowManagerService().notifyScreenshotListeners(i));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public boolean replaceContentOnDisplayWithMirror(int i, Window window) {
        View peekDecorView = window.peekDecorView();
        if (peekDecorView == null) {
            Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's decorView was null.");
            return false;
        }
        ViewRootImpl viewRootImpl = peekDecorView.getViewRootImpl();
        if (viewRootImpl == null) {
            Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's viewRootImpl was null.");
            return false;
        }
        SurfaceControl surfaceControl = viewRootImpl.getSurfaceControl();
        if (!surfaceControl.isValid()) {
            Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's SC is invalid.");
            return false;
        }
        return replaceContentOnDisplayWithSc(i, SurfaceControl.mirrorSurface(surfaceControl));
    }

    @Override // android.view.WindowManager
    public boolean replaceContentOnDisplayWithSc(int i, SurfaceControl surfaceControl) {
        if (!surfaceControl.isValid()) {
            Log.e(TAG, "replaceContentOnDisplayWithSc: Invalid SC.");
            return false;
        }
        try {
            return WindowManagerGlobal.getWindowManagerService().replaceContentOnDisplay(i, surfaceControl);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void registerTrustedPresentationListener(IBinder iBinder, TrustedPresentationThresholds trustedPresentationThresholds, Executor executor, Consumer<Boolean> consumer) {
        Objects.requireNonNull(iBinder, "window must not be null");
        Objects.requireNonNull(trustedPresentationThresholds, "thresholds must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(consumer, "listener must not be null");
        this.mGlobal.registerTrustedPresentationListener(iBinder, trustedPresentationThresholds, executor, consumer);
    }

    @Override // android.view.WindowManager
    public void unregisterTrustedPresentationListener(Consumer<Boolean> consumer) {
        Objects.requireNonNull(consumer, "listener must not be null");
        this.mGlobal.unregisterTrustedPresentationListener(consumer);
    }

    @Override // android.view.WindowManager
    public InputTransferToken registerBatchedSurfaceControlInputReceiver(InputTransferToken inputTransferToken, SurfaceControl surfaceControl, Choreographer choreographer, SurfaceControlInputReceiver surfaceControlInputReceiver) {
        Objects.requireNonNull(inputTransferToken);
        Objects.requireNonNull(surfaceControl);
        Objects.requireNonNull(choreographer);
        Objects.requireNonNull(surfaceControlInputReceiver);
        return this.mGlobal.registerBatchedSurfaceControlInputReceiver(inputTransferToken, surfaceControl, choreographer, surfaceControlInputReceiver);
    }

    @Override // android.view.WindowManager
    public InputTransferToken registerUnbatchedSurfaceControlInputReceiver(InputTransferToken inputTransferToken, SurfaceControl surfaceControl, Looper looper, SurfaceControlInputReceiver surfaceControlInputReceiver) {
        Objects.requireNonNull(inputTransferToken);
        Objects.requireNonNull(surfaceControl);
        Objects.requireNonNull(looper);
        Objects.requireNonNull(surfaceControlInputReceiver);
        return this.mGlobal.registerUnbatchedSurfaceControlInputReceiver(inputTransferToken, surfaceControl, looper, surfaceControlInputReceiver);
    }

    @Override // android.view.WindowManager
    public void unregisterSurfaceControlInputReceiver(SurfaceControl surfaceControl) {
        Objects.requireNonNull(surfaceControl);
        this.mGlobal.unregisterSurfaceControlInputReceiver(surfaceControl);
    }

    @Override // android.view.WindowManager
    public IBinder getSurfaceControlInputClientToken(SurfaceControl surfaceControl) {
        Objects.requireNonNull(surfaceControl);
        return this.mGlobal.getSurfaceControlInputClientToken(surfaceControl);
    }

    @Override // android.view.WindowManager
    public boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) {
        Objects.requireNonNull(inputTransferToken);
        Objects.requireNonNull(inputTransferToken2);
        return this.mGlobal.transferTouchGesture(inputTransferToken, inputTransferToken2);
    }

    @Override // android.view.WindowManager
    public int addScreenRecordingCallback(Executor executor, Consumer<Integer> consumer) {
        if (!Flags.screenRecordingCallbacks()) {
            return 0;
        }
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(consumer, "callback must not be null");
        return ScreenRecordingCallbacks.getInstance().addCallback(executor, consumer);
    }

    @Override // android.view.WindowManager
    public void removeScreenRecordingCallback(Consumer<Integer> consumer) {
        if (Flags.screenRecordingCallbacks()) {
            Objects.requireNonNull(consumer, "callback must not be null");
            ScreenRecordingCallbacks.getInstance().removeCallback(consumer);
        }
    }
}
