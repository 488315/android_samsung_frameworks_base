package com.android.server.devicepolicy;

import android.os.PowerManager;
import android.os.RecoverySystem;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public final class NonAbUpdateInstaller extends UpdateInstaller {
    @Override // com.android.server.devicepolicy.UpdateInstaller
    public final void installUpdateInThread() {
        try {
            RecoverySystem.installPackage(this.mContext, this.mCopiedUpdateFile);
            File file = this.mCopiedUpdateFile;
            if (file != null && file.exists()) {
                this.mCopiedUpdateFile.delete();
            }
            ((PowerManager) this.mInjector.mContext.getSystemService(PowerManager.class))
                    .reboot("deviceowner");
        } catch (IOException e) {
            Log.w("UpdateInstaller", "IO error while trying to install non AB update.", e);
            notifyCallbackOnError(1, Log.getStackTraceString(e));
        }
    }
}
