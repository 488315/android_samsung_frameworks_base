package com.android.wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.IScrollCaptureResponseListener;
import android.view.IWindow;
import android.view.IWindowManager;
import android.view.IWindowSessionCallback;
import android.view.InputEvent;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.ScrollCaptureResponse;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.inputmethod.ImeTracker;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;
import android.window.InputTransferToken;
import com.android.internal.os.IResultReceiver;
import com.android.wm.shell.common.DisplayController;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class SystemWindows {
    public final DisplayController mDisplayController;
    public final AnonymousClass1 mDisplayListener;
    public final SparseArray mPerDisplay = new SparseArray();
    public final HashMap mViewRoots = new HashMap();
    public final IWindowManager mWmService;

    public class PerDisplay {
        public final int mDisplayId;
        public final SparseArray mWwms = new SparseArray();

        public PerDisplay(int i) {
            this.mDisplayId = i;
        }

        public final void setShellRootAccessibilityWindow(View view) {
            int i = this.mDisplayId;
            if (((SysUiWindowManager) this.mWwms.get(1)) == null) {
                return;
            }
            try {
                SystemWindows systemWindows = SystemWindows.this;
                systemWindows.mWmService.setShellRootAccessibilityWindow(i, 1, view != null ? ((SurfaceControlViewHost) systemWindows.mViewRoots.get(view)).getWindowToken() : null);
            } catch (RemoteException e) {
                Slog.e("SystemWindows", "Error setting accessibility window for " + i + ":1", e);
            }
        }
    }

    public class SysUiWindowManager extends WindowlessWindowManager {
        public final HashMap mLeashForWindow;

        public SysUiWindowManager(SystemWindows systemWindows, int i, Context context, SurfaceControl surfaceControl, ContainerWindow containerWindow) {
            super(context.getResources().getConfiguration(), surfaceControl, (InputTransferToken) null);
            this.mLeashForWindow = new HashMap();
        }

        public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
            SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setContainerLayer().setName("SystemWindowLeash").setHidden(false).setParent(((WindowlessWindowManager) this).mRootSurface).setCallsite("SysUiWIndowManager#attachToParentSurface").build();
            synchronized (this) {
                this.mLeashForWindow.put(iWindow.asBinder(), surfaceControlBuild);
            }
            return surfaceControlBuild;
        }

        public final SurfaceControl getSurfaceControlForWindow(View view) {
            SurfaceControl surfaceControl;
            synchronized (this) {
                surfaceControl = (SurfaceControl) this.mLeashForWindow.get(getWindowBinder(view));
            }
            return surfaceControl;
        }

        public final void remove(IBinder iBinder) {
            super.remove(iBinder);
            synchronized (this) {
                new SurfaceControl.Transaction().remove((SurfaceControl) this.mLeashForWindow.get(iBinder)).apply();
                this.mLeashForWindow.remove(iBinder);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.common.DisplayController$OnDisplaysChangedListener, com.android.wm.shell.common.SystemWindows$1] */
    public SystemWindows(DisplayController displayController, IWindowManager iWindowManager) {
        ?? r0 = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.common.SystemWindows.1
            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
                PerDisplay perDisplay = (PerDisplay) SystemWindows.this.mPerDisplay.get(i);
                if (perDisplay == null) {
                    return;
                }
                for (int i2 = 0; i2 < perDisplay.mWwms.size(); i2++) {
                    ((SysUiWindowManager) perDisplay.mWwms.valueAt(i2)).setConfiguration(configuration);
                }
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayAdded(int i) {
            }

            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.mDisplayListener = r0;
        this.mWmService = iWindowManager;
        this.mDisplayController = displayController;
        displayController.addDisplayWindowListener(r0, -1);
        try {
            iWindowManager.openSession(new IWindowSessionCallback.Stub(this) { // from class: com.android.wm.shell.common.SystemWindows.2
                public final void onAnimatorScaleChanged(float f) {
                }
            });
        } catch (RemoteException e) {
            Slog.e("SystemWindows", "Unable to create layer", e);
        }
    }

    public final void addView(View view, WindowManager.LayoutParams layoutParams, int i) {
        SurfaceControl surfaceControlAddShellRoot;
        PerDisplay perDisplay = (PerDisplay) this.mPerDisplay.get(i);
        if (perDisplay == null) {
            perDisplay = new PerDisplay(i);
            this.mPerDisplay.put(i, perDisplay);
        }
        SysUiWindowManager sysUiWindowManager = (SysUiWindowManager) perDisplay.mWwms.get(1);
        int i2 = perDisplay.mDisplayId;
        SystemWindows systemWindows = SystemWindows.this;
        if (sysUiWindowManager == null) {
            ContainerWindow containerWindow = new ContainerWindow();
            sysUiWindowManager = null;
            try {
                surfaceControlAddShellRoot = systemWindows.mWmService.addShellRoot(i2, containerWindow, 1);
            } catch (RemoteException unused) {
                surfaceControlAddShellRoot = null;
            }
            if (surfaceControlAddShellRoot == null) {
                Slog.e("SystemWindows", "Unable to get root surfacecontrol for systemui");
            } else {
                SysUiWindowManager sysUiWindowManager2 = new SysUiWindowManager(SystemWindows.this, perDisplay.mDisplayId, systemWindows.mDisplayController.getDisplayContext(i2), surfaceControlAddShellRoot, containerWindow);
                perDisplay.mWwms.put(1, sysUiWindowManager2);
                sysUiWindowManager = sysUiWindowManager2;
            }
        }
        if (sysUiWindowManager == null) {
            Slog.e("SystemWindows", "Unable to create systemui root");
            return;
        }
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(view.getContext(), systemWindows.mDisplayController.mDisplayManager.getDisplay(i2), sysUiWindowManager, "SystemWindows");
        layoutParams.flags |= 16777216;
        surfaceControlViewHost.setView(view, layoutParams);
        systemWindows.mViewRoots.put(view, surfaceControlViewHost);
        perDisplay.setShellRootAccessibilityWindow(view);
    }

    public final InputTransferToken getFocusGrantToken(View view) {
        SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) this.mViewRoots.get(view);
        if (surfaceControlViewHost != null) {
            return surfaceControlViewHost.getInputTransferToken();
        }
        Slog.e("SystemWindows", "Couldn't get focus grant token since view does not exist in SystemWindow:" + view);
        return null;
    }

    public final SurfaceControl getViewSurface(View view) {
        for (int i = 0; i < this.mPerDisplay.size(); i++) {
            for (int i2 = 0; i2 < ((PerDisplay) this.mPerDisplay.valueAt(i)).mWwms.size(); i2++) {
                SurfaceControl surfaceControlForWindow = ((SysUiWindowManager) ((PerDisplay) this.mPerDisplay.valueAt(i)).mWwms.valueAt(i2)).getSurfaceControlForWindow(view);
                if (surfaceControlForWindow != null) {
                    return surfaceControlForWindow;
                }
            }
        }
        return null;
    }

    public final void setShellRootAccessibilityWindow(View view) {
        PerDisplay perDisplay = (PerDisplay) this.mPerDisplay.get(0);
        if (perDisplay == null) {
            return;
        }
        perDisplay.setShellRootAccessibilityWindow(view);
    }

    public class ContainerWindow extends IWindow.Stub {
        public final void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
            try {
                iScrollCaptureResponseListener.onScrollCaptureResponse(new ScrollCaptureResponse.Builder().setDescription("Not Implemented").build());
            } catch (RemoteException unused) {
            }
        }

        public final void closeSystemDialogs(String str) {
        }

        public final void dispatchAppVisibility(boolean z) {
        }

        public final void dispatchDragEvent(DragEvent dragEvent) {
        }

        public final void dispatchDragEventUpdated(DragEvent dragEvent) {
        }

        public final void dispatchLetterboxDirectionChanged(int i) {
        }

        public final void dispatchSPenGestureEvent(InputEvent[] inputEventArr) {
        }

        public final void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
        }

        public final void dumpWindow(ParcelFileDescriptor parcelFileDescriptor) {
        }

        public final void invalidateForScreenShot(boolean z) {
        }

        public final void windowFocusInTaskChanged(boolean z) {
        }

        public final void dispatchGetNewSurface() {
        }

        public final void dispatchWindowShown() {
        }

        public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) {
        }

        public final void moved(int i, int i2) {
        }

        public final void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        }

        public final void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) {
        }

        public final void hideInsets(int i, boolean z, ImeTracker.Token token) {
        }

        public final void showInsets(int i, boolean z, ImeTracker.Token token) {
        }

        public final void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        }

        public final void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
        }

        public final void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
        }
    }
}
