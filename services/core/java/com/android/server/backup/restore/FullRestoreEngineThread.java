package com.android.server.backup.restore;

import android.os.ParcelFileDescriptor;
import java.io.InputStream;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FullRestoreEngineThread implements Runnable {
    public final FullRestoreEngine mEngine;
    public final InputStream mEngineStream;

    public FullRestoreEngineThread(FullRestoreEngine fullRestoreEngine, ParcelFileDescriptor parcelFileDescriptor) {
        this.mEngine = fullRestoreEngine;
        fullRestoreEngine.setRunning(true);
        this.mEngineStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
    }

    @Override // java.lang.Runnable
    public final void run() {
        while (this.mEngine.mRunning.get()) {
            try {
                FullRestoreEngine fullRestoreEngine = this.mEngine;
                fullRestoreEngine.restoreOneFile(this.mEngineStream, false, fullRestoreEngine.mBuffer, fullRestoreEngine.mOnlyPackage, fullRestoreEngine.mAllowApks, fullRestoreEngine.mEphemeralOpToken, fullRestoreEngine.mMonitor);
            } finally {
                IoUtils.closeQuietly(this.mEngineStream);
            }
        }
    }

    public final void waitForResult() {
        FullRestoreEngine fullRestoreEngine = this.mEngine;
        synchronized (fullRestoreEngine.mRunning) {
            while (fullRestoreEngine.mRunning.get()) {
                try {
                    fullRestoreEngine.mRunning.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
        fullRestoreEngine.mResult.get();
    }
}
