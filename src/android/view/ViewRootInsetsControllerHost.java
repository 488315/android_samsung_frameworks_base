package android.view;

import android.content.Context;
import android.content.res.CompatibilityInfo;
import android.graphics.HardwareRenderer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.InsetsController;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.ViewTreeObserver;
import android.view.WindowInsetsAnimation;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManager;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class ViewRootInsetsControllerHost implements InsetsController.Host {
    private final String TAG = "VRInsetsControllerHost";
    private SyncRtSurfaceTransactionApplier mApplier;
    private final ViewRootImpl mViewRoot;

    public ViewRootInsetsControllerHost(ViewRootImpl viewRootImpl) {
        this.mViewRoot = viewRootImpl;
    }

    @Override // android.view.InsetsController.Host
    public Handler getHandler() {
        return this.mViewRoot.mHandler;
    }

    @Override // android.view.InsetsController.Host
    public void notifyInsetsChanged() {
        this.mViewRoot.notifyInsetsChanged();
    }

    @Override // android.view.InsetsController.Host
    public void addOnPreDrawRunnable(final Runnable runnable) {
        if (this.mViewRoot.mView == null) {
            return;
        }
        this.mViewRoot.mView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.view.ViewRootInsetsControllerHost.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ViewRootInsetsControllerHost.this.mViewRoot.mView.getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        });
        this.mViewRoot.mView.invalidate();
    }

    @Override // android.view.InsetsController.Host
    public void dispatchWindowInsetsAnimationPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        if (this.mViewRoot.mView == null) {
            return;
        }
        this.mViewRoot.mView.dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
    }

    @Override // android.view.InsetsController.Host
    public WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        if (this.mViewRoot.mView == null) {
            return null;
        }
        if (InsetsController.DEBUG) {
            Log.d("VRInsetsControllerHost", "windowInsetsAnimation started");
        }
        return this.mViewRoot.mView.dispatchWindowInsetsAnimationStart(windowInsetsAnimation, bounds);
    }

    @Override // android.view.InsetsController.Host
    public WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets, List<WindowInsetsAnimation> list) {
        if (this.mViewRoot.mView == null) {
            return null;
        }
        if (InsetsController.DEBUG) {
            Iterator<WindowInsetsAnimation> it = list.iterator();
            while (it.hasNext()) {
                Log.d("VRInsetsControllerHost", "windowInsetsAnimation progress: " + it.next().getInterpolatedFraction());
            }
        }
        return this.mViewRoot.mView.dispatchWindowInsetsAnimationProgress(windowInsets, list);
    }

    @Override // android.view.InsetsController.Host
    public void dispatchWindowInsetsAnimationEnd(WindowInsetsAnimation windowInsetsAnimation) {
        if (InsetsController.DEBUG) {
            Log.d("VRInsetsControllerHost", "windowInsetsAnimation ended");
        }
        if (this.mViewRoot.mView == null) {
            return;
        }
        this.mViewRoot.mView.dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
    }

    @Override // android.view.InsetsController.Host
    public void applySurfaceParams(SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        if (this.mViewRoot.mView == null) {
            throw new IllegalStateException("View of the ViewRootImpl is not initiated.");
        }
        if (this.mApplier == null) {
            this.mApplier = new SyncRtSurfaceTransactionApplier(this.mViewRoot.mView);
        }
        if (this.mViewRoot.mView.isHardwareAccelerated() && isVisibleToUser()) {
            this.mApplier.scheduleApply(surfaceParamsArr);
            return;
        }
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        this.mApplier.applyParams(transaction, surfaceParamsArr);
        transaction.apply();
    }

    @Override // android.view.InsetsController.Host
    public void postInsetsAnimationCallback(Runnable runnable) {
        this.mViewRoot.mChoreographer.postCallback(2, runnable, null);
    }

    @Override // android.view.InsetsController.Host
    public void updateCompatSysUiVisibility(int i, int i2, int i3) {
        this.mViewRoot.updateCompatSysUiVisibility(i, i2, i3);
    }

    @Override // android.view.InsetsController.Host
    public void updateRequestedVisibleTypes(int i, ImeTracker.Token token) {
        try {
            if (this.mViewRoot.mAdded) {
                ImeTracker.forLogging().onProgress(token, 53);
                this.mViewRoot.mWindowSession.updateRequestedVisibleTypes(this.mViewRoot.mWindow, i, token);
            } else {
                ImeTracker.forLogging().onFailed(token, 53);
            }
        } catch (RemoteException e) {
            Log.e("VRInsetsControllerHost", "Failed to call insetsModified", e);
        }
    }

    @Override // android.view.InsetsController.Host
    public void updateAnimatingTypes(int i, ImeTracker.Token token) {
        if (this.mViewRoot != null) {
            ImeTracker.forLogging().onProgress(token, 71);
            this.mViewRoot.updateAnimatingTypes(i, token);
        } else {
            ImeTracker.forLogging().onFailed(token, 71);
        }
    }

    @Override // android.view.InsetsController.Host
    public boolean hasAnimationCallbacks() {
        if (this.mViewRoot.mView == null) {
            return false;
        }
        return this.mViewRoot.mView.hasWindowInsetsAnimationCallback();
    }

    @Override // android.view.InsetsController.Host
    public void setSystemBarsAppearance(int i, int i2) {
        InsetsFlags insetsFlags = this.mViewRoot.mWindowAttributes.insetsFlags;
        int i3 = (i & i2) | (insetsFlags.appearance & (~i2));
        if (insetsFlags.appearance != i3) {
            insetsFlags.appearance = i3;
            this.mViewRoot.mWindowAttributesChanged = true;
            this.mViewRoot.scheduleTraversals();
        }
    }

    @Override // android.view.InsetsController.Host
    public int getSystemBarsAppearance() {
        return this.mViewRoot.mWindowAttributes.insetsFlags.appearance;
    }

    @Override // android.view.InsetsController.Host
    public void setSystemBarsBehavior(int i) {
        if (this.mViewRoot.mWindowAttributes.insetsFlags.behavior != i) {
            this.mViewRoot.mWindowAttributes.insetsFlags.behavior = i;
            this.mViewRoot.mWindowAttributesChanged = true;
            this.mViewRoot.scheduleTraversals();
        }
    }

    @Override // android.view.InsetsController.Host
    public int getSystemBarsBehavior() {
        return this.mViewRoot.mWindowAttributes.insetsFlags.behavior;
    }

    @Override // android.view.InsetsController.Host
    public void releaseSurfaceControlFromRt(final SurfaceControl surfaceControl) {
        if (this.mViewRoot.mView != null && this.mViewRoot.mView.isHardwareAccelerated()) {
            this.mViewRoot.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: android.view.ViewRootInsetsControllerHost$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public final void onFrameDraw(long j) {
                    surfaceControl.release();
                }
            });
            this.mViewRoot.mView.invalidate();
        } else {
            surfaceControl.release();
        }
    }

    @Override // android.view.InsetsController.Host
    public InputMethodManager getInputMethodManager() {
        return (InputMethodManager) this.mViewRoot.mContext.getSystemService(InputMethodManager.class);
    }

    @Override // android.view.InsetsController.Host
    public String getRootViewTitle() {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.getTitle().toString();
    }

    public ViewRootImpl getViewRoot() {
        return this.mViewRoot;
    }

    @Override // android.view.InsetsController.Host
    public Context getRootViewContext() {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl != null) {
            return viewRootImpl.mContext;
        }
        return null;
    }

    @Override // android.view.InsetsController.Host
    public int dipToPx(int i) {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl != null) {
            return viewRootImpl.dipToPx(i);
        }
        return 0;
    }

    @Override // android.view.InsetsController.Host
    public IBinder getWindowToken() {
        View view;
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl == null || (view = viewRootImpl.getView()) == null) {
            return null;
        }
        return view.getWindowToken();
    }

    @Override // android.view.InsetsController.Host
    public CompatibilityInfo.Translator getTranslator() {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        if (viewRootImpl != null) {
            return viewRootImpl.mTranslator;
        }
        return null;
    }

    @Override // android.view.InsetsController.Host
    public boolean shouldIgnoreInsetsAnimation() {
        return this.mViewRoot.shouldIgnoreInsetsAnimation();
    }

    @Override // android.view.InsetsController.Host
    public void applyInsetsHintSandboxingIfNeeded(InsetsSourceControl[] insetsSourceControlArr) {
        this.mViewRoot.applyInsetsHintSandboxingIfNeeded(insetsSourceControlArr);
    }

    @Override // android.view.InsetsController.Host
    public boolean isRelaunchingRemoved() {
        return this.mViewRoot.isRelaunchingRemoved();
    }

    @Override // android.view.InsetsController.Host
    public boolean isHandlingPointerEvent() {
        ViewRootImpl viewRootImpl = this.mViewRoot;
        return viewRootImpl != null && viewRootImpl.isHandlingPointerEvent();
    }

    private boolean isVisibleToUser() {
        return this.mViewRoot.getHostVisibility() == 0;
    }
}
