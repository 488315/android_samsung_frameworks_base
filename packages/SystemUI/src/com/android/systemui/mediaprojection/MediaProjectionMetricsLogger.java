package com.android.systemui.mediaprojection;

import android.media.projection.IMediaProjectionManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.mediaprojection.SessionCreationSource;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaProjectionMetricsLogger {
    public final IMediaProjectionManager service;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaProjectionMetricsLogger(IMediaProjectionManager iMediaProjectionManager) {
        this.service = iMediaProjectionManager;
    }

    public final void notifyPermissionRequestDisplayed(int i) {
        try {
            this.service.notifyPermissionRequestDisplayed(i);
        } catch (RemoteException e) {
            Log.e("MediaProjectionMetricsLogger", "Error notifying server of projection displayed", e);
        }
    }

    public final void notifyProjectionInitiated(int i, SessionCreationSource sessionCreationSource) {
        try {
            IMediaProjectionManager iMediaProjectionManager = this.service;
            int i2 = SessionCreationSource.WhenMappings.$EnumSwitchMapping$0[sessionCreationSource.ordinal()];
            int i3 = 1;
            if (i2 != 1) {
                i3 = 3;
                if (i2 != 2) {
                    if (i2 == 3) {
                        i3 = 2;
                    } else {
                        if (i2 != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        i3 = 0;
                    }
                }
            }
            iMediaProjectionManager.notifyPermissionRequestInitiated(i, i3);
        } catch (RemoteException e) {
            Log.e("MediaProjectionMetricsLogger", "Error notifying server of projection initiated", e);
        }
    }
}
