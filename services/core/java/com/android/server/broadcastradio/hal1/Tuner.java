package com.android.server.broadcastradio.hal1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import java.util.List;
import java.util.Map;

class Tuner extends ITuner.Stub {
    public static final String TAG = "BcRadio1Srv.Tuner";
    public final ITunerCallback mClientCallback;
    public final IBinder.DeathRecipient mDeathRecipient;
    public final long mNativeContext;
    public int mRegion;
    public final TunerCallback mTunerCallback;
    public final boolean mWithAudio;
    public final Object mLock = new Object();
    public boolean mIsClosed = false;
    public boolean mIsMuted = false;

    public Tuner(ITunerCallback iTunerCallback, int i, int i2, boolean z, int i3) {
        this.mClientCallback = iTunerCallback;
        this.mTunerCallback = new TunerCallback(this, iTunerCallback, i);
        this.mRegion = i2;
        this.mWithAudio = z;
        this.mNativeContext = nativeInit(i, z, i3);
        IBinder.DeathRecipient deathRecipient =
                new IBinder
                        .DeathRecipient() { // from class:
                                            // com.android.server.broadcastradio.hal1.Tuner$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        Tuner.this.close();
                    }
                };
        this.mDeathRecipient = deathRecipient;
        try {
            iTunerCallback.asBinder().linkToDeath(deathRecipient, 0);
        } catch (RemoteException unused) {
            close();
        }
    }

    private native void nativeCancel(long j);

    private native void nativeCancelAnnouncement(long j);

    private native void nativeClose(long j);

    private native void nativeFinalize(long j);

    private native RadioManager.BandConfig nativeGetConfiguration(long j, int i);

    private native byte[] nativeGetImage(long j, int i);

    private native List nativeGetProgramList(long j, Map map);

    private native long nativeInit(int i, boolean z, int i2);

    private native boolean nativeIsAnalogForced(long j);

    private native void nativeScan(long j, boolean z, boolean z2);

    private native void nativeSetAnalogForced(long j, boolean z);

    private native void nativeSetConfiguration(long j, RadioManager.BandConfig bandConfig);

    private native boolean nativeStartBackgroundScan(long j);

    private native void nativeStep(long j, boolean z, boolean z2);

    private native void nativeTune(long j, ProgramSelector programSelector);

    public final void cancel() {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot cancel on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeCancel(this.mNativeContext);
        }
    }

    public final void cancelAnnouncement() {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot cancel announcement on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeCancelAnnouncement(this.mNativeContext);
        }
    }

    public final boolean checkConfiguredLocked() {
        if (this.mTunerCallback.mInitialConfigurationDone) {
            return true;
        }
        Slogf.w(TAG, "Initial configuration is still pending, skipping the operation");
        return false;
    }

    public final void checkNotClosedLocked() {
        if (this.mIsClosed) {
            throw new IllegalStateException("Tuner is closed, no further operations are allowed");
        }
    }

    public final void close() {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    return;
                }
                this.mIsClosed = true;
                this.mTunerCallback.detach();
                this.mClientCallback.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
                nativeClose(this.mNativeContext);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void finalize() throws Throwable {
        nativeFinalize(this.mNativeContext);
        super /*java.lang.Object*/.finalize();
    }

    public final RadioManager.BandConfig getConfiguration() {
        RadioManager.BandConfig nativeGetConfiguration;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeGetConfiguration = nativeGetConfiguration(this.mNativeContext, this.mRegion);
        }
        return nativeGetConfiguration;
    }

    public final Bitmap getImage(int i) {
        byte[] nativeGetImage;
        if (i == 0) {
            throw new IllegalArgumentException("Image ID is missing");
        }
        synchronized (this.mLock) {
            nativeGetImage = nativeGetImage(this.mNativeContext, i);
        }
        if (nativeGetImage == null || nativeGetImage.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(nativeGetImage, 0, nativeGetImage.length);
    }

    public final Map getParameters(List list) {
        throw new UnsupportedOperationException("Not supported by HAL 1.x");
    }

    public final List getProgramList(Map map) {
        List nativeGetProgramList;
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                nativeGetProgramList = nativeGetProgramList(this.mNativeContext, map);
                if (nativeGetProgramList == null) {
                    throw new IllegalStateException("Program list is not ready");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return nativeGetProgramList;
    }

    public final boolean isClosed() {
        return this.mIsClosed;
    }

    public final boolean isConfigFlagSet(int i) {
        boolean nativeIsAnalogForced;
        if (i != 2) {
            throw new UnsupportedOperationException("Not supported by HAL 1.x");
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeIsAnalogForced = nativeIsAnalogForced(this.mNativeContext);
        }
        return nativeIsAnalogForced;
    }

    public final boolean isConfigFlagSupported(int i) {
        return i == 2;
    }

    public final boolean isMuted() {
        boolean z;
        if (!this.mWithAudio) {
            Slogf.w(TAG, "Tuner did not request audio, pretending it was muted");
            return true;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            z = this.mIsMuted;
        }
        return z;
    }

    public final void seek(boolean z, boolean z2) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot seek on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (checkConfiguredLocked()) {
                    nativeScan(this.mNativeContext, z, z2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setConfigFlag(int i, boolean z) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot set config flag for HAL 1.x client from non-current user");
        } else {
            if (i != 2) {
                throw new UnsupportedOperationException("Not supported by HAL 1.x");
            }
            synchronized (this.mLock) {
                checkNotClosedLocked();
                nativeSetAnalogForced(this.mNativeContext, z);
            }
        }
    }

    public final void setConfiguration(RadioManager.BandConfig bandConfig) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot set configuration for HAL 1.x client from non-current user");
            return;
        }
        if (bandConfig == null) {
            throw new IllegalArgumentException("The argument must not be a null pointer");
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeSetConfiguration(this.mNativeContext, bandConfig);
            this.mRegion = bandConfig.getRegion();
        }
    }

    public final void setMuted(boolean z) {
        if (!this.mWithAudio) {
            throw new IllegalStateException("Can't operate on mute - no audio requested");
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (this.mIsMuted == z) {
                    return;
                }
                this.mIsMuted = z;
                Slogf.w(TAG, "Mute via RadioService is not implemented - please handle it via app");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Map setParameters(Map map) {
        throw new UnsupportedOperationException("Not supported by HAL 1.x");
    }

    public final boolean startBackgroundScan() {
        boolean nativeStartBackgroundScan;
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot start background scan on HAL 1.x client from non-current user");
            return false;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            nativeStartBackgroundScan = nativeStartBackgroundScan(this.mNativeContext);
        }
        return nativeStartBackgroundScan;
    }

    public final void startProgramListUpdates(ProgramList.Filter filter) {
        if (RadioServiceUserController.isCurrentOrSystemUser()) {
            this.mTunerCallback.startProgramListUpdates(filter);
        } else {
            Slogf.w(
                    TAG,
                    "Cannot start program list updates on HAL 1.x client from non-current user");
        }
    }

    public final void step(boolean z, boolean z2) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot step on HAL 1.x client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (checkConfiguredLocked()) {
                    nativeStep(this.mNativeContext, z, z2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopProgramListUpdates() {
        if (RadioServiceUserController.isCurrentOrSystemUser()) {
            this.mTunerCallback.stopProgramListUpdates();
        } else {
            Slogf.w(
                    TAG,
                    "Cannot stop program list updates on HAL 1.x client from non-current user");
        }
    }

    public final void tune(ProgramSelector programSelector) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(TAG, "Cannot tune on HAL 1.x client from non-current user");
            return;
        }
        if (programSelector == null) {
            throw new IllegalArgumentException("The argument must not be a null pointer");
        }
        Slogf.i(TAG, "Tuning to " + programSelector);
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (checkConfiguredLocked()) {
                    nativeTune(this.mNativeContext, programSelector);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
