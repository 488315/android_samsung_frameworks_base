package com.android.server.wm;

import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.util.Slog;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class TransientLaunchOverlayToken extends WindowToken {
    @Override // com.android.server.wm.WindowContainer
    public TransientLaunchOverlayToken asTransientLaunchOverlay() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean showSurfaceOnCreation() {
        return false;
    }

    public TransientLaunchOverlayToken(WindowManagerService windowManagerService, IBinder iBinder, boolean z, DisplayContent displayContent, boolean z2, Bundle bundle) {
        super(windowManagerService, iBinder, 2632, z, displayContent, z2, false, false, bundle);
        displayContent.addTransientLaunchOverlayToken(this);
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public void removeImmediately() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.removeTransientLaunchOverlayToken(this);
        }
        super.removeImmediately();
    }

    public void setVisibility(boolean z) {
        if (this.mVisibleRequested != z) {
            Transition collectingTransition = this.mTransitionController.getCollectingTransition();
            if (collectingTransition != null && collectingTransition.hasTransientLaunch() && z) {
                collectingTransition.collect(this);
            }
            Slog.i("TransientLaunchOverlayToken", "setVisibility, visible=" + z + ", t=" + this + ", caller=" + Debug.getCallers(3));
            setVisibleRequested(z);
            scheduleAnimation();
        }
    }

    @Override // com.android.server.wm.WindowToken
    public String toString() {
        if (this.stringName == null) {
            this.stringName = "TransientLaunchOverlayToken{" + Integer.toHexString(System.identityHashCode(this)) + " token=" + this.token + '}';
        }
        return this.stringName;
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.print("visibleRequested=");
        printWriter.println(this.mVisibleRequested);
    }
}
