package com.android.server.wm;

import android.os.Debug;
import android.util.Slog;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TransientLaunchOverlayToken extends WindowToken {
    @Override // com.android.server.wm.WindowContainer
    public final TransientLaunchOverlayToken asTransientLaunchOverlay() {
        return this;
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.print("visibleRequested=");
        printWriter.println(this.mVisibleRequested);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        return false;
    }

    @Override // com.android.server.wm.WindowToken, com.android.server.wm.WindowContainer
    public final void removeImmediately() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent != null) {
            displayContent.mTransientLaunchOverlayTokens.remove(this);
        }
        super.removeImmediately();
    }

    public final void setVisibility(boolean z) {
        if (this.mVisibleRequested != z) {
            Transition transition = this.mTransitionController.mCollectingTransition;
            if (transition != null && transition.hasTransientLaunch() && z) {
                transition.collect(this, false);
            }
            Slog.i("TransientLaunchOverlayToken", "setVisibility, visible=" + z + ", t=" + this + ", caller=" + Debug.getCallers(3));
            setVisibleRequested(z);
            scheduleAnimation();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showSurfaceOnCreation() {
        return false;
    }

    @Override // com.android.server.wm.WindowToken
    public final String toString() {
        if (this.stringName == null) {
            this.stringName = "TransientLaunchOverlayToken{" + Integer.toHexString(System.identityHashCode(this)) + " token=" + this.token + '}';
        }
        return this.stringName;
    }
}
