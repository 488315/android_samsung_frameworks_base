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

/* loaded from: classes4.dex */
public class BaseIWindow extends IWindow.Stub {
    private IWindowSession mSession;

    @Override // android.view.IWindow
    public void closeSystemDialogs(String str) {
    }

    public void dispatchAppVisibility(boolean z) {
    }

    @Override // android.view.IWindow
    public void dispatchDragEventUpdated(DragEvent dragEvent) {
    }

    @Override // android.view.IWindow
    public void dispatchGetNewSurface() {
    }

    @Override // android.view.IWindow
    public void dispatchLetterboxDirectionChanged(int i) {
    }

    @Override // android.view.IWindow
    public void dispatchSPenGestureEvent(InputEvent[] inputEventArr) {
    }

    @Override // android.view.IWindow
    public void dispatchSmartClipRemoteRequest(SmartClipRemoteRequestInfo smartClipRemoteRequestInfo) {
    }

    @Override // android.view.IWindow
    public void dispatchWindowShown() {
    }

    @Override // android.view.IWindow
    public void dumpWindow(ParcelFileDescriptor parcelFileDescriptor) {
    }

    @Override // android.view.IWindow
    public void hideInsets(int i, boolean z, ImeTracker.Token token) {
    }

    @Override // android.view.IWindow
    public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl.Array array) {
    }

    @Override // android.view.IWindow
    public void invalidateForScreenShot(boolean z) {
    }

    public void moved(int i, int i2) {
    }

    @Override // android.view.IWindow
    public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) {
    }

    @Override // android.view.IWindow
    public void showInsets(int i, boolean z, ImeTracker.Token token) {
    }

    @Override // android.view.IWindow
    public void windowFocusInTaskChanged(boolean z) {
    }

    public void setSession(IWindowSession iWindowSession) {
        this.mSession = iWindowSession;
    }

    public void resized(ClientWindowFrames clientWindowFrames, boolean z, MergedConfiguration mergedConfiguration, InsetsState insetsState, boolean z2, boolean z3, int i, int i2, boolean z4, ActivityWindowInfo activityWindowInfo) {
        if (z) {
            try {
                this.mSession.finishDrawing(this, null, i2);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.IWindow
    public void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.closeWithError("Unsupported command " + str);
            } catch (IOException unused) {
            }
        }
    }

    public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, float f5, boolean z) {
        if (z) {
            try {
                this.mSession.wallpaperOffsetsComplete(asBinder());
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.IWindow
    public void dispatchDragEvent(DragEvent dragEvent) {
        if (dragEvent.getAction() == 3) {
            try {
                this.mSession.reportDropResult(this, false);
            } catch (RemoteException unused) {
            }
        }
    }

    public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        if (z) {
            try {
                this.mSession.wallpaperCommandComplete(asBinder(), null);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.view.IWindow
    public void requestScrollCapture(IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        try {
            iScrollCaptureResponseListener.onScrollCaptureResponse(new ScrollCaptureResponse.Builder().setDescription("Not Implemented").build());
        } catch (RemoteException unused) {
        }
    }
}
