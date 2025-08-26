package android.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.ISurfaceControlViewHost;
import android.view.SurfaceControl;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.accessibility.IAccessibilityEmbeddedConnection;
import android.window.ISurfaceSyncGroup;
import android.window.InputTransferToken;
import android.window.WindowTokenClient;
import dalvik.system.CloseGuard;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class SurfaceControlViewHost {
    private static final String TAG = "SurfaceControlViewHost";
    private IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
    private final CloseGuard mCloseGuard;
    private ViewRootImpl.ConfigChangedCallback mConfigChangedCallback;
    private boolean mReleased;
    private ISurfaceControlViewHost mRemoteInterface;
    private SurfaceControl mSurfaceControl;
    private ViewRootImpl mViewRoot;
    private final WindowlessWindowManager mWm;

    /* JADX INFO: Access modifiers changed from: private */
    final class ISurfaceControlViewHostImpl extends ISurfaceControlViewHost.Stub {
        private ISurfaceControlViewHostImpl() {
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onConfigurationChanged(final Configuration configuration) {
            if (SurfaceControlViewHost.this.mViewRoot == null) {
                return;
            }
            SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onConfigurationChanged$0(configuration);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigurationChanged$0(Configuration configuration) {
            SurfaceControlViewHost.this.mWm.setConfiguration(configuration);
            if (SurfaceControlViewHost.this.mViewRoot != null) {
                SurfaceControlViewHost.this.mViewRoot.forceWmRelayout();
            }
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onDispatchDetachedFromWindow() {
            if (SurfaceControlViewHost.this.mViewRoot == null) {
                return;
            }
            SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDispatchDetachedFromWindow$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDispatchDetachedFromWindow$1() {
            SurfaceControlViewHost.this.release();
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onInsetsChanged(InsetsState insetsState, final Rect rect) {
            if (SurfaceControlViewHost.this.mViewRoot != null) {
                SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onInsetsChanged$2(rect);
                    }
                });
            }
            SurfaceControlViewHost.this.mWm.setInsetsState(insetsState);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInsetsChanged$2(Rect rect) {
            SurfaceControlViewHost.this.mViewRoot.setOverrideInsetsFrame(rect);
        }

        @Override // android.view.ISurfaceControlViewHost
        public ISurfaceSyncGroup getSurfaceSyncGroup() {
            final CompletableFuture completableFuture = new CompletableFuture();
            if (Thread.currentThread() == SurfaceControlViewHost.this.mViewRoot.mThread) {
                return SurfaceControlViewHost.this.mViewRoot.getOrCreateSurfaceSyncGroup().mISurfaceSyncGroup;
            }
            SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$getSurfaceSyncGroup$3(completableFuture);
                }
            });
            try {
                return (ISurfaceSyncGroup) completableFuture.get(1L, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Log.e(SurfaceControlViewHost.TAG, "Failed to get SurfaceSyncGroup for SCVH", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSurfaceSyncGroup$3(CompletableFuture completableFuture) {
            completableFuture.complete(SurfaceControlViewHost.this.mViewRoot.getOrCreateSurfaceSyncGroup().mISurfaceSyncGroup);
        }

        @Override // android.view.ISurfaceControlViewHost
        public void attachParentInterface(final ISurfaceControlViewHostParent iSurfaceControlViewHostParent) {
            if (SurfaceControlViewHost.this.mViewRoot == null) {
                Log.d(SurfaceControlViewHost.TAG, "attachParentInterface called but mViewRoot is null. return here.");
            } else {
                SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$attachParentInterface$4(iSurfaceControlViewHostParent);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$attachParentInterface$4(ISurfaceControlViewHostParent iSurfaceControlViewHostParent) {
            SurfaceControlViewHost.this.mWm.setParentInterface(iSurfaceControlViewHostParent);
        }
    }

    public static final class SurfacePackage implements Parcelable {
        public static final Parcelable.Creator<SurfacePackage> CREATOR = new Parcelable.Creator<SurfacePackage>() { // from class: android.view.SurfaceControlViewHost.SurfacePackage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SurfacePackage createFromParcel(Parcel parcel) {
                return new SurfacePackage(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SurfacePackage[] newArray(int i) {
                return new SurfacePackage[i];
            }
        };
        private final IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
        private final InputTransferToken mInputTransferToken;
        private final ISurfaceControlViewHost mRemoteInterface;
        private SurfaceControl mSurfaceControl;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SurfacePackage(SurfaceControl surfaceControl, IAccessibilityEmbeddedConnection iAccessibilityEmbeddedConnection, InputTransferToken inputTransferToken, ISurfaceControlViewHost iSurfaceControlViewHost) {
            this.mSurfaceControl = surfaceControl;
            this.mAccessibilityEmbeddedConnection = iAccessibilityEmbeddedConnection;
            this.mInputTransferToken = inputTransferToken;
            this.mRemoteInterface = iSurfaceControlViewHost;
        }

        public SurfacePackage(SurfacePackage surfacePackage) {
            SurfaceControl surfaceControl = surfacePackage.mSurfaceControl;
            if (surfaceControl != null && surfaceControl.isValid()) {
                this.mSurfaceControl = new SurfaceControl(surfaceControl, "SurfacePackage");
            }
            this.mAccessibilityEmbeddedConnection = surfacePackage.mAccessibilityEmbeddedConnection;
            this.mInputTransferToken = surfacePackage.mInputTransferToken;
            this.mRemoteInterface = surfacePackage.mRemoteInterface;
        }

        private SurfacePackage(Parcel parcel) {
            SurfaceControl surfaceControl = new SurfaceControl();
            this.mSurfaceControl = surfaceControl;
            surfaceControl.readFromParcel(parcel);
            this.mSurfaceControl.setUnreleasedWarningCallSite("SurfacePackage(Parcel)");
            this.mAccessibilityEmbeddedConnection = IAccessibilityEmbeddedConnection.Stub.asInterface(parcel.readStrongBinder());
            this.mInputTransferToken = InputTransferToken.CREATOR.createFromParcel(parcel);
            this.mRemoteInterface = ISurfaceControlViewHost.Stub.asInterface(parcel.readStrongBinder());
        }

        public SurfaceControl getSurfaceControl() {
            return this.mSurfaceControl;
        }

        public IAccessibilityEmbeddedConnection getAccessibilityEmbeddedConnection() {
            return this.mAccessibilityEmbeddedConnection;
        }

        public ISurfaceControlViewHost getRemoteInterface() {
            return this.mRemoteInterface;
        }

        public void notifyConfigurationChanged(Configuration configuration) {
            try {
                getRemoteInterface().onConfigurationChanged(configuration);
            } catch (RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }

        public void notifyDetachedFromWindow() {
            try {
                getRemoteInterface().onDispatchDetachedFromWindow();
            } catch (RemoteException e) {
                e.rethrowAsRuntimeException();
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mSurfaceControl.writeToParcel(parcel, i);
            parcel.writeStrongBinder(this.mAccessibilityEmbeddedConnection.asBinder());
            this.mInputTransferToken.writeToParcel(parcel, i);
            parcel.writeStrongBinder(this.mRemoteInterface.asBinder());
        }

        public void release() {
            SurfaceControl surfaceControl = this.mSurfaceControl;
            if (surfaceControl != null) {
                surfaceControl.release();
            }
            this.mSurfaceControl = null;
        }

        public InputTransferToken getInputTransferToken() {
            return this.mInputTransferToken;
        }

        public String toString() {
            return "{inputTransferToken=" + getInputTransferToken() + " remoteInterface=" + getRemoteInterface() + "}";
        }
    }

    public SurfaceControlViewHost(Context context, Display display, WindowlessWindowManager windowlessWindowManager, String str) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mReleased = false;
        this.mRemoteInterface = new ISurfaceControlViewHostImpl();
        this.mSurfaceControl = windowlessWindowManager.mRootSurface;
        this.mWm = windowlessWindowManager;
        this.mViewRoot = new ViewRootImpl(context, display, windowlessWindowManager, new WindowlessWindowLayout());
        closeGuard.openWithCallSite("release", str);
        setConfigCallback(context, display);
        WindowManagerGlobal.getInstance().addWindowlessRoot(this.mViewRoot);
        this.mAccessibilityEmbeddedConnection = this.mViewRoot.getAccessibilityEmbeddedConnection();
    }

    public SurfaceControlViewHost(Context context, Display display, IBinder iBinder) {
        this(context, display, iBinder == null ? null : new InputTransferToken(iBinder), "untracked");
    }

    public SurfaceControlViewHost(Context context, Display display, InputTransferToken inputTransferToken) {
        this(context, display, inputTransferToken, "untracked");
    }

    public SurfaceControlViewHost(Context context, Display display, InputTransferToken inputTransferToken, String str) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mReleased = false;
        this.mRemoteInterface = new ISurfaceControlViewHostImpl();
        this.mSurfaceControl = new SurfaceControl.Builder().setContainerLayer().setName(TAG).setCallsite("SurfaceControlViewHost[" + str + NavigationBarInflaterView.SIZE_MOD_END).build();
        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(context.getResources().getConfiguration(), this.mSurfaceControl, inputTransferToken);
        this.mWm = windowlessWindowManager;
        this.mViewRoot = new ViewRootImpl(context, display, windowlessWindowManager, new WindowlessWindowLayout());
        closeGuard.openWithCallSite("release", str);
        setConfigCallback(context, display);
        WindowManagerGlobal.getInstance().addWindowlessRoot(this.mViewRoot);
        this.mAccessibilityEmbeddedConnection = this.mViewRoot.getAccessibilityEmbeddedConnection();
    }

    private void setConfigCallback(Context context, final Display display) {
        final IBinder windowContextToken = context.getWindowContextToken();
        ViewRootImpl.ConfigChangedCallback configChangedCallback = new ViewRootImpl.ConfigChangedCallback() { // from class: android.view.SurfaceControlViewHost$$ExternalSyntheticLambda0
            @Override // android.view.ViewRootImpl.ConfigChangedCallback
            public final void onConfigurationChanged(Configuration configuration) {
                SurfaceControlViewHost.lambda$setConfigCallback$0(windowContextToken, display, configuration);
            }
        };
        this.mConfigChangedCallback = configChangedCallback;
        ViewRootImpl.addConfigCallback(configChangedCallback);
    }

    static /* synthetic */ void lambda$setConfigCallback$0(IBinder iBinder, Display display, Configuration configuration) {
        if (iBinder instanceof WindowTokenClient) {
            ((WindowTokenClient) iBinder).onConfigurationChanged(configuration, display.getDisplayId(), true);
        }
    }

    protected void finalize() throws Throwable {
        if (this.mReleased) {
            return;
        }
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            closeGuard.warnIfOpen();
        }
        doRelease(false);
    }

    public SurfacePackage getSurfacePackage() {
        if (this.mSurfaceControl == null || this.mAccessibilityEmbeddedConnection == null) {
            return null;
        }
        return new SurfacePackage(new SurfaceControl(this.mSurfaceControl, "getSurfacePackage"), this.mAccessibilityEmbeddedConnection, getInputTransferToken(), this.mRemoteInterface);
    }

    public AttachedSurfaceControl getRootSurfaceControl() {
        return this.mViewRoot;
    }

    public void setView(View view, int i, int i2) {
        setView(view, new WindowManager.LayoutParams(i, i2, 2, 0, -2));
    }

    public void setView(View view, WindowManager.LayoutParams layoutParams) {
        Objects.requireNonNull(view);
        layoutParams.flags |= 16777216;
        addWindowToken(layoutParams);
        view.setLayoutParams(layoutParams);
        this.mViewRoot.setView(view, layoutParams, null);
        ViewRootImpl viewRootImpl = this.mViewRoot;
        final WindowlessWindowManager windowlessWindowManager = this.mWm;
        Objects.requireNonNull(windowlessWindowManager);
        viewRootImpl.setBackKeyCallbackForWindowlessWindow(new Predicate() { // from class: android.view.SurfaceControlViewHost$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return windowlessWindowManager.forwardBackKeyToParent((KeyEvent) obj);
            }
        });
    }

    public View getView() {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl != null) {
            return viewRootImpl.getView();
        }
        return null;
    }

    public IWindow getWindowToken() {
        return this.mViewRoot.mWindow;
    }

    public WindowlessWindowManager getWindowlessWM() {
        return this.mWm;
    }

    public void relayout(WindowManager.LayoutParams layoutParams, WindowlessWindowManager.ResizeCompleteCallback resizeCompleteCallback) {
        this.mViewRoot.setLayoutParams(layoutParams, false);
        this.mViewRoot.setReportNextDraw(true, "scvh_relayout");
        this.mWm.setCompletionCallback(this.mViewRoot.mWindow.asBinder(), resizeCompleteCallback);
    }

    public void relayout(WindowManager.LayoutParams layoutParams) {
        this.mViewRoot.setLayoutParams(layoutParams, false);
    }

    public void relayout(WindowManager.LayoutParams layoutParams, boolean z) {
        this.mViewRoot.setLayoutParams(layoutParams, z);
    }

    public void relayout(int i, int i2) {
        relayout(new WindowManager.LayoutParams(i, i2, 2, 0, -2));
    }

    public void release() {
        doRelease(true);
    }

    private void doRelease(boolean z) {
        ViewRootImpl.ConfigChangedCallback configChangedCallback = this.mConfigChangedCallback;
        if (configChangedCallback != null) {
            ViewRootImpl.removeConfigCallback(configChangedCallback);
            this.mConfigChangedCallback = null;
        }
        this.mViewRoot.die(z);
        WindowManagerGlobal.getInstance().removeWindowlessRoot(this.mViewRoot);
        this.mReleased = true;
        this.mCloseGuard.close();
        this.mViewRoot = null;
    }

    public InputTransferToken getInputTransferToken() {
        return this.mWm.getInputTransferToken(getWindowToken().asBinder());
    }

    private void addWindowToken(WindowManager.LayoutParams layoutParams) {
        layoutParams.token = ((WindowManager) this.mViewRoot.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultToken();
    }

    @Deprecated
    public boolean transferTouchGestureToHost() {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl == null) {
            return false;
        }
        WindowManager windowManager = (WindowManager) viewRootImpl.mContext.getSystemService(Context.WINDOW_SERVICE);
        InputTransferToken inputTransferToken = getInputTransferToken();
        InputTransferToken inputTransferToken2 = this.mWm.mHostInputTransferToken;
        if (inputTransferToken == null || inputTransferToken2 == null) {
            Log.w(TAG, "Failed to transferTouchGestureToHost. Host or embedded token is null");
            return false;
        }
        return windowManager.transferTouchGesture(getInputTransferToken(), this.mWm.mHostInputTransferToken);
    }
}
