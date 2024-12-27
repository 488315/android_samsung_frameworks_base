package com.android.server.broadcastradio.hal1;

import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;

import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class BroadcastRadioService {
    public static final String TAG = "BcRadio1Srv";
    public final long mNativeContext = nativeInit();
    public final Object mLock = new Object();

    private native void nativeFinalize(long j);

    private native long nativeInit();

    private native List nativeLoadModules(long j);

    private native Tuner nativeOpenTuner(
            long j,
            int i,
            RadioManager.BandConfig bandConfig,
            boolean z,
            ITunerCallback iTunerCallback);

    public final void finalize() throws Throwable {
        nativeFinalize(this.mNativeContext);
        super.finalize();
    }

    public final List loadModules() {
        List list;
        synchronized (this.mLock) {
            List nativeLoadModules = nativeLoadModules(this.mNativeContext);
            Objects.requireNonNull(nativeLoadModules);
            list = nativeLoadModules;
        }
        return list;
    }

    public final ITuner openTuner(
            int i, RadioManager.BandConfig bandConfig, boolean z, ITunerCallback iTunerCallback) {
        Tuner nativeOpenTuner;
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.e(TAG, "Cannot open tuner on HAL 1.x client for non-current user");
            throw new IllegalStateException("Cannot open tuner for non-current user");
        }
        synchronized (this.mLock) {
            nativeOpenTuner =
                    nativeOpenTuner(this.mNativeContext, i, bandConfig, z, iTunerCallback);
        }
        return nativeOpenTuner;
    }
}
