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
import com.android.internal.os.IResultReceiver;
import com.android.window.flags.Flags;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes4.dex */
public final class WindowManagerImpl implements WindowManager {
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

    private WindowManagerImpl(Context context, Window parentWindow, IBinder windowContextToken) {
        this.mGlobal = WindowManagerGlobal.getInstance();
        this.mOnFpsCallbackListenerProxies = new ArrayList<>();
        this.mContext = context;
        this.mParentWindow = parentWindow;
        this.mWindowContextToken = windowContextToken;
        this.mWindowMetricsController = new WindowMetricsController(this.mContext);
    }

    public WindowManagerImpl createLocalWindowManager(Window parentWindow) {
        return new WindowManagerImpl(this.mContext, parentWindow, this.mWindowContextToken);
    }

    public WindowManagerImpl createPresentationWindowManager(Context displayContext) {
        return new WindowManagerImpl(displayContext, this.mParentWindow, this.mWindowContextToken);
    }

    public static WindowManager createWindowContextWindowManager(Context context) {
        IBinder clientToken = context.getWindowContextToken();
        return new WindowManagerImpl(context, null, clientToken);
    }

    public void setDefaultToken(IBinder token) {
        this.mDefaultToken = token;
    }

    @Override // android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams params) {
        applyTokens(params);
        this.mGlobal.addView(view, params, this.mContext.getDisplayNoVerify(), this.mParentWindow, this.mContext.getUserId());
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
        applyTokens(params);
        this.mGlobal.updateViewLayout(view, params);
    }

    private void applyTokens(ViewGroup.LayoutParams params) {
        if (!(params instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        WindowManager.LayoutParams wparams = (WindowManager.LayoutParams) params;
        assertWindowContextTypeMatches(wparams.type);
        if (this.mDefaultToken != null && this.mParentWindow == null && wparams.token == null) {
            wparams.token = this.mDefaultToken;
        }
        wparams.mWindowContextToken = this.mWindowContextToken;
    }

    private void assertWindowContextTypeMatches(int windowType) {
        if (!(this.mContext instanceof WindowProvider)) {
            return;
        }
        if (windowType >= 1000 && windowType <= 1999) {
            return;
        }
        WindowProvider windowProvider = (WindowProvider) this.mContext;
        if (windowProvider.getWindowType() == windowType) {
            return;
        }
        IllegalArgumentException exception = new IllegalArgumentException("Window type mismatch. Window Context's window type is " + windowProvider.getWindowType() + ", while LayoutParams' type is set to " + windowType + ". Please create another Window Context via createWindowContext(getDisplay(), " + windowType + ", null) to add window with type:" + windowType);
        if (!WindowProviderService.isWindowProviderService(windowProvider.getWindowContextOptions())) {
            throw exception;
        }
        StrictMode.onIncorrectContextUsed("WindowContext's window type must match type in WindowManager.LayoutParams", exception);
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
    public void requestAppKeyboardShortcuts(final WindowManager.KeyboardShortcutsReceiver receiver, int deviceId) {
        IResultReceiver resultReceiver = new IResultReceiver.Stub() { // from class: android.view.WindowManagerImpl.1
            @Override // com.android.internal.os.IResultReceiver
            public void send(int resultCode, Bundle resultData) throws RemoteException {
                List<KeyboardShortcutGroup> result = resultData.getParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, KeyboardShortcutGroup.class);
                receiver.onKeyboardShortcutsReceived(result);
            }
        };
        try {
            WindowManagerGlobal.getWindowManagerService().requestAppKeyboardShortcuts(resultReceiver, deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public void requestImeKeyboardShortcuts(final WindowManager.KeyboardShortcutsReceiver receiver, int deviceId) {
        IResultReceiver resultReceiver = new IResultReceiver.Stub() { // from class: android.view.WindowManagerImpl.2
            @Override // com.android.internal.os.IResultReceiver
            public void send(int resultCode, Bundle resultData) throws RemoteException {
                List<KeyboardShortcutGroup> result = resultData.getParcelableArrayList(WindowManager.PARCEL_KEY_SHORTCUTS_ARRAY, KeyboardShortcutGroup.class);
                receiver.onKeyboardShortcutsReceived(result);
            }
        };
        try {
            WindowManagerGlobal.getWindowManagerService().requestImeKeyboardShortcuts(resultReceiver, deviceId);
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
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowWithInsecureKeyguard(int displayId, boolean shouldShow) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowWithInsecureKeyguard(displayId, shouldShow);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public void setShouldShowSystemDecors(int displayId, boolean shouldShow) {
        try {
            WindowManagerGlobal.getWindowManagerService().setShouldShowSystemDecors(displayId, shouldShow);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public boolean shouldShowSystemDecors(int displayId) {
        try {
            return WindowManagerGlobal.getWindowManagerService().shouldShowSystemDecors(displayId);
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void setDisplayImePolicy(int displayId, int imePolicy) {
        try {
            WindowManagerGlobal.getWindowManagerService().setDisplayImePolicy(displayId, imePolicy);
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.WindowManager
    public int getDisplayImePolicy(int displayId) {
        try {
            return WindowManagerGlobal.getWindowManagerService().getDisplayImePolicy(displayId);
        } catch (RemoteException e) {
            return 1;
        }
    }

    @Override // android.view.WindowManager
    public boolean isGlobalKey(int keyCode) {
        try {
            return WindowManagerGlobal.getWindowManagerService().isGlobalKey(keyCode);
        } catch (RemoteException e) {
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
    public Set<WindowMetrics> getPossibleMaximumWindowMetrics(int displayId) {
        return this.mWindowMetricsController.getPossibleMaximumWindowMetrics(displayId);
    }

    @Override // android.view.WindowManager
    public void holdLock(IBinder token, int durationMs) {
        try {
            WindowManagerGlobal.getWindowManagerService().holdLock(token, durationMs);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public boolean isCrossWindowBlurEnabled() {
        return CrossWindowBlurListeners.getInstance().isCrossWindowBlurEnabled();
    }

    @Override // android.view.WindowManager
    public void addCrossWindowBlurEnabledListener(Consumer<Boolean> listener) {
        addCrossWindowBlurEnabledListener(this.mContext.getMainExecutor(), listener);
    }

    @Override // android.view.WindowManager
    public void addCrossWindowBlurEnabledListener(Executor executor, Consumer<Boolean> listener) {
        CrossWindowBlurListeners.getInstance().addListener(executor, listener);
    }

    @Override // android.view.WindowManager
    public void removeCrossWindowBlurEnabledListener(Consumer<Boolean> listener) {
        CrossWindowBlurListeners.getInstance().removeListener(listener);
    }

    @Override // android.view.WindowManager
    public void addProposedRotationListener(Executor executor, IntConsumer listener) {
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(listener, "listener must not be null");
        IBinder contextToken = Context.getToken(this.mContext);
        if (contextToken == null) {
            throw new UnsupportedOperationException("The context of this window manager instance must be a UI context, e.g. an Activity or a Context created by Context#createWindowContext()");
        }
        this.mGlobal.registerProposedRotationListener(contextToken, executor, listener);
    }

    @Override // android.view.WindowManager
    public void removeProposedRotationListener(IntConsumer listener) {
        this.mGlobal.unregisterProposedRotationListener(Context.getToken(this.mContext), listener);
    }

    @Override // android.view.WindowManager
    public boolean isTaskSnapshotSupported() {
        try {
            return WindowManagerGlobal.getWindowManagerService().isTaskSnapshotSupported();
        } catch (RemoteException e) {
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void registerTaskFpsCallback(int taskId, Executor executor, TaskFpsCallback callback) {
        OnFpsCallbackListenerProxy onFpsCallbackListenerProxy = new OnFpsCallbackListenerProxy(executor, callback);
        try {
            WindowManagerGlobal.getWindowManagerService().registerTaskFpsCallback(taskId, onFpsCallbackListenerProxy);
            synchronized (this.mOnFpsCallbackListenerProxies) {
                this.mOnFpsCallbackListenerProxies.add(onFpsCallbackListenerProxy);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public void unregisterTaskFpsCallback(TaskFpsCallback callback) {
        synchronized (this.mOnFpsCallbackListenerProxies) {
            Iterator<OnFpsCallbackListenerProxy> iterator = this.mOnFpsCallbackListenerProxies.iterator();
            while (iterator.hasNext()) {
                OnFpsCallbackListenerProxy proxy = iterator.next();
                if (proxy.mCallback == callback) {
                    try {
                        WindowManagerGlobal.getWindowManagerService().unregisterTaskFpsCallback(proxy);
                        iterator.remove();
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

        private OnFpsCallbackListenerProxy(Executor executor, TaskFpsCallback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        @Override // android.window.ITaskFpsCallback
        public void onFpsReported(final float fps) {
            this.mExecutor.execute(new Runnable() { // from class: android.view.WindowManagerImpl$OnFpsCallbackListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowManagerImpl.OnFpsCallbackListenerProxy.this.lambda$onFpsReported$0(fps);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFpsReported$0(float fps) {
            this.mCallback.onFpsReported(fps);
        }
    }

    @Override // android.view.WindowManager
    public Bitmap snapshotTaskForRecents(int taskId) {
        try {
            return WindowManagerGlobal.getWindowManagerService().snapshotTaskForRecents(taskId);
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
    public List<ComponentName> notifyScreenshotListeners(int displayId) {
        try {
            return List.copyOf(WindowManagerGlobal.getWindowManagerService().notifyScreenshotListeners(displayId));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.view.WindowManager
    public boolean replaceContentOnDisplayWithMirror(int displayId, Window window) {
        View decorView = window.peekDecorView();
        if (decorView == null) {
            Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's decorView was null.");
            return false;
        }
        ViewRootImpl viewRoot = decorView.getViewRootImpl();
        if (viewRoot == null) {
            Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's viewRootImpl was null.");
            return false;
        }
        SurfaceControl sc = viewRoot.getSurfaceControl();
        if (!sc.isValid()) {
            Log.e(TAG, "replaceContentOnDisplayWithMirror: Window's SC is invalid.");
            return false;
        }
        return replaceContentOnDisplayWithSc(displayId, SurfaceControl.mirrorSurface(sc));
    }

    @Override // android.view.WindowManager
    public boolean replaceContentOnDisplayWithSc(int displayId, SurfaceControl sc) {
        if (!sc.isValid()) {
            Log.e(TAG, "replaceContentOnDisplayWithSc: Invalid SC.");
            return false;
        }
        try {
            return WindowManagerGlobal.getWindowManagerService().replaceContentOnDisplay(displayId, sc);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @Override // android.view.WindowManager
    public void registerTrustedPresentationListener(IBinder window, TrustedPresentationThresholds thresholds, Executor executor, Consumer<Boolean> listener) {
        Objects.requireNonNull(window, "window must not be null");
        Objects.requireNonNull(thresholds, "thresholds must not be null");
        Objects.requireNonNull(executor, "executor must not be null");
        Objects.requireNonNull(listener, "listener must not be null");
        this.mGlobal.registerTrustedPresentationListener(window, thresholds, executor, listener);
    }

    @Override // android.view.WindowManager
    public void unregisterTrustedPresentationListener(Consumer<Boolean> listener) {
        Objects.requireNonNull(listener, "listener must not be null");
        this.mGlobal.unregisterTrustedPresentationListener(listener);
    }

    @Override // android.view.WindowManager
    public InputTransferToken registerBatchedSurfaceControlInputReceiver(InputTransferToken hostInputTransferToken, SurfaceControl surfaceControl, Choreographer choreographer, SurfaceControlInputReceiver receiver) {
        Objects.requireNonNull(hostInputTransferToken);
        Objects.requireNonNull(surfaceControl);
        Objects.requireNonNull(choreographer);
        Objects.requireNonNull(receiver);
        return this.mGlobal.registerBatchedSurfaceControlInputReceiver(hostInputTransferToken, surfaceControl, choreographer, receiver);
    }

    @Override // android.view.WindowManager
    public InputTransferToken registerUnbatchedSurfaceControlInputReceiver(InputTransferToken hostInputTransferToken, SurfaceControl surfaceControl, Looper looper, SurfaceControlInputReceiver receiver) {
        Objects.requireNonNull(hostInputTransferToken);
        Objects.requireNonNull(surfaceControl);
        Objects.requireNonNull(looper);
        Objects.requireNonNull(receiver);
        return this.mGlobal.registerUnbatchedSurfaceControlInputReceiver(hostInputTransferToken, surfaceControl, looper, receiver);
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
    public boolean transferTouchGesture(InputTransferToken transferFromToken, InputTransferToken transferToToken) {
        Objects.requireNonNull(transferFromToken);
        Objects.requireNonNull(transferToToken);
        return this.mGlobal.transferTouchGesture(transferFromToken, transferToToken);
    }

    @Override // android.view.WindowManager
    public int addScreenRecordingCallback(Executor executor, Consumer<Integer> callback) {
        if (Flags.screenRecordingCallbacks()) {
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(callback, "callback must not be null");
            return ScreenRecordingCallbacks.getInstance().addCallback(executor, callback);
        }
        return 0;
    }

    @Override // android.view.WindowManager
    public void removeScreenRecordingCallback(Consumer<Integer> callback) {
        if (Flags.screenRecordingCallbacks()) {
            Objects.requireNonNull(callback, "callback must not be null");
            ScreenRecordingCallbacks.getInstance().removeCallback(callback);
        }
    }
}
