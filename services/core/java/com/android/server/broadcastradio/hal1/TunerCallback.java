package com.android.server.broadcastradio.hal1;

import android.hardware.radio.ITunerCallback;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.server.utils.Slogf;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
class TunerCallback implements ITunerCallback {
    public static final String TAG = "BcRadio1Srv.TunerCallback";
    public final ITunerCallback mClientCallback;
    public final long mNativeContext;
    public final Tuner mTuner;
    public final AtomicReference mProgramListFilter = new AtomicReference();
    public boolean mInitialConfigurationDone = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface RunnableThrowingRemoteException {
        void run();
    }

    public TunerCallback(Tuner tuner, ITunerCallback iTunerCallback, int i) {
        this.mTuner = tuner;
        this.mClientCallback = iTunerCallback;
        this.mNativeContext = nativeInit(tuner, i);
    }

    private native void nativeDetach(long j);

    private native void nativeFinalize(long j);

    private native long nativeInit(Tuner tuner, int i);

    public final IBinder asBinder() {
        throw new RuntimeException("Not a binder");
    }

    public final void detach() {
        nativeDetach(this.mNativeContext);
    }

    public final void dispatch(RunnableThrowingRemoteException runnableThrowingRemoteException) {
        try {
            runnableThrowingRemoteException.run();
        } catch (RemoteException e) {
            Slogf.e(TAG, "client died", e);
        }
    }

    public final void finalize() throws Throwable {
        nativeFinalize(this.mNativeContext);
        super.finalize();
    }

    public final void handleHwFailure() {
        onError(0);
        this.mTuner.close();
    }

    public final boolean isInitialConfigurationDone() {
        return this.mInitialConfigurationDone;
    }

    public final /* synthetic */ void lambda$onAntennaState$5(boolean z) throws RemoteException {
        this.mClientCallback.onAntennaState(z);
    }

    public final /* synthetic */ void lambda$onBackgroundScanAvailabilityChange$6(boolean z)
            throws RemoteException {
        this.mClientCallback.onBackgroundScanAvailabilityChange(z);
    }

    public final /* synthetic */ void lambda$onBackgroundScanComplete$7() throws RemoteException {
        this.mClientCallback.onBackgroundScanComplete();
    }

    public final /* synthetic */ void lambda$onConfigurationChanged$1(
            RadioManager.BandConfig bandConfig) throws RemoteException {
        this.mClientCallback.onConfigurationChanged(bandConfig);
    }

    public final /* synthetic */ void lambda$onCurrentProgramInfoChanged$2(
            RadioManager.ProgramInfo programInfo) throws RemoteException {
        this.mClientCallback.onCurrentProgramInfoChanged(programInfo);
    }

    public final /* synthetic */ void lambda$onEmergencyAnnouncement$4(boolean z)
            throws RemoteException {
        this.mClientCallback.onEmergencyAnnouncement(z);
    }

    public final /* synthetic */ void lambda$onError$0(int i) throws RemoteException {
        this.mClientCallback.onError(i);
    }

    public final /* synthetic */ void lambda$onProgramListChanged$8() throws RemoteException {
        this.mClientCallback.onProgramListChanged();
    }

    public final /* synthetic */ void lambda$onProgramListUpdated$10(ProgramList.Chunk chunk)
            throws RemoteException {
        this.mClientCallback.onProgramListUpdated(chunk);
    }

    public final /* synthetic */ void lambda$onTrafficAnnouncement$3(boolean z)
            throws RemoteException {
        this.mClientCallback.onTrafficAnnouncement(z);
    }

    public final /* synthetic */ void lambda$sendProgramListUpdate$9(ProgramList.Chunk chunk)
            throws RemoteException {
        this.mClientCallback.onProgramListUpdated(chunk);
    }

    public final void onAntennaState(boolean z) {
        dispatch(new TunerCallback$$ExternalSyntheticLambda1(this, z, 2));
    }

    public final void onBackgroundScanAvailabilityChange(boolean z) {
        dispatch(new TunerCallback$$ExternalSyntheticLambda1(this, z, 0));
    }

    public final void onBackgroundScanComplete() {
        dispatch(new TunerCallback$$ExternalSyntheticLambda2(this, 1));
    }

    public final void onConfigFlagUpdated(int i, boolean z) {
        Slogf.w(TAG, "Not applicable for HAL 1.x");
    }

    public final void onConfigurationChanged(RadioManager.BandConfig bandConfig) {
        this.mInitialConfigurationDone = true;
        dispatch(new TunerCallback$$ExternalSyntheticLambda5(this, bandConfig));
    }

    public final void onCurrentProgramInfoChanged(RadioManager.ProgramInfo programInfo) {
        dispatch(new TunerCallback$$ExternalSyntheticLambda5(this, programInfo));
    }

    public final void onEmergencyAnnouncement(boolean z) {
        dispatch(new TunerCallback$$ExternalSyntheticLambda1(this, z, 1));
    }

    public final void onError(final int i) {
        dispatch(
                new RunnableThrowingRemoteException() { // from class:
                                                        // com.android.server.broadcastradio.hal1.TunerCallback$$ExternalSyntheticLambda9
                    @Override // com.android.server.broadcastradio.hal1.TunerCallback.RunnableThrowingRemoteException
                    public final void run() {
                        TunerCallback.this.lambda$onError$0(i);
                    }
                });
    }

    public final void onParametersUpdated(Map map) {
        Slogf.w(TAG, "Not applicable for HAL 1.x");
    }

    public final void onProgramListChanged() {
        dispatch(new TunerCallback$$ExternalSyntheticLambda2(this, 0));
        sendProgramListUpdate();
    }

    public final void onProgramListUpdated(ProgramList.Chunk chunk) {
        dispatch(new TunerCallback$$ExternalSyntheticLambda0(this, chunk, 1));
    }

    public final void onTrafficAnnouncement(boolean z) {
        dispatch(new TunerCallback$$ExternalSyntheticLambda1(this, z, 3));
    }

    public final void onTuneFailed(int i, ProgramSelector programSelector) {
        Slogf.e(TAG, "Not applicable for HAL 1.x");
    }

    public final void sendProgramListUpdate() {
        ProgramList.Filter filter = (ProgramList.Filter) this.mProgramListFilter.get();
        if (filter == null) {
            return;
        }
        try {
            dispatch(
                    new TunerCallback$$ExternalSyntheticLambda0(
                            this,
                            new ProgramList.Chunk(
                                    true,
                                    true,
                                    (Set)
                                            this.mTuner
                                                    .getProgramList(filter.getVendorFilter())
                                                    .stream()
                                                    .collect(Collectors.toSet()),
                                    (Set) null),
                            0));
        } catch (IllegalStateException unused) {
            Slogf.d(TAG, "Program list not ready yet");
        }
    }

    public final void startProgramListUpdates(ProgramList.Filter filter) {
        if (filter == null) {
            filter = new ProgramList.Filter();
        }
        this.mProgramListFilter.set(filter);
        sendProgramListUpdate();
    }

    public final void stopProgramListUpdates() {
        this.mProgramListFilter.set(null);
    }
}
