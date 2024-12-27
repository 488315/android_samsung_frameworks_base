package com.android.server.systemcaptions;

import android.os.UserHandle;
import android.util.Slog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RemoteSystemCaptionsManagerService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        RemoteSystemCaptionsManagerService remoteSystemCaptionsManagerService =
                (RemoteSystemCaptionsManagerService) obj;
        switch (this.$r8$classId) {
            case 0:
                synchronized (remoteSystemCaptionsManagerService.mLock) {
                    try {
                        if (remoteSystemCaptionsManagerService.mService == null
                                && !remoteSystemCaptionsManagerService.mBinding) {
                            if (remoteSystemCaptionsManagerService.mVerbose) {
                                Slog.v(
                                        "RemoteSystemCaptionsManagerService",
                                        "handleEnsureBound(): binding");
                            }
                            remoteSystemCaptionsManagerService.mBinding = true;
                            if (!remoteSystemCaptionsManagerService.mContext.bindServiceAsUser(
                                    remoteSystemCaptionsManagerService.mIntent,
                                    remoteSystemCaptionsManagerService.mServiceConnection,
                                    67112961,
                                    remoteSystemCaptionsManagerService.mHandler,
                                    new UserHandle(remoteSystemCaptionsManagerService.mUserId))) {
                                Slog.w(
                                        "RemoteSystemCaptionsManagerService",
                                        "Could not bind to "
                                                + remoteSystemCaptionsManagerService.mIntent
                                                + " with flags 67112961");
                                remoteSystemCaptionsManagerService.mBinding = false;
                                remoteSystemCaptionsManagerService.mService = null;
                            }
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            default:
                if (remoteSystemCaptionsManagerService.mVerbose) {
                    Slog.v("RemoteSystemCaptionsManagerService", "handleDestroy()");
                }
                synchronized (remoteSystemCaptionsManagerService.mLock) {
                    try {
                        if (remoteSystemCaptionsManagerService.mDestroyed) {
                            if (remoteSystemCaptionsManagerService.mVerbose) {
                                Slog.v(
                                        "RemoteSystemCaptionsManagerService",
                                        "handleDestroy(): Already destroyed");
                            }
                            return;
                        }
                        remoteSystemCaptionsManagerService.mDestroyed = true;
                        if (remoteSystemCaptionsManagerService.mService != null
                                || remoteSystemCaptionsManagerService.mBinding) {
                            remoteSystemCaptionsManagerService.mBinding = false;
                            remoteSystemCaptionsManagerService.mService = null;
                            if (remoteSystemCaptionsManagerService.mVerbose) {
                                Slog.v(
                                        "RemoteSystemCaptionsManagerService",
                                        "ensureUnbound(): unbinding");
                            }
                            remoteSystemCaptionsManagerService.mContext.unbindService(
                                    remoteSystemCaptionsManagerService.mServiceConnection);
                        }
                        return;
                    } finally {
                    }
                }
        }
    }
}
