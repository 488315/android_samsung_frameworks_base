package com.android.server.broadcastradio.hal2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.broadcastradio.V2_0.ConfigFlag;
import android.hardware.broadcastradio.V2_0.IBroadcastRadio;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.net.INetd;
import android.os.Binder;
import android.os.HwParcel;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.MutableBoolean;
import android.util.MutableInt;

import com.android.server.broadcastradio.RadioEventLogger;
import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class TunerSession extends ITuner.Stub {
    public final ITunerCallback mCallback;
    public final RadioEventLogger mEventLogger;
    public final ITunerSession$Proxy mHwSession;
    public final RadioModule mModule;
    public final int mUserId;
    public final Object mLock = new Object();
    public boolean mIsClosed = false;
    public boolean mIsMuted = false;
    public ProgramInfoCache mProgramInfoCache = null;
    public RadioManager.BandConfig mDummyConfig = null;

    public TunerSession(
            RadioModule radioModule,
            ITunerSession$Proxy iTunerSession$Proxy,
            ITunerCallback iTunerCallback) {
        this.mModule = radioModule;
        Objects.requireNonNull(iTunerSession$Proxy);
        this.mHwSession = iTunerSession$Proxy;
        this.mUserId = Binder.getCallingUserHandle().getIdentifier();
        Objects.requireNonNull(iTunerCallback);
        this.mCallback = iTunerCallback;
        this.mEventLogger = new RadioEventLogger("BcRadio2Srv.session");
    }

    public final void cancel() {
        HwParcel hwParcel;
        HwParcel hwParcel2;
        Slogf.i("BcRadio2Srv.session", "Cancel");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w("BcRadio2Srv.session", "Cannot cancel on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            ITunerSession$Proxy iTunerSession$Proxy = this.mHwSession;
            Objects.requireNonNull(iTunerSession$Proxy);
            try {
                hwParcel = new HwParcel();
                hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
                hwParcel2 = new HwParcel();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
            try {
                iTunerSession$Proxy.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                hwParcel2.release();
            } catch (Throwable th) {
                hwParcel2.release();
                throw th;
            }
        }
    }

    public final void cancelAnnouncement() {
        Slogf.w(
                "BcRadio2Srv.session",
                "Announcements control doesn't involve cancelling at the HAL level in HAL 2.0");
    }

    public final void checkNotClosedLocked() {
        if (this.mIsClosed) {
            throw new IllegalStateException("Tuner is closed, no further operations are allowed");
        }
    }

    public final void close() {
        this.mEventLogger.logRadioEvent("Close", new Object[0]);
        close(null);
    }

    public final void close(Integer num) {
        this.mEventLogger.logRadioEvent("Close on error %d", num);
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    return;
                }
                this.mIsClosed = true;
                if (num != null) {
                    try {
                        this.mCallback.onError(num.intValue());
                    } catch (RemoteException e) {
                        Slogf.w("BcRadio2Srv.session", "mCallback.onError() failed: ", e);
                    }
                }
                RadioModule radioModule = this.mModule;
                synchronized (radioModule.mLock) {
                    radioModule.onTunerSessionsClosedLocked(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dispatchClientUpdateChunks(List list) {
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                this.mCallback.onProgramListUpdated((ProgramList.Chunk) it.next());
            } catch (RemoteException e) {
                Slogf.w("BcRadio2Srv.session", "mCallback.onProgramListUpdated() failed: ", e);
            }
        }
    }

    public final void dumpInfo(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("TunerSession\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("HIDL HAL Session: %s\n", new Object[] {this.mHwSession});
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf(
                        "Is session closed? %s\n", new Object[] {this.mIsClosed ? "Yes" : "No"});
                indentingPrintWriter.printf(
                        "Is muted? %s\n", new Object[] {this.mIsMuted ? "Yes" : "No"});
                indentingPrintWriter.printf(
                        "ProgramInfoCache: %s\n", new Object[] {this.mProgramInfoCache});
                indentingPrintWriter.printf("Config: %s\n", new Object[] {this.mDummyConfig});
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Tuner session events:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mEventLogger.mEventLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final RadioManager.BandConfig getConfiguration() {
        RadioManager.BandConfig bandConfig;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            bandConfig = this.mDummyConfig;
        }
        return bandConfig;
    }

    public final Bitmap getImage(int i) {
        ArrayList arrayList;
        this.mEventLogger.logRadioEvent("Get image for %d", Integer.valueOf(i));
        RadioModule radioModule = this.mModule;
        radioModule.mEventLogger.logRadioEvent("Get image for id %d", Integer.valueOf(i));
        if (i == 0) {
            throw new IllegalArgumentException("Image ID is missing");
        }
        try {
            IBroadcastRadio.Proxy proxy = (IBroadcastRadio.Proxy) radioModule.mService;
            proxy.getClass();
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::IBroadcastRadio");
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                proxy.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                arrayList = hwParcel2.readInt8Vector();
                hwParcel2.release();
            } catch (Throwable th) {
                hwParcel2.release();
                throw th;
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            arrayList = null;
        }
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bArr[i2] = ((Byte) arrayList.get(i2)).byteValue();
        }
        if (size == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bArr, 0, size);
    }

    public final Map getParameters(List list) {
        Object obj;
        Map vendorInfoFromHal;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            try {
                obj = new TunerSession$$ExternalSyntheticLambda0(2, this, list).exec();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                obj = null;
            }
            vendorInfoFromHal = Convert.vendorInfoFromHal((List) obj);
        }
        return vendorInfoFromHal;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsClosed;
        }
        return z;
    }

    public final boolean isConfigFlagSet(int i) {
        boolean z;
        this.mEventLogger.logRadioEvent("Is ConfigFlagSet for %s", ConfigFlag.toString(i));
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                MutableInt mutableInt = new MutableInt(1);
                MutableBoolean mutableBoolean = new MutableBoolean(false);
                try {
                    this.mHwSession.isConfigFlagSet(
                            i,
                            new TunerSession$$ExternalSyntheticLambda0(
                                    0, mutableInt, mutableBoolean));
                    Convert.throwOnError(mutableInt.value, "isConfigFlagSet");
                    z = mutableBoolean.value;
                } catch (RemoteException e) {
                    throw new RuntimeException("Failed to check flag " + ConfigFlag.toString(i), e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean isConfigFlagSupported(int i) {
        try {
            isConfigFlagSet(i);
            return true;
        } catch (IllegalStateException | UnsupportedOperationException unused) {
            return false;
        }
    }

    public final boolean isMuted() {
        boolean z;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            z = this.mIsMuted;
        }
        return z;
    }

    public final void seek(boolean z, boolean z2) {
        this.mEventLogger.logRadioEvent(
                "Seek with direction %s, skipSubChannel? %s",
                z ? INetd.IF_STATE_DOWN : INetd.IF_STATE_UP, z2 ? "yes" : "no");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w("BcRadio2Srv.session", "Cannot scan on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            Convert.throwOnError(this.mHwSession.scan(!z, z2), "step");
        }
    }

    public final void setConfigFlag(int i, boolean z) {
        this.mEventLogger.logRadioEvent(
                "Set ConfigFlag  %s = %b", ConfigFlag.toString(i), Boolean.valueOf(z));
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadio2Srv.session",
                    "Cannot set config flag for HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            Convert.throwOnError(this.mHwSession.setConfigFlag(i, z), "setConfigFlag");
        }
    }

    public final void setConfiguration(final RadioManager.BandConfig bandConfig) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadio2Srv.session",
                    "Cannot set configuration for HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            Objects.requireNonNull(bandConfig);
            this.mDummyConfig = bandConfig;
        }
        Slogf.i(
                "BcRadio2Srv.session",
                "Ignoring setConfiguration - not applicable for broadcastradio HAL 2.0");
        RadioModule radioModule = this.mModule;
        RadioModule.AidlCallbackRunnable aidlCallbackRunnable =
                new RadioModule
                        .AidlCallbackRunnable() { // from class:
                                                  // com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda4
                    @Override // com.android.server.broadcastradio.hal2.RadioModule.AidlCallbackRunnable
                    public final void run(ITunerCallback iTunerCallback) {
                        iTunerCallback.onConfigurationChanged(bandConfig);
                    }
                };
        radioModule.getClass();
        radioModule.fireLater(
                new RadioModule$$ExternalSyntheticLambda3(0, radioModule, aidlCallbackRunnable));
    }

    public final void setMuted(boolean z) {
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                if (this.mIsMuted == z) {
                    return;
                }
                this.mIsMuted = z;
                Slogf.w(
                        "BcRadio2Srv.session",
                        "Mute via RadioService is not implemented - please handle it via app");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Map setParameters(Map map) {
        Object obj;
        Map vendorInfoFromHal;
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadio2Srv.session",
                    "Cannot set parameters for HAL 2.0 client from non-current user");
            return new ArrayMap();
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            try {
                obj = new TunerSession$$ExternalSyntheticLambda0(1, this, map).exec();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                obj = null;
            }
            vendorInfoFromHal = Convert.vendorInfoFromHal((List) obj);
        }
        return vendorInfoFromHal;
    }

    public final boolean startBackgroundScan() {
        Slogf.w(
                "BcRadio2Srv.session",
                "Explicit background scan trigger is not supported with HAL 2.0");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadio2Srv.session",
                    "Cannot start background scan on HAL 2.0 client from non-current user");
            return false;
        }
        RadioModule radioModule = this.mModule;
        TunerSession$$ExternalSyntheticLambda1 tunerSession$$ExternalSyntheticLambda1 =
                new TunerSession$$ExternalSyntheticLambda1();
        radioModule.getClass();
        radioModule.fireLater(
                new RadioModule$$ExternalSyntheticLambda3(
                        0, radioModule, tunerSession$$ExternalSyntheticLambda1));
        return true;
    }

    public final void startProgramListUpdates(ProgramList.Filter filter) {
        this.mEventLogger.logRadioEvent("start programList updates %s", filter);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadio2Srv.session",
                    "Cannot start program list updates on HAL 2.0 client from non-current user");
            return;
        }
        if (filter == null) {
            filter = new ProgramList.Filter(new ArraySet(), new ArraySet(), true, false);
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            this.mProgramInfoCache = new ProgramInfoCache(filter);
        }
        RadioModule radioModule = this.mModule;
        synchronized (radioModule.mLock) {
            radioModule.onTunerSessionProgramListFilterChangedLocked(this);
        }
    }

    public final void step(boolean z, boolean z2) {
        this.mEventLogger.logRadioEvent(
                "Step with direction %s, skipSubChannel?  %s",
                z ? INetd.IF_STATE_DOWN : INetd.IF_STATE_UP, z2 ? "yes" : "no");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w("BcRadio2Srv.session", "Cannot step on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            Convert.throwOnError(this.mHwSession.step(!z), "step");
        }
    }

    public final void stopProgramListUpdates() {
        this.mEventLogger.logRadioEvent("Stop programList updates", new Object[0]);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadio2Srv.session",
                    "Cannot stop program list updates on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            this.mProgramInfoCache = null;
        }
        RadioModule radioModule = this.mModule;
        synchronized (radioModule.mLock) {
            radioModule.onTunerSessionProgramListFilterChangedLocked(this);
        }
    }

    public final void tune(ProgramSelector programSelector) {
        this.mEventLogger.logRadioEvent("Tune with selector %s", programSelector);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w("BcRadio2Srv.session", "Cannot tune on HAL 2.0 client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            Convert.throwOnError(
                    this.mHwSession.tune(Convert.programSelectorToHal(programSelector)), "tune");
        }
    }
}
