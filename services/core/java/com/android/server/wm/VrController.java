package com.android.server.wm;

import android.service.vr.IPersistentVrStateCallbacks;
import android.util.Slog;

import com.android.server.am.ActivityManagerService;
import com.android.server.vr.VrManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VrController {
    public static final int[] ORIG_ENUMS = {0, 1, 2};
    public static final int[] PROTO_ENUMS = {0, 1, 2};
    public final Object mGlobalAmLock;
    public VrManagerService.LocalService mVrService;
    public volatile int mVrState = 0;
    public int mVrRenderThreadTid = 0;
    public final AnonymousClass1 mPersistentVrModeListener =
            new IPersistentVrStateCallbacks
                    .Stub() { // from class: com.android.server.wm.VrController.1
                public final void onPersistentVrStateChanged(boolean z) {
                    synchronized (VrController.this.mGlobalAmLock) {
                        try {
                            if (z) {
                                VrController.this.setVrRenderThreadLocked(0, 3, true);
                                VrController.this.mVrState |= 2;
                            } else {
                                VrController vrController = VrController.this;
                                if ((vrController.mVrState & 2) != 0) {
                                    vrController.updateVrRenderThreadLocked(0, true);
                                }
                                VrController.this.mVrState &= -3;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.wm.VrController$1] */
    public VrController(Object obj) {
        this.mGlobalAmLock = obj;
    }

    public final int setVrRenderThreadLocked(int i, int i2, boolean z) {
        boolean z2 = (this.mVrState & 1) != 0;
        boolean z3 = (this.mVrState & 2) != 0;
        if (z2 && !z3 && i2 == 3) {
            return updateVrRenderThreadLocked(i, z);
        }
        if (!z) {
            Slog.w(
                    "VrController",
                    "Failed to set VR thread, "
                            .concat(
                                    !z2
                                            ? "system not in VR mode."
                                            : z3
                                                    ? "system in persistent VR mode."
                                                    : "caller is not the current top"
                                                          + " application."));
        }
        return this.mVrRenderThreadTid;
    }

    public final String toString() {
        return String.format(
                "[VrState=0x%x,VrRenderThreadTid=%d]",
                Integer.valueOf(this.mVrState), Integer.valueOf(this.mVrRenderThreadTid));
    }

    public final int updateVrRenderThreadLocked(int i, boolean z) {
        int i2 = this.mVrRenderThreadTid;
        if (i2 == i) {
            return i2;
        }
        if (i2 > 0) {
            ActivityManagerService.scheduleAsRegularPriority(i2, z);
            this.mVrRenderThreadTid = 0;
        }
        if (i > 0) {
            this.mVrRenderThreadTid = i;
            ActivityManagerService.scheduleAsFifoPriority(i, z);
        }
        return this.mVrRenderThreadTid;
    }
}
