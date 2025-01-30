package com.android.p038wm.shell.common;

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
import android.view.SurfaceSession;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.view.inputmethod.ImeTracker;
import android.window.ClientWindowFrames;
import com.android.internal.os.IResultReceiver;
import com.android.p038wm.shell.common.DisplayController;
import com.android.systemui.AbstractC0970x34f4116a;
import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemWindows {
    public final DisplayController mDisplayController;
    public final C38661 mDisplayListener;
    public final SparseArray mPerDisplay = new SparseArray();
    public final HashMap mViewRoots = new HashMap();
    public final IWindowManager mWmService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PerDisplay {
        public final int mDisplayId;
        public final SparseArray mWwms = new SparseArray();

        public PerDisplay(int i) {
            this.mDisplayId = i;
        }

        public final void setShellRootAccessibilityWindow(View view, int i) {
            int i2 = this.mDisplayId;
            if (((SysUiWindowManager) this.mWwms.get(i)) == null) {
                return;
            }
            try {
                SystemWindows systemWindows = SystemWindows.this;
                systemWindows.mWmService.setShellRootAccessibilityWindow(i2, i, view != null ? ((SurfaceControlViewHost) systemWindows.mViewRoots.get(view)).getWindowToken() : null);
            } catch (RemoteException e) {
                Slog.e("SystemWindows", AbstractC0970x34f4116a.m94m("Error setting accessibility window for ", i2, ":", i), e);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SysUiWindowManager extends WindowlessWindowManager {
        public final HashMap mLeashForWindow;

        public SysUiWindowManager(SystemWindows systemWindows, int i, Context context, SurfaceControl surfaceControl, ContainerWindow containerWindow) {
            super(context.getResources().getConfiguration(), surfaceControl, (IBinder) null);
            this.mLeashForWindow = new HashMap();
        }

        public final SurfaceControl getParentSurface(IWindow iWindow, WindowManager.LayoutParams layoutParams) {
            SurfaceControl build = new SurfaceControl.Builder(new SurfaceSession()).setContainerLayer().setName("SystemWindowLeash").setHidden(false).setParent(((WindowlessWindowManager) this).mRootSurface).setCallsite("SysUiWIndowManager#attachToParentSurface").build();
            synchronized (this) {
                this.mLeashForWindow.put(iWindow.asBinder(), build);
            }
            return build;
        }

        public final SurfaceControl getSurfaceControlForWindow(View view) {
            SurfaceControl surfaceControl;
            synchronized (this) {
                surfaceControl = (SurfaceControl) this.mLeashForWindow.get(getWindowBinder(view));
            }
            return surfaceControl;
        }

        public final void remove(IWindow iWindow) {
            super.remove(iWindow);
            synchronized (this) {
                IBinder asBinder = iWindow.asBinder();
                new SurfaceControl.Transaction().remove((SurfaceControl) this.mLeashForWindow.get(asBinder)).apply();
                this.mLeashForWindow.remove(asBinder);
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
                int i2 = 0;
                while (true) {
                    SparseArray sparseArray = perDisplay.mWwms;
                    if (i2 >= sparseArray.size()) {
                        return;
                    }
                    ((SysUiWindowManager) sparseArray.valueAt(i2)).setConfiguration(configuration);
                    i2++;
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
        displayController.addDisplayWindowListener(r0);
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
        SurfaceControl surfaceControl;
        SparseArray sparseArray = this.mPerDisplay;
        PerDisplay perDisplay = (PerDisplay) sparseArray.get(0);
        if (perDisplay == null) {
            perDisplay = new PerDisplay(0);
            sparseArray.put(0, perDisplay);
        }
        SparseArray sparseArray2 = perDisplay.mWwms;
        SysUiWindowManager sysUiWindowManager = (SysUiWindowManager) sparseArray2.get(i);
        int i2 = perDisplay.mDisplayId;
        SystemWindows systemWindows = SystemWindows.this;
        if (sysUiWindowManager == null) {
            ContainerWindow containerWindow = new ContainerWindow();
            sysUiWindowManager = null;
            try {
                surfaceControl = systemWindows.mWmService.addShellRoot(i2, containerWindow, i);
            } catch (RemoteException unused) {
                surfaceControl = null;
            }
            if (surfaceControl == null) {
                Slog.e("SystemWindows", "Unable to get root surfacecontrol for systemui");
            } else {
                sysUiWindowManager = new SysUiWindowManager(SystemWindows.this, perDisplay.mDisplayId, systemWindows.mDisplayController.getDisplayContext(i2), surfaceControl, containerWindow);
                sparseArray2.put(i, sysUiWindowManager);
            }
        }
        if (sysUiWindowManager == null) {
            Slog.e("SystemWindows", "Unable to create systemui root");
            return;
        }
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(view.getContext(), systemWindows.mDisplayController.getDisplay(i2), sysUiWindowManager, "SystemWindows");
        layoutParams.flags |= 16777216;
        surfaceControlViewHost.setView(view, layoutParams);
        systemWindows.mViewRoots.put(view, surfaceControlViewHost);
        perDisplay.setShellRootAccessibilityWindow(view, i);
    }

    public final IBinder getFocusGrantToken(View view) {
        SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) this.mViewRoots.get(view);
        if (surfaceControlViewHost != null) {
            return surfaceControlViewHost.getFocusGrantToken();
        }
        Slog.e("SystemWindows", "Couldn't get focus grant token since view does not exist in SystemWindow:" + view);
        return null;
    }

    public final SurfaceControl getViewSurface(View view) {
        int i = 0;
        while (true) {
            SparseArray sparseArray = this.mPerDisplay;
            if (i >= sparseArray.size()) {
                return null;
            }
            for (int i2 = 0; i2 < ((PerDisplay) sparseArray.valueAt(i)).mWwms.size(); i2++) {
                SurfaceControl surfaceControlForWindow = ((SysUiWindowManager) ((PerDisplay) sparseArray.valueAt(i)).mWwms.valueAt(i2)).getSurfaceControlForWindow(view);
                if (surfaceControlForWindow != null) {
                    return surfaceControlForWindow;
                }
            }
            i++;
        }
    }

    public final void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
        SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) this.mViewRoots.get(view);
        if (surfaceControlViewHost != null) {
            view.setLayoutParams(layoutParams);
            surfaceControlViewHost.relayout((WindowManager.LayoutParams) layoutParams);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ContainerWindow extends IWindow.Stub {
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

        public final void invalidateForScreenShot(boolean z) {
        }

        public final void windowFocusInTaskChanged(boolean z) {
        }

        public final void dispatchGetNewSurface() {
        }

        public final void dispatchWindowShown() {
        }

        public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
        }

        public final void moved(int i, int i2) {
        }

        public final void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
        }

        public final void updatePointerIcon(float f, float f2) {
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

        public final void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4) {
        }
    }
}
