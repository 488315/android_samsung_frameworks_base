package com.android.internal.view;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.DragEvent;
import android.view.IScrollCaptureResponseListener;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.InputEvent;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.ScrollCaptureResponse;
import android.view.inputmethod.ImeTracker;
import android.window.ActivityWindowInfo;
import android.window.ClientWindowFrames;

import com.android.internal.os.IResultReceiver;

import com.samsung.android.content.smartclip.SmartClipRemoteRequestInfo;

import java.io.IOException;

public class BaseIWindow extends IWindow.Stub {
    private IWindowSession mSession;

    public void setSession(IWindowSession session) {
        this.mSession = session;
    }

    public void resized(
            ClientWindowFrames frames,
            boolean reportDraw,
            MergedConfiguration mergedConfiguration,
            InsetsState insetsState,
            boolean forceLayout,
            boolean alwaysConsumeSystemBars,
            int displayId,
            int seqId,
            boolean dragResizing,
            ActivityWindowInfo activityWindowInfo) {
        if (reportDraw) {
            try {
                this.mSession.finishDrawing(this, null, seqId);
            } catch (RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void insetsControlChanged(
            InsetsState insetsState, InsetsSourceControl.Array activeControls) {}

    @Override // android.view.IWindow
    public void showInsets(int types, boolean fromIme, ImeTracker.Token statsToken) {}

    @Override // android.view.IWindow
    public void hideInsets(int types, boolean fromIme, ImeTracker.Token statsToken) {}

    public void moved(int newX, int newY) {}

    public void dispatchAppVisibility(boolean visible) {}

    @Override // android.view.IWindow
    public void dispatchGetNewSurface() {}

    @Override // android.view.IWindow
    public void executeCommand(String command, String parameters, ParcelFileDescriptor out) {
        if (out != null) {
            try {
                out.closeWithError("Unsupported command " + command);
            } catch (IOException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void closeSystemDialogs(String reason) {}

    public void dispatchWallpaperOffsets(
            float x, float y, float xStep, float yStep, float zoom, boolean sync) {
        if (sync) {
            try {
                this.mSession.wallpaperOffsetsComplete(asBinder());
            } catch (RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void dispatchDragEvent(DragEvent event) {
        if (event.getAction() == 3) {
            try {
                this.mSession.reportDropResult(this, false);
            } catch (RemoteException e) {
            }
        }
    }

    public void dispatchWallpaperCommand(
            String action, int x, int y, int z, Bundle extras, boolean sync) {
        if (sync) {
            try {
                this.mSession.wallpaperCommandComplete(asBinder(), null);
            } catch (RemoteException e) {
            }
        }
    }

    @Override // android.view.IWindow
    public void dispatchWindowShown() {}

    @Override // android.view.IWindow
    public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) {}

    @Override // android.view.IWindow
    public void requestScrollCapture(IScrollCaptureResponseListener listener) {
        try {
            listener.onScrollCaptureResponse(
                    new ScrollCaptureResponse.Builder().setDescription("Not Implemented").build());
        } catch (RemoteException e) {
        }
    }

    @Override // android.view.IWindow
    public void dumpWindow(ParcelFileDescriptor pfd) {}

    @Override // android.view.IWindow
    public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo request) {}

    @Override // android.view.IWindow
    public void dispatchLetterboxDirectionChanged(int direction) {}

    @Override // android.view.IWindow
    public void dispatchDragEventUpdated(DragEvent event) {}

    @Override // android.view.IWindow
    public void windowFocusInTaskChanged(boolean hasFocus) {}

    @Override // android.view.IWindow
    public void dispatchSPenGestureEvent(InputEvent[] events) {}

    @Override // android.view.IWindow
    public void invalidateForScreenShot(boolean forceMode) {}
}
