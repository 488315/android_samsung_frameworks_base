package com.android.server.broadcastradio.aidl;

import android.app.compat.CompatChanges;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.broadcastradio.ConfigFlag$$;
import android.hardware.broadcastradio.IBroadcastRadio;
import android.hardware.broadcastradio.ProgramIdentifier;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.net.INetd;
import android.os.Binder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;

import com.android.server.broadcastradio.RadioEventLogger;
import com.android.server.broadcastradio.RadioServiceUserController;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TunerSession extends ITuner.Stub {
    public final ITunerCallback mCallback;
    public boolean mIsClosed;
    public boolean mIsMuted;
    public final Object mLock = new Object();
    public final RadioEventLogger mLogger;
    public final RadioModule mModule;
    public RadioManager.BandConfig mPlaceHolderConfig;
    public ProgramInfoCache mProgramInfoCache;
    public final IBroadcastRadio mService;
    public final int mUid;
    public final int mUserId;

    public TunerSession(
            RadioModule radioModule,
            IBroadcastRadio iBroadcastRadio,
            ITunerCallback iTunerCallback) {
        this.mModule = radioModule;
        Objects.requireNonNull(iBroadcastRadio, "service cannot be null");
        this.mService = iBroadcastRadio;
        this.mUserId = Binder.getCallingUserHandle().getIdentifier();
        Objects.requireNonNull(iTunerCallback, "callback cannot be null");
        this.mCallback = iTunerCallback;
        this.mUid = Binder.getCallingUid();
        this.mLogger = new RadioEventLogger("BcRadioAidlSrv.session");
    }

    public final void cancel() {
        Slogf.i("BcRadioAidlSrv.session", "Cancel");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot cancel on AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    ((IBroadcastRadio.Stub.Proxy) this.mService).cancel();
                } catch (RemoteException e) {
                    Slogf.e("BcRadioAidlSrv.session", "Failed to cancel tuner session");
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancelAnnouncement() {
        Slogf.w(
                "BcRadioAidlSrv.session",
                "Announcements control doesn't involve cancelling at the HAL level in AIDL");
    }

    public final void checkNotClosedLocked() {
        if (this.mIsClosed) {
            throw new IllegalStateException("Tuner is closed, no further operations are allowed");
        }
    }

    public final void close() {
        this.mLogger.logRadioEvent("Close tuner", new Object[0]);
        close(null);
    }

    public final void close(Integer num) {
        if (num == null) {
            this.mLogger.logRadioEvent("Close tuner session on error null", new Object[0]);
        } else {
            this.mLogger.logRadioEvent("Close tuner session on error %d", num);
        }
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
                        Slogf.w("BcRadioAidlSrv.session", e, "mCallback.onError(%s) failed", num);
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
        for (int i = 0; i < list.size(); i++) {
            try {
                if (CompatChanges.isChangeEnabled(261770108L, this.mUid)) {
                    this.mCallback.onProgramListUpdated((ProgramList.Chunk) list.get(i));
                } else {
                    this.mCallback.onProgramListUpdated(
                            ConversionUtils.convertChunkToTargetSdkVersion(
                                    (ProgramList.Chunk) list.get(i), this.mUid));
                }
            } catch (RemoteException e) {
                Slogf.w(
                        "BcRadioAidlSrv.session",
                        e,
                        "mCallback.onProgramListUpdated() failed",
                        new Object[0]);
            }
        }
    }

    public final void dumpInfo(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.printf("TunerSession\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.printf(
                        "Is session closed? %s\n", new Object[] {this.mIsClosed ? "Yes" : "No"});
                indentingPrintWriter.printf(
                        "Is muted? %s\n", new Object[] {this.mIsMuted ? "Yes" : "No"});
                indentingPrintWriter.printf(
                        "ProgramInfoCache: %s\n", new Object[] {this.mProgramInfoCache});
                indentingPrintWriter.printf("Config: %s\n", new Object[] {this.mPlaceHolderConfig});
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.printf("Tuner session events:\n", new Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mLogger.mEventLogger.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final RadioManager.BandConfig getConfiguration() {
        RadioManager.BandConfig bandConfig;
        synchronized (this.mLock) {
            checkNotClosedLocked();
            bandConfig = this.mPlaceHolderConfig;
        }
        return bandConfig;
    }

    public final Bitmap getImage(int i) {
        this.mLogger.logRadioEvent("Get image for %d", Integer.valueOf(i));
        RadioModule radioModule = this.mModule;
        radioModule.mLogger.logRadioEvent("Get image for id = %d", Integer.valueOf(i));
        if (i == 0) {
            throw new IllegalArgumentException("Image ID is missing");
        }
        try {
            byte[] image = ((IBroadcastRadio.Stub.Proxy) radioModule.mService).getImage(i);
            if (image == null || image.length == 0) {
                return null;
            }
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final Map getParameters(List list) {
        Map vendorInfoFromHalVendorKeyValues;
        this.mLogger.logRadioEvent("Get parameters ", new Object[0]);
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    vendorInfoFromHalVendorKeyValues =
                            ConversionUtils.vendorInfoFromHalVendorKeyValues(
                                    ((IBroadcastRadio.Stub.Proxy) this.mService)
                                            .getParameters((String[]) list.toArray(new String[0])));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return vendorInfoFromHalVendorKeyValues;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsClosed;
        }
        return z;
    }

    public final boolean isConfigFlagSet(int i) {
        boolean isConfigFlagSet;
        this.mLogger.logRadioEvent("is ConfigFlag %s set? ", ConfigFlag$$.toString(i));
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    try {
                        isConfigFlagSet =
                                ((IBroadcastRadio.Stub.Proxy) this.mService).isConfigFlagSet(i);
                    } catch (RuntimeException e) {
                        throw ConversionUtils.throwOnError(e, "isConfigFlagSet");
                    }
                } catch (RemoteException e2) {
                    throw new RuntimeException(
                            "Failed to check flag " + ConfigFlag$$.toString(i), e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return isConfigFlagSet;
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
        this.mLogger.logRadioEvent(
                "Seek with direction %s, skipSubChannel? %s",
                z ? INetd.IF_STATE_DOWN : INetd.IF_STATE_UP, z2 ? "yes" : "no");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot scan on AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    ((IBroadcastRadio.Stub.Proxy) this.mService).seek(!z, z2);
                } catch (RuntimeException e) {
                    throw ConversionUtils.throwOnError(e, "seek");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setConfigFlag(int i, boolean z) {
        this.mLogger.logRadioEvent(
                "set ConfigFlag %s to %b ", ConfigFlag$$.toString(i), Boolean.valueOf(z));
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot set config flag for AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    ((IBroadcastRadio.Stub.Proxy) this.mService).setConfigFlag(i, z);
                } catch (RuntimeException e) {
                    throw ConversionUtils.throwOnError(e, "setConfigFlag");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setConfiguration(final RadioManager.BandConfig bandConfig) {
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot set configuration for AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            checkNotClosedLocked();
            Objects.requireNonNull(bandConfig, "config cannot be null");
            this.mPlaceHolderConfig = bandConfig;
        }
        Slogf.i(
                "BcRadioAidlSrv.session",
                "Ignoring setConfiguration - not applicable for broadcastradio HAL AIDL");
        RadioModule radioModule = this.mModule;
        RadioModule.AidlCallbackRunnable aidlCallbackRunnable =
                new RadioModule
                        .AidlCallbackRunnable() { // from class:
                                                  // com.android.server.broadcastradio.aidl.TunerSession$$ExternalSyntheticLambda1
                    @Override // com.android.server.broadcastradio.aidl.RadioModule.AidlCallbackRunnable
                    public final void run(ITunerCallback iTunerCallback, int i) {
                        iTunerCallback.onConfigurationChanged(bandConfig);
                    }
                };
        radioModule.getClass();
        radioModule.fireLater(
                new RadioModule$$ExternalSyntheticLambda0(0, radioModule, aidlCallbackRunnable));
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
                        "BcRadioAidlSrv.session",
                        "Mute %b via RadioService is not implemented - please handle it via app",
                        Boolean.valueOf(z));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Map setParameters(Map map) {
        Map vendorInfoFromHalVendorKeyValues;
        this.mLogger.logRadioEvent("Set parameters ", new Object[0]);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot set parameters for AIDL HAL client from non-current user");
            return new ArrayMap();
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    vendorInfoFromHalVendorKeyValues =
                            ConversionUtils.vendorInfoFromHalVendorKeyValues(
                                    ((IBroadcastRadio.Stub.Proxy) this.mService)
                                            .setParameters(
                                                    ConversionUtils.vendorInfoToHalVendorKeyValues(
                                                            map)));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return vendorInfoFromHalVendorKeyValues;
    }

    public final boolean startBackgroundScan() {
        Slogf.w(
                "BcRadioAidlSrv.session",
                "Explicit background scan trigger is not supported with HAL AIDL");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot start background scan on AIDL HAL client from non-current user");
            return false;
        }
        RadioModule radioModule = this.mModule;
        TunerSession$$ExternalSyntheticLambda0 tunerSession$$ExternalSyntheticLambda0 =
                new TunerSession$$ExternalSyntheticLambda0();
        radioModule.getClass();
        radioModule.fireLater(
                new RadioModule$$ExternalSyntheticLambda0(
                        0, radioModule, tunerSession$$ExternalSyntheticLambda0));
        return true;
    }

    public final void startProgramListUpdates(ProgramList.Filter filter) {
        this.mLogger.logRadioEvent("Start programList updates %s", filter);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot start program list updates on AIDL HAL client from non-current user");
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
        this.mLogger.logRadioEvent(
                "Step with direction %s, skipSubChannel?  %s",
                z ? INetd.IF_STATE_DOWN : INetd.IF_STATE_UP, z2 ? "yes" : "no");
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot step on AIDL HAL client from non-current user");
            return;
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    ((IBroadcastRadio.Stub.Proxy) this.mService).step(!z);
                } catch (RuntimeException e) {
                    throw ConversionUtils.throwOnError(e, "step");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopProgramListUpdates() {
        this.mLogger.logRadioEvent("Stop programList updates", new Object[0]);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot stop program list updates on AIDL HAL client from non-current user");
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
        this.mLogger.logRadioEvent("Tune with selector %s", programSelector);
        if (!RadioServiceUserController.isCurrentOrSystemUser()) {
            Slogf.w(
                    "BcRadioAidlSrv.session",
                    "Cannot tune on AIDL HAL client from non-current user");
            return;
        }
        android.hardware.broadcastradio.ProgramSelector programSelector2 =
                new android.hardware.broadcastradio.ProgramSelector();
        programSelector2.primaryId =
                ConversionUtils.identifierToHalProgramIdentifier(programSelector.getPrimaryId());
        ProgramSelector.Identifier[] secondaryIds = programSelector.getSecondaryIds();
        ArrayList arrayList = new ArrayList(secondaryIds.length);
        for (int i = 0; i < secondaryIds.length; i++) {
            ProgramIdentifier identifierToHalProgramIdentifier =
                    ConversionUtils.identifierToHalProgramIdentifier(secondaryIds[i]);
            if (identifierToHalProgramIdentifier.type != 0) {
                arrayList.add(identifierToHalProgramIdentifier);
            } else {
                Slogf.w("BcRadioAidlSrv.convert", "Invalid secondary id: %s", secondaryIds[i]);
            }
        }
        programSelector2.secondaryIds =
                (ProgramIdentifier[])
                        arrayList.toArray(new ConversionUtils$$ExternalSyntheticLambda0(1));
        if (!ConversionUtils.isValidHalProgramSelector(programSelector2)) {
            programSelector2 = null;
        }
        if (programSelector2 == null) {
            throw new IllegalArgumentException("tune: INVALID_ARGUMENTS for program selector");
        }
        synchronized (this.mLock) {
            try {
                checkNotClosedLocked();
                try {
                    ((IBroadcastRadio.Stub.Proxy) this.mService).tune(programSelector2);
                } catch (RuntimeException e) {
                    throw ConversionUtils.throwOnError(e, "tune");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
