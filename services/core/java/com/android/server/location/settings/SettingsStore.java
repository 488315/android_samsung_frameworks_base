package com.android.server.location.settings;

import android.util.AtomicFile;
import android.util.Log;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class SettingsStore {
    public LocationUserSettings mCache;
    public final AtomicFile mFile;
    public boolean mInitialized;

    public SettingsStore(File file) {
        this.mFile = new AtomicFile(file);
    }

    public synchronized void deleteFile() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        BackgroundThread.getExecutor().execute(new SettingsStore$$ExternalSyntheticLambda1(this, countDownLatch, 0));
        countDownLatch.await();
    }

    public synchronized void flushFile() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        BackgroundThread.getExecutor().execute(new SettingsStore$$ExternalSyntheticLambda0(countDownLatch));
        countDownLatch.await();
    }

    public final synchronized void initializeCache() {
        if (!this.mInitialized) {
            if (this.mFile.exists()) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(this.mFile.openRead());
                    try {
                        this.mCache = read(dataInputStream.readInt(), dataInputStream);
                        Preconditions.checkState(true);
                        dataInputStream.close();
                    } catch (Throwable th) {
                        try {
                            dataInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    Log.e("LocationManagerService", "error reading location settings (" + this.mFile + "), falling back to defaults", e);
                }
            }
            if (this.mCache == null) {
                try {
                    this.mCache = read(Integer.MAX_VALUE, new DataInputStream(new ByteArrayInputStream(new byte[0])));
                    Preconditions.checkState(true);
                } catch (IOException e2) {
                    throw new AssertionError(e2);
                }
            }
            this.mInitialized = true;
        }
    }

    public abstract LocationUserSettings read(int i, DataInput dataInput);
}
