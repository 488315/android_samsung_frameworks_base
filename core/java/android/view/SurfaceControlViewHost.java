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
import android.view.SurfaceControlViewHost;
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
                    SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$onConfigurationChanged$0(configuration);
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
                    SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$onDispatchDetachedFromWindow$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDispatchDetachedFromWindow$1() {
            SurfaceControlViewHost.this.release();
        }

        @Override // android.view.ISurfaceControlViewHost
        public void onInsetsChanged(InsetsState state, final Rect frame) {
            if (SurfaceControlViewHost.this.mViewRoot != null) {
                SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$onInsetsChanged$2(frame);
                    }
                });
            }
            SurfaceControlViewHost.this.mWm.setInsetsState(state);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInsetsChanged$2(Rect frame) {
            SurfaceControlViewHost.this.mViewRoot.setOverrideInsetsFrame(frame);
        }

        @Override // android.view.ISurfaceControlViewHost
        public ISurfaceSyncGroup getSurfaceSyncGroup() {
            final CompletableFuture<ISurfaceSyncGroup> surfaceSyncGroup = new CompletableFuture<>();
            if (Thread.currentThread() == SurfaceControlViewHost.this.mViewRoot.mThread) {
                return SurfaceControlViewHost.this.mViewRoot.getOrCreateSurfaceSyncGroup().mISurfaceSyncGroup;
            }
            SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$getSurfaceSyncGroup$3(surfaceSyncGroup);
                }
            });
            try {
                return surfaceSyncGroup.get(1L, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Log.e(SurfaceControlViewHost.TAG, "Failed to get SurfaceSyncGroup for SCVH", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getSurfaceSyncGroup$3(CompletableFuture surfaceSyncGroup) {
            surfaceSyncGroup.complete(SurfaceControlViewHost.this.mViewRoot.getOrCreateSurfaceSyncGroup().mISurfaceSyncGroup);
        }

        @Override // android.view.ISurfaceControlViewHost
        public void attachParentInterface(final ISurfaceControlViewHostParent parentInterface) {
            if (SurfaceControlViewHost.this.mViewRoot == null) {
                Log.d(SurfaceControlViewHost.TAG, "attachParentInterface called but mViewRoot is null. return here.");
            } else {
                SurfaceControlViewHost.this.mViewRoot.mHandler.post(new Runnable() { // from class: android.view.SurfaceControlViewHost$ISurfaceControlViewHostImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SurfaceControlViewHost.ISurfaceControlViewHostImpl.this.lambda$attachParentInterface$4(parentInterface);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$attachParentInterface$4(ISurfaceControlViewHostParent parentInterface) {
            SurfaceControlViewHost.this.mWm.setParentInterface(parentInterface);
        }
    }

    public static final class SurfacePackage implements Parcelable {
        public static final Parcelable.Creator<SurfacePackage> CREATOR = new Parcelable.Creator<SurfacePackage>() { // from class: android.view.SurfaceControlViewHost.SurfacePackage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SurfacePackage createFromParcel(Parcel in) {
                return new SurfacePackage(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SurfacePackage[] newArray(int size) {
                return new SurfacePackage[size];
            }
        };
        private final IAccessibilityEmbeddedConnection mAccessibilityEmbeddedConnection;
        private final InputTransferToken mInputTransferToken;
        private final ISurfaceControlViewHost mRemoteInterface;
        private SurfaceControl mSurfaceControl;

        SurfacePackage(SurfaceControl sc, IAccessibilityEmbeddedConnection connection, InputTransferToken inputTransferToken, ISurfaceControlViewHost ri) {
            this.mSurfaceControl = sc;
            this.mAccessibilityEmbeddedConnection = connection;
            this.mInputTransferToken = inputTransferToken;
            this.mRemoteInterface = ri;
        }

        public SurfacePackage(SurfacePackage other) {
            SurfaceControl otherSurfaceControl = other.mSurfaceControl;
            if (otherSurfaceControl != null && otherSurfaceControl.isValid()) {
                this.mSurfaceControl = new SurfaceControl(otherSurfaceControl, "SurfacePackage");
            }
            this.mAccessibilityEmbeddedConnection = other.mAccessibilityEmbeddedConnection;
            this.mInputTransferToken = other.mInputTransferToken;
            this.mRemoteInterface = other.mRemoteInterface;
        }

        private SurfacePackage(Parcel in) {
            this.mSurfaceControl = new SurfaceControl();
            this.mSurfaceControl.readFromParcel(in);
            this.mSurfaceControl.setUnreleasedWarningCallSite("SurfacePackage(Parcel)");
            this.mAccessibilityEmbeddedConnection = IAccessibilityEmbeddedConnection.Stub.asInterface(in.readStrongBinder());
            this.mInputTransferToken = InputTransferToken.CREATOR.createFromParcel(in);
            this.mRemoteInterface = ISurfaceControlViewHost.Stub.asInterface(in.readStrongBinder());
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

        public void notifyConfigurationChanged(Configuration c) {
            try {
                getRemoteInterface().onConfigurationChanged(c);
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
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            this.mSurfaceControl.writeToParcel(out, flags);
            out.writeStrongBinder(this.mAccessibilityEmbeddedConnection.asBinder());
            this.mInputTransferToken.writeToParcel(out, flags);
            out.writeStrongBinder(this.mRemoteInterface.asBinder());
        }

        public void release() {
            if (this.mSurfaceControl != null) {
                this.mSurfaceControl.release();
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

    public SurfaceControlViewHost(Context c, Display d, WindowlessWindowManager wwm, String callsite) {
        this.mCloseGuard = CloseGuard.get();
        this.mReleased = false;
        this.mRemoteInterface = new ISurfaceControlViewHostImpl();
        this.mSurfaceControl = wwm.mRootSurface;
        this.mWm = wwm;
        this.mViewRoot = new ViewRootImpl(c, d, this.mWm, new WindowlessWindowLayout());
        this.mCloseGuard.openWithCallSite("release", callsite);
        setConfigCallback(c, d);
        WindowManagerGlobal.getInstance().addWindowlessRoot(this.mViewRoot);
        this.mAccessibilityEmbeddedConnection = this.mViewRoot.getAccessibilityEmbeddedConnection();
    }

    public SurfaceControlViewHost(Context context, Display display, IBinder hostToken) {
        this(context, display, hostToken == null ? null : new InputTransferToken(hostToken), "untracked");
    }

    public SurfaceControlViewHost(Context context, Display display, InputTransferToken hostInputTransferToken) {
        this(context, display, hostInputTransferToken, "untracked");
    }

    public SurfaceControlViewHost(Context context, Display display, InputTransferToken hostToken, String callsite) {
        this.mCloseGuard = CloseGuard.get();
        this.mReleased = false;
        this.mRemoteInterface = new ISurfaceControlViewHostImpl();
        this.mSurfaceControl = new SurfaceControl.Builder().setContainerLayer().setName(TAG).setCallsite("SurfaceControlViewHost[" + callsite + NavigationBarInflaterView.SIZE_MOD_END).build();
        this.mWm = new WindowlessWindowManager(context.getResources().getConfiguration(), this.mSurfaceControl, hostToken);
        this.mViewRoot = new ViewRootImpl(context, display, this.mWm, new WindowlessWindowLayout());
        this.mCloseGuard.openWithCallSite("release", callsite);
        setConfigCallback(context, display);
        WindowManagerGlobal.getInstance().addWindowlessRoot(this.mViewRoot);
        this.mAccessibilityEmbeddedConnection = this.mViewRoot.getAccessibilityEmbeddedConnection();
    }

    private void setConfigCallback(Context c, final Display d) {
        final IBinder token = c.getWindowContextToken();
        this.mConfigChangedCallback = new ViewRootImpl.ConfigChangedCallback() { // from class: android.view.SurfaceControlViewHost$$ExternalSyntheticLambda0
            @Override // android.view.ViewRootImpl.ConfigChangedCallback
            public final void onConfigurationChanged(Configuration configuration) {
                SurfaceControlViewHost.lambda$setConfigCallback$0(IBinder.this, d, configuration);
            }
        };
        ViewRootImpl.addConfigCallback(this.mConfigChangedCallback);
    }

    static /* synthetic */ void lambda$setConfigCallback$0(IBinder token, Display d, Configuration conf) {
        if (token instanceof WindowTokenClient) {
            WindowTokenClient w = (WindowTokenClient) token;
            w.onConfigurationChanged(conf, d.getDisplayId(), true);
        }
    }

    protected void finalize() throws Throwable {
        if (this.mReleased) {
            return;
        }
        if (this.mCloseGuard != null) {
            this.mCloseGuard.warnIfOpen();
        }
        doRelease(false);
    }

    public SurfacePackage getSurfacePackage() {
        if (this.mSurfaceControl != null && this.mAccessibilityEmbeddedConnection != null) {
            return new SurfacePackage(new SurfaceControl(this.mSurfaceControl, "getSurfacePackage"), this.mAccessibilityEmbeddedConnection, getInputTransferToken(), this.mRemoteInterface);
        }
        return null;
    }

    public AttachedSurfaceControl getRootSurfaceControl() {
        return this.mViewRoot;
    }

    public void setView(View view, int width, int height) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(width, height, 2, 0, -2);
        setView(view, lp);
    }

    public void setView(View view, WindowManager.LayoutParams attrs) {
        Objects.requireNonNull(view);
        attrs.flags |= 16777216;
        addWindowToken(attrs);
        view.setLayoutParams(attrs);
        this.mViewRoot.setView(view, attrs, null);
        ViewRootImpl viewRootImpl = this.mViewRoot;
        final WindowlessWindowManager windowlessWindowManager = this.mWm;
        Objects.requireNonNull(windowlessWindowManager);
        viewRootImpl.setBackKeyCallbackForWindowlessWindow(new Predicate() { // from class: android.view.SurfaceControlViewHost$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return WindowlessWindowManager.this.forwardBackKeyToParent((KeyEvent) obj);
            }
        });
    }

    public View getView() {
        if (this.mViewRoot != null) {
            return this.mViewRoot.getView();
        }
        return null;
    }

    public IWindow getWindowToken() {
        return this.mViewRoot.mWindow;
    }

    public WindowlessWindowManager getWindowlessWM() {
        return this.mWm;
    }

    public void relayout(WindowManager.LayoutParams attrs, WindowlessWindowManager.ResizeCompleteCallback callback) {
        this.mViewRoot.setLayoutParams(attrs, false);
        this.mViewRoot.setReportNextDraw(true, "scvh_relayout");
        this.mWm.setCompletionCallback(this.mViewRoot.mWindow.asBinder(), callback);
    }

    public void relayout(WindowManager.LayoutParams attrs) {
        this.mViewRoot.setLayoutParams(attrs, false);
    }

    public void relayout(WindowManager.LayoutParams attrs, boolean newView) {
        this.mViewRoot.setLayoutParams(attrs, newView);
    }

    public void relayout(int width, int height) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(width, height, 2, 0, -2);
        relayout(lp);
    }

    public void release() {
        doRelease(true);
    }

    private void doRelease(boolean immediate) {
        if (this.mConfigChangedCallback != null) {
            ViewRootImpl.removeConfigCallback(this.mConfigChangedCallback);
            this.mConfigChangedCallback = null;
        }
        this.mViewRoot.die(immediate);
        WindowManagerGlobal.getInstance().removeWindowlessRoot(this.mViewRoot);
        this.mReleased = true;
        this.mCloseGuard.close();
        this.mViewRoot = null;
    }

    public InputTransferToken getInputTransferToken() {
        return this.mWm.getInputTransferToken(getWindowToken().asBinder());
    }

    private void addWindowToken(WindowManager.LayoutParams attrs) {
        WindowManager wm = (WindowManager) this.mViewRoot.mContext.getSystemService(Context.WINDOW_SERVICE);
        attrs.token = wm.getDefaultToken();
    }

    @Deprecated
    public boolean transferTouchGestureToHost() {
        if (this.mViewRoot == null) {
            return false;
        }
        WindowManager wm = (WindowManager) this.mViewRoot.mContext.getSystemService(Context.WINDOW_SERVICE);
        InputTransferToken embeddedToken = getInputTransferToken();
        InputTransferToken hostToken = this.mWm.mHostInputTransferToken;
        if (embeddedToken == null || hostToken == null) {
            Log.w(TAG, "Failed to transferTouchGestureToHost. Host or embedded token is null");
            return false;
        }
        return wm.transferTouchGesture(getInputTransferToken(), this.mWm.mHostInputTransferToken);
    }
}
