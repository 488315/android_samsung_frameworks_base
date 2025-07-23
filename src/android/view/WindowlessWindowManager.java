package android.view;

import android.content.ClipData;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Log;
import android.util.MergedConfiguration;
import android.view.IWindowSession;
import android.view.InsetsSourceControl;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.window.ClientWindowFrames;
import android.window.InputTransferToken;
import android.window.OnBackInvokedCallbackInfo;
import android.window.WindowContainerToken;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WindowlessWindowManager implements IWindowSession {
    private static final String TAG = "WindowlessWindowManager";
    private final Configuration mConfiguration;
    final InputTransferToken mHostInputTransferToken;
    private final InputTransferToken mInputTransferToken;
    private InsetsState mInsetsState;
    private final WindowlessWindowLayout mLayout;
    private ISurfaceControlViewHostParent mParentInterface;
    private final IWindowSession mRealWm;
    final HashMap<IBinder, ResizeCompleteCallback> mResizeCompletionForWindow;
    protected final SurfaceControl mRootSurface;
    final HashMap<IBinder, State> mStateForWindow;
    private WindowContainerToken mTaskToken;
    private final MergedConfiguration mTmpConfig;
    private final ClientWindowFrames mTmpFrames;

    public interface ResizeCompleteCallback {
        void finished(SurfaceControl.Transaction transaction);
    }

    @Override // android.view.IWindowSession
    public int addToDisplayWithoutInputChannel(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, InsetsState insetsState, Rect rect, float[] fArr) {
        return 0;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // android.view.IWindowSession
    public void cancelDragAndDrop(IBinder iBinder, boolean z) {
    }

    @Override // android.view.IWindowSession
    public boolean cancelDraw(IWindow iWindow) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void clearTspDeadzone(IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void dragRecipientEntered(IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void dragRecipientExited(IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public boolean dropForAccessibility(IWindow iWindow, int i, int i2) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void finishMovingTask(IWindow iWindow) {
    }

    @Override // android.view.IWindowSession
    public void generateDisplayHash(IWindow iWindow, Rect rect, String str, RemoteCallback remoteCallback) {
    }

    @Override // android.view.IWindowSession
    public int getDragDeviceId() {
        return -1;
    }

    @Override // android.view.IWindowSession
    public int getDragPointerId() {
        return -1;
    }

    @Override // android.view.IWindowSession
    public IBinder getDragStateInputToken() {
        return null;
    }

    @Override // android.view.IWindowSession
    public IWindowId getWindowId(IBinder iBinder) {
        return null;
    }

    @Override // android.view.IWindowSession
    public void grantEmbeddedWindowFocus(IWindow iWindow, InputTransferToken inputTransferToken, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void grantInputChannel(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel) {
    }

    @Override // android.view.IWindowSession
    public void grantInputChannelWithTaskToken(int i, SurfaceControl surfaceControl, IBinder iBinder, InputTransferToken inputTransferToken, int i2, int i3, int i4, int i5, IBinder iBinder2, InputTransferToken inputTransferToken2, String str, InputChannel inputChannel, int i6, WindowContainerToken windowContainerToken) {
    }

    @Override // android.view.IWindowSession
    public void notifyImeWindowVisibilityChangedFromClient(IWindow iWindow, boolean z, ImeTracker.Token token) {
    }

    @Override // android.view.IWindowSession
    public void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) {
    }

    @Override // android.view.IWindowSession
    public boolean outOfMemory(IWindow iWindow) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void performClipDataUpdate(ClipData clipData) {
    }

    @Override // android.view.IWindowSession
    public IBinder performDrag(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData) {
        return null;
    }

    @Override // android.view.IWindowSession
    public IBinder performDragWithArea(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, float f, float f2, float f3, float f4, ClipData clipData, RectF rectF, Point point) {
        return null;
    }

    @Override // android.view.IWindowSession
    public void pokeDrawLock(IBinder iBinder) {
    }

    @Override // android.view.IWindowSession
    public void removeWithTaskToken(IBinder iBinder, WindowContainerToken windowContainerToken) throws RemoteException {
    }

    @Override // android.view.IWindowSession
    public void reportDecorViewGestureInterceptionChanged(IWindow iWindow, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void reportDropResult(IWindow iWindow, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void reportKeepClearAreasChanged(IWindow iWindow, List<Rect> list, List<Rect> list2) {
    }

    @Override // android.view.IWindowSession
    public void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) {
    }

    @Override // android.view.IWindowSession
    public void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void setKeyguardWallpaperTouchAllowed(IWindow iWindow, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void setOnBackInvokedCallbackInfo(IWindow iWindow, OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) throws RemoteException {
    }

    @Override // android.view.IWindowSession
    public void setShouldZoomOutWallpaper(IBinder iBinder, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void setTspDeadzone(IWindow iWindow, Bundle bundle) {
    }

    @Override // android.view.IWindowSession
    public void setTspNoteMode(IWindow iWindow, boolean z) {
    }

    @Override // android.view.IWindowSession
    public void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) {
    }

    @Override // android.view.IWindowSession
    public void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) {
    }

    @Override // android.view.IWindowSession
    public void setWallpaperZoomOut(IBinder iBinder, float f) {
    }

    @Override // android.view.IWindowSession
    public boolean startMovingTask(IWindow iWindow, float f, float f2) {
        return false;
    }

    @Override // android.view.IWindowSession
    public void updateAnimatingTypes(IWindow iWindow, int i, ImeTracker.Token token) {
    }

    @Override // android.view.IWindowSession
    public void updateInputChannel(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region) {
    }

    @Override // android.view.IWindowSession
    public void updateInputChannelWithPointerRegion(IBinder iBinder, int i, SurfaceControl surfaceControl, int i2, int i3, int i4, Region region, Region region2) {
    }

    @Override // android.view.IWindowSession
    public void updateTapExcludeRegion(IWindow iWindow, Region region) {
    }

    @Override // android.view.IWindowSession
    public void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) {
    }

    @Override // android.view.IWindowSession
    public void wallpaperOffsetsComplete(IBinder iBinder) {
    }

    private class State {
        Rect mAttachedFrame;
        IWindow mClient;
        int mDisplayId;
        Rect mFrame;
        IBinder mInputChannelToken;
        Region mInputRegion;
        InputTransferToken mInputTransferToken;
        final WindowManager.LayoutParams mLastReportedParams;
        SurfaceControl mLeash;
        final WindowManager.LayoutParams mParams;
        SurfaceControl mSurfaceControl;

        State(WindowlessWindowManager windowlessWindowManager, SurfaceControl surfaceControl, WindowManager.LayoutParams layoutParams, int i, IWindow iWindow, SurfaceControl surfaceControl2, Rect rect) {
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
            this.mParams = layoutParams2;
            this.mLastReportedParams = new WindowManager.LayoutParams();
            this.mSurfaceControl = surfaceControl;
            layoutParams2.copyFrom(layoutParams);
            this.mDisplayId = i;
            this.mClient = iWindow;
            this.mLeash = surfaceControl2;
            this.mFrame = rect;
        }
    }

    public WindowlessWindowManager(Configuration configuration, SurfaceControl surfaceControl, InputTransferToken inputTransferToken) {
        this(configuration, surfaceControl, inputTransferToken, null);
    }

    public WindowlessWindowManager(Configuration configuration, SurfaceControl surfaceControl, InputTransferToken inputTransferToken, WindowContainerToken windowContainerToken) {
        this.mStateForWindow = new HashMap<>();
        this.mResizeCompletionForWindow = new HashMap<>();
        this.mInputTransferToken = new InputTransferToken();
        this.mTmpFrames = new ClientWindowFrames();
        this.mTmpConfig = new MergedConfiguration();
        this.mLayout = new WindowlessWindowLayout();
        this.mTaskToken = windowContainerToken;
        this.mRootSurface = surfaceControl;
        this.mConfiguration = new Configuration(configuration);
        this.mRealWm = WindowManagerGlobal.getWindowSession();
        this.mHostInputTransferToken = inputTransferToken;
    }

    public void setConfiguration(Configuration configuration) {
        this.mConfiguration.setTo(configuration);
    }

    InputTransferToken getInputTransferToken(IBinder iBinder) {
        synchronized (this) {
            if (this.mStateForWindow.isEmpty()) {
                return this.mInputTransferToken;
            }
            State state = this.mStateForWindow.get(iBinder);
            if (state != null) {
                return state.mInputTransferToken;
            }
            Log.w(TAG, "Failed to get focusGrantToken. Returning null token");
            return null;
        }
    }

    void setCompletionCallback(IBinder iBinder, ResizeCompleteCallback resizeCompleteCallback) {
        if (this.mResizeCompletionForWindow.get(iBinder) != null) {
            Log.w(TAG, "Unsupported overlapping resizes");
        }
        this.mResizeCompletionForWindow.put(iBinder, resizeCompleteCallback);
    }

    protected void setTouchRegion(IBinder iBinder, Region region) {
        synchronized (this) {
            State state = this.mStateForWindow.get(iBinder);
            if (state == null) {
                return;
            }
            if (Objects.equals(region, state.mInputRegion)) {
                return;
            }
            state.mInputRegion = region != null ? new Region(region) : null;
            if (state.mInputChannelToken != null) {
                try {
                    this.mRealWm.updateInputChannel(state.mInputChannelToken, state.mDisplayId, state.mSurfaceControl, state.mParams.flags, state.mParams.privateFlags, state.mParams.inputFeatures, state.mInputRegion);
                } catch (RemoteException e) {
                    Log.e(TAG, "Failed to update surface input channel: ", e);
                }
            }
        }
    }

    protected SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
        synchronized (this) {
            if (this.mStateForWindow.isEmpty()) {
                return this.mRootSurface;
            }
            return this.mStateForWindow.get(layoutParams.token).mLeash;
        }
    }

    @Override // android.view.IWindowSession
    public int addToDisplay(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) {
        SurfaceControl build = new SurfaceControl.Builder().setName(layoutParams.getTitle().toString() + "Leash").setCallsite("WindowlessWindowManager.addToDisplay").setParent(getParentSurface(iWindow, layoutParams)).build();
        SurfaceControl build2 = new SurfaceControl.Builder().setFormat(layoutParams.format).setBLASTLayer().setName(layoutParams.getTitle().toString()).setCallsite("WindowlessWindowManager.addToDisplay").setHidden(false).setParent(build).build();
        State state = new State(this, build2, layoutParams, i2, iWindow, build, new Rect());
        synchronized (this) {
            State state2 = this.mStateForWindow.get(layoutParams.token);
            if (state2 != null) {
                state.mAttachedFrame = state2.mFrame;
            }
            if (this.mStateForWindow.isEmpty()) {
                state.mInputTransferToken = this.mInputTransferToken;
            } else {
                state.mInputTransferToken = new InputTransferToken();
            }
            this.mStateForWindow.put(iWindow.asBinder(), state);
        }
        if (state.mAttachedFrame == null) {
            rect.set(0, 0, -1, -1);
        } else {
            rect.set(state.mAttachedFrame);
        }
        fArr[0] = 1.0f;
        if ((layoutParams.inputFeatures & 1) == 0) {
            try {
                IWindowSession iWindowSession = this.mRealWm;
                if (iWindowSession instanceof IWindowSession.Stub) {
                    iWindowSession.grantInputChannel(i2, new SurfaceControl(build2, "WindowlessWindowManager.addToDisplay"), iWindow.asBinder(), this.mHostInputTransferToken, layoutParams.flags, layoutParams.privateFlags, layoutParams.inputFeatures, layoutParams.type, layoutParams.token, state.mInputTransferToken, layoutParams.getTitle().toString(), inputChannel);
                } else if (CoreRune.MW_CAPTION_POPUP && this.mTaskToken != null) {
                    this.mRealWm.grantInputChannelWithTaskToken(i2, build2, iWindow.asBinder(), this.mHostInputTransferToken, layoutParams.flags, layoutParams.privateFlags, layoutParams.inputFeatures, layoutParams.type, layoutParams.token, state.mInputTransferToken, layoutParams.getTitle().toString(), inputChannel, layoutParams.surfaceInsets.left, this.mTaskToken);
                } else {
                    this.mRealWm.grantInputChannel(i2, build2, iWindow.asBinder(), this.mHostInputTransferToken, layoutParams.flags, layoutParams.privateFlags, layoutParams.inputFeatures, layoutParams.type, layoutParams.token, state.mInputTransferToken, layoutParams.getTitle().toString(), inputChannel);
                }
                state.mInputChannelToken = inputChannel != null ? inputChannel.getToken() : null;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to grant input to surface: ", e);
            }
        }
        sendLayoutParamsToParent();
        return isInTouchModeInternal(i2) ? 3 : 2;
    }

    @Override // android.view.IWindowSession
    public int addToDisplayAsUser(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, InputChannel inputChannel, InsetsState insetsState, InsetsSourceControl.Array array, Rect rect, float[] fArr) {
        return addToDisplay(iWindow, layoutParams, i, i2, i4, inputChannel, insetsState, array, rect, fArr);
    }

    @Override // android.view.IWindowSession
    public void remove(IBinder iBinder) throws RemoteException {
        State remove;
        WindowContainerToken windowContainerToken;
        if (CoreRune.MW_CAPTION_POPUP && (windowContainerToken = this.mTaskToken) != null) {
            this.mRealWm.removeWithTaskToken(iBinder, windowContainerToken);
        } else {
            this.mRealWm.remove(iBinder);
        }
        synchronized (this) {
            remove = this.mStateForWindow.remove(iBinder);
        }
        if (remove == null) {
            throw new IllegalArgumentException("Invalid window token (never added or removed already)");
        }
        removeSurface(remove.mSurfaceControl);
        removeSurface(remove.mLeash);
    }

    protected void removeSurface(SurfaceControl surfaceControl) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        try {
            transaction.remove(surfaceControl).apply();
            transaction.close();
        } catch (Throwable th) {
            try {
                transaction.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private boolean isOpaque(WindowManager.LayoutParams layoutParams) {
        if ((layoutParams.surfaceInsets == null || layoutParams.surfaceInsets.left == 0) && layoutParams.surfaceInsets.top == 0 && layoutParams.surfaceInsets.right == 0 && layoutParams.surfaceInsets.bottom == 0) {
            return !PixelFormat.formatHasAlpha(layoutParams.format);
        }
        return false;
    }

    private boolean isInTouchModeInternal(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().isInTouchMode(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to check if the window is in touch mode", e);
            return false;
        }
    }

    protected IBinder getWindowBinder(View view) {
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return viewRootImpl.mWindow.asBinder();
    }

    protected SurfaceControl getSurfaceControl(View view) {
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl == null) {
            return null;
        }
        return getSurfaceControl(viewRootImpl.mWindow);
    }

    protected SurfaceControl getSurfaceControl(IWindow iWindow) {
        State state = this.mStateForWindow.get(iWindow.asBinder());
        if (state == null) {
            return null;
        }
        return state.mSurfaceControl;
    }

    @Override // android.view.IWindowSession
    public int relayout(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, WindowRelayoutResult windowRelayoutResult) {
        ClientWindowFrames clientWindowFrames;
        MergedConfiguration mergedConfiguration;
        SurfaceControl surfaceControl;
        InsetsState insetsState;
        InsetsSourceControl.Array array;
        WindowlessWindowManager windowlessWindowManager;
        IWindow iWindow2;
        WindowManager.LayoutParams layoutParams2;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        if (windowRelayoutResult != null) {
            ClientWindowFrames clientWindowFrames2 = windowRelayoutResult.frames;
            MergedConfiguration mergedConfiguration2 = windowRelayoutResult.mergedConfiguration;
            SurfaceControl surfaceControl2 = windowRelayoutResult.surfaceControl;
            InsetsState insetsState2 = windowRelayoutResult.insetsState;
            array = windowRelayoutResult.activeControls;
            clientWindowFrames = clientWindowFrames2;
            mergedConfiguration = mergedConfiguration2;
            surfaceControl = surfaceControl2;
            insetsState = insetsState2;
            i7 = i;
            i8 = i2;
            i9 = i3;
            i10 = i4;
            i11 = i5;
            i12 = i6;
            windowlessWindowManager = this;
            iWindow2 = iWindow;
            layoutParams2 = layoutParams;
        } else {
            clientWindowFrames = null;
            mergedConfiguration = null;
            surfaceControl = null;
            insetsState = null;
            array = null;
            windowlessWindowManager = this;
            iWindow2 = iWindow;
            layoutParams2 = layoutParams;
            i7 = i;
            i8 = i2;
            i9 = i3;
            i10 = i4;
            i11 = i5;
            i12 = i6;
        }
        return windowlessWindowManager.relayoutInner(iWindow2, layoutParams2, i7, i8, i9, i10, i11, i12, clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array);
    }

    private int relayoutInner(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, ClientWindowFrames clientWindowFrames, MergedConfiguration mergedConfiguration, SurfaceControl surfaceControl, InsetsState insetsState, InsetsSourceControl.Array array) {
        State state;
        InsetsState insetsState2;
        synchronized (this) {
            state = this.mStateForWindow.get(iWindow.asBinder());
        }
        if (state == null) {
            throw new IllegalArgumentException("Invalid window token (never added or removed already)");
        }
        SurfaceControl surfaceControl2 = state.mSurfaceControl;
        SurfaceControl surfaceControl3 = state.mLeash;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        int copyFrom = layoutParams != null ? state.mParams.copyFrom(layoutParams) : 0;
        WindowManager.LayoutParams layoutParams2 = state.mParams;
        ClientWindowFrames clientWindowFrames2 = new ClientWindowFrames();
        clientWindowFrames2.attachedFrame = state.mAttachedFrame;
        this.mLayout.computeFrames(layoutParams2, null, null, null, 0, i, i2, 0, 0.0f, clientWindowFrames2);
        state.mFrame.set(clientWindowFrames2.frame);
        if (clientWindowFrames != null) {
            if (CoreRune.MW_CAPTION_TOOLTIP && (layoutParams2.multiWindowFlags & 8) != 0) {
                updateTooltipBounds(layoutParams2, clientWindowFrames2);
            }
            clientWindowFrames.frame.set(clientWindowFrames2.frame);
            clientWindowFrames.parentFrame.set(clientWindowFrames2.parentFrame);
            clientWindowFrames.displayFrame.set(clientWindowFrames2.displayFrame);
        }
        transaction.setPosition(surfaceControl3, clientWindowFrames2.frame.left, clientWindowFrames2.frame.top);
        if (i3 == 0) {
            transaction.setOpaque(surfaceControl2, isOpaque(layoutParams2)).show(surfaceControl3).apply();
            if (surfaceControl != null) {
                surfaceControl.copyFrom(surfaceControl2, "WindowlessWindowManager.relayout");
            }
        } else {
            transaction.hide(surfaceControl3).apply();
            if (surfaceControl != null) {
                surfaceControl.release();
            }
        }
        if (mergedConfiguration != null) {
            Configuration configuration = this.mConfiguration;
            mergedConfiguration.setConfiguration(configuration, configuration);
        }
        if ((copyFrom & 65540) != 0 && state.mInputChannelToken != null) {
            try {
                IWindowSession iWindowSession = this.mRealWm;
                if (iWindowSession instanceof IWindowSession.Stub) {
                    iWindowSession.updateInputChannel(state.mInputChannelToken, state.mDisplayId, new SurfaceControl(surfaceControl2, "WindowlessWindowManager.relayout"), layoutParams2.flags, layoutParams2.privateFlags, layoutParams2.inputFeatures, state.mInputRegion);
                } else {
                    iWindowSession.updateInputChannel(state.mInputChannelToken, state.mDisplayId, surfaceControl2, layoutParams2.flags, layoutParams2.privateFlags, layoutParams2.inputFeatures, state.mInputRegion);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to update surface input channel: ", e);
            }
        }
        if (insetsState != null && (insetsState2 = this.mInsetsState) != null) {
            insetsState.set(insetsState2);
        }
        sendLayoutParamsToParent();
        return 0;
    }

    @Override // android.view.IWindowSession
    public void relayoutAsync(IWindow iWindow, WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6) {
        relayoutInner(iWindow, layoutParams, i, i2, i3, i4, i5, i6, null, null, null, null, null);
    }

    @Override // android.view.IWindowSession
    public void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region, Rect rect3) {
        setTouchRegion(iWindow.asBinder(), region);
    }

    @Override // android.view.IWindowSession
    public void clearTouchableRegion(IWindow iWindow) {
        setTouchRegion(iWindow.asBinder(), null);
    }

    @Override // android.view.IWindowSession
    public void finishDrawing(IWindow iWindow, SurfaceControl.Transaction transaction, int i) {
        synchronized (this) {
            ResizeCompleteCallback resizeCompleteCallback = this.mResizeCompletionForWindow.get(iWindow.asBinder());
            if (resizeCompleteCallback == null) {
                transaction.apply();
            } else {
                resizeCompleteCallback.finished(transaction);
                this.mResizeCompletionForWindow.remove(iWindow.asBinder());
            }
        }
    }

    @Override // android.view.IWindowSession
    public void updateRequestedVisibleTypes(IWindow iWindow, int i, ImeTracker.Token token) throws RemoteException {
        if (Flags.refactorInsetsController()) {
            this.mRealWm.updateRequestedVisibleTypes(iWindow, i & WindowInsets.Type.ime(), token);
        }
    }

    public void setInsetsState(InsetsState insetsState) {
        this.mInsetsState = insetsState;
        for (State state : this.mStateForWindow.values()) {
            try {
                this.mTmpFrames.frame.set(0, 0, state.mParams.width, state.mParams.height);
                this.mTmpFrames.displayFrame.set(this.mTmpFrames.frame);
                MergedConfiguration mergedConfiguration = this.mTmpConfig;
                Configuration configuration = this.mConfiguration;
                mergedConfiguration.setConfiguration(configuration, configuration);
                state.mClient.resized(this.mTmpFrames, false, this.mTmpConfig, insetsState, false, false, state.mDisplayId, Integer.MAX_VALUE, false, null);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.IWindowSession
    public boolean moveFocusToAdjacentWindow(IWindow iWindow, int i) {
        Log.e(TAG, "Received request to moveFocusToAdjacentWindow on WindowlessWindowManager. We shouldn't get here!");
        return false;
    }

    void setParentInterface(ISurfaceControlViewHostParent iSurfaceControlViewHostParent) {
        ISurfaceControlViewHostParent iSurfaceControlViewHostParent2 = this.mParentInterface;
        if ((iSurfaceControlViewHostParent2 == null ? null : iSurfaceControlViewHostParent2.asBinder()) != (iSurfaceControlViewHostParent != null ? iSurfaceControlViewHostParent.asBinder() : null)) {
            clearLastReportedParams();
        }
        this.mParentInterface = iSurfaceControlViewHostParent;
        sendLayoutParamsToParent();
    }

    private void clearLastReportedParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Iterator<State> it = this.mStateForWindow.values().iterator();
        while (it.hasNext()) {
            it.next().mLastReportedParams.copyFrom(layoutParams);
        }
    }

    private void sendLayoutParamsToParent() {
        if (this.mParentInterface == null) {
            return;
        }
        WindowManager.LayoutParams[] layoutParamsArr = new WindowManager.LayoutParams[this.mStateForWindow.size()];
        boolean z = false;
        int i = 0;
        for (State state : this.mStateForWindow.values()) {
            z |= state.mLastReportedParams.copyFrom(state.mParams) != 0;
            layoutParamsArr[i] = state.mParams;
            i++;
        }
        if (z) {
            try {
                this.mParentInterface.updateParams(layoutParamsArr);
            } catch (RemoteException unused) {
            }
        }
    }

    boolean forwardBackKeyToParent(KeyEvent keyEvent) {
        ISurfaceControlViewHostParent iSurfaceControlViewHostParent = this.mParentInterface;
        if (iSurfaceControlViewHostParent == null) {
            return false;
        }
        try {
            iSurfaceControlViewHostParent.forwardBackKeyToParent(keyEvent);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to forward back key To Parent: ", e);
            return false;
        }
    }

    private void updateTooltipBounds(WindowManager.LayoutParams layoutParams, ClientWindowFrames clientWindowFrames) {
        Rect bounds = this.mConfiguration.windowConfiguration.getBounds();
        Rect maxBounds = this.mConfiguration.windowConfiguration.getMaxBounds();
        if ((layoutParams.multiWindowFlags & 2) != 0) {
            if (clientWindowFrames.frame.left < maxBounds.left) {
                clientWindowFrames.frame.offset(maxBounds.left - clientWindowFrames.frame.left, 0);
                return;
            }
            int min = Math.min(bounds.left + ((bounds.width() - clientWindowFrames.parentFrame.width()) / 2), maxBounds.width() - clientWindowFrames.parentFrame.width());
            if (clientWindowFrames.frame.right + min > maxBounds.right) {
                clientWindowFrames.frame.offset(maxBounds.right - (min + clientWindowFrames.frame.right), 0);
                return;
            }
            return;
        }
        if (bounds.left + clientWindowFrames.frame.left + layoutParams.surfaceInsets.left < maxBounds.left) {
            clientWindowFrames.frame.offset(maxBounds.left - ((bounds.left + clientWindowFrames.frame.left) + layoutParams.surfaceInsets.left), 0);
        } else if (bounds.left + clientWindowFrames.frame.right + layoutParams.surfaceInsets.right > maxBounds.right) {
            clientWindowFrames.frame.offset(maxBounds.right - ((bounds.left + clientWindowFrames.frame.right) + layoutParams.surfaceInsets.right), 0);
        }
    }
}
