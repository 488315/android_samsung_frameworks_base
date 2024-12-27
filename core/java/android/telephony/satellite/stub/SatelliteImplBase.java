package android.telephony.satellite.stub;

import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.IBooleanConsumer;
import android.telephony.IIntegerConsumer;
import android.util.Log;

import com.android.internal.telephony.util.TelephonyUtils;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class SatelliteImplBase extends SatelliteService {
    private static final String TAG = "SatelliteImplBase";
    private final IBinder mBinder = new AnonymousClass1();
    protected final Executor mExecutor;

    public SatelliteImplBase(Executor executor) {
        this.mExecutor = executor;
    }

    public final IBinder getBinder() {
        return this.mBinder;
    }

    /* renamed from: android.telephony.satellite.stub.SatelliteImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends ISatellite.Stub {
        AnonymousClass1() {}

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteListener(final ISatelliteListener listener) throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda17
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this.lambda$setSatelliteListener$0(
                                    listener);
                        }
                    },
                    "setSatelliteListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatelliteListener$0(ISatelliteListener listener) {
            SatelliteImplBase.this.setSatelliteListener(listener);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteListeningEnabled(
                final boolean enable, final int timeout, final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda16
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestSatelliteListeningEnabled$1(
                                            enable, timeout, resultCallback);
                        }
                    },
                    "requestSatelliteListeningEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteListeningEnabled$1(
                boolean enable, int timeout, IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.requestSatelliteListeningEnabled(
                    enable, timeout, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(
                final boolean enabled, final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$enableTerrestrialNetworkScanWhileSatelliteModeIsOn$2(
                                            enabled, resultCallback);
                        }
                    },
                    "enableTerrestrialNetworkScanWhileSatelliteModeIsOn");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$enableTerrestrialNetworkScanWhileSatelliteModeIsOn$2(
                boolean enabled, IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.enableTerrestrialNetworkScanWhileSatelliteModeIsOn(
                    enabled, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteEnabled(
                final SatelliteModemEnableRequestAttributes enableAttributes,
                final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteEnabled$3(
                                    enableAttributes, resultCallback);
                        }
                    },
                    "requestSatelliteEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteEnabled$3(
                SatelliteModemEnableRequestAttributes enableAttributes,
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.requestSatelliteEnabled(enableAttributes, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabled(
                final IIntegerConsumer resultCallback, final IBooleanConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestIsSatelliteEnabled$4(resultCallback, callback);
                        }
                    },
                    "requestIsSatelliteEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteEnabled$4(
                IIntegerConsumer resultCallback, IBooleanConsumer callback) {
            SatelliteImplBase.this.requestIsSatelliteEnabled(resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteSupported(
                final IIntegerConsumer resultCallback, final IBooleanConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestIsSatelliteSupported$5(resultCallback, callback);
                        }
                    },
                    "requestIsSatelliteSupported");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteSupported$5(
                IIntegerConsumer resultCallback, IBooleanConsumer callback) {
            SatelliteImplBase.this.requestIsSatelliteSupported(resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteCapabilities(
                final IIntegerConsumer resultCallback,
                final ISatelliteCapabilitiesConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestSatelliteCapabilities$6(
                                            resultCallback, callback);
                        }
                    },
                    "requestSatelliteCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteCapabilities$6(
                IIntegerConsumer resultCallback, ISatelliteCapabilitiesConsumer callback) {
            SatelliteImplBase.this.requestSatelliteCapabilities(resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingSatellitePointingInfo(final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$startSendingSatellitePointingInfo$7(resultCallback);
                        }
                    },
                    "startSendingSatellitePointingInfo");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSendingSatellitePointingInfo$7(
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.startSendingSatellitePointingInfo(resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingSatellitePointingInfo(final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$stopSendingSatellitePointingInfo$8(resultCallback);
                        }
                    },
                    "stopSendingSatellitePointingInfo");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopSendingSatellitePointingInfo$8(
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.stopSendingSatellitePointingInfo(resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void pollPendingSatelliteDatagrams(final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda20
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$pollPendingSatelliteDatagrams$9(resultCallback);
                        }
                    },
                    "pollPendingSatelliteDatagrams");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$pollPendingSatelliteDatagrams$9(
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.pollPendingSatelliteDatagrams(resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void sendSatelliteDatagram(
                final SatelliteDatagram datagram,
                final boolean isEmergency,
                final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this.lambda$sendSatelliteDatagram$10(
                                    datagram, isEmergency, resultCallback);
                        }
                    },
                    "sendDatagram");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendSatelliteDatagram$10(
                SatelliteDatagram datagram, boolean isEmergency, IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.sendSatelliteDatagram(datagram, isEmergency, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteModemState(
                final IIntegerConsumer resultCallback, final IIntegerConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestSatelliteModemState$11(resultCallback, callback);
                        }
                    },
                    "requestSatelliteModemState");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteModemState$11(
                IIntegerConsumer resultCallback, IIntegerConsumer callback) {
            SatelliteImplBase.this.requestSatelliteModemState(resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestTimeForNextSatelliteVisibility(
                final IIntegerConsumer resultCallback, final IIntegerConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestTimeForNextSatelliteVisibility$12(
                                            resultCallback, callback);
                        }
                    },
                    "requestTimeForNextSatelliteVisibility");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTimeForNextSatelliteVisibility$12(
                IIntegerConsumer resultCallback, IIntegerConsumer callback) {
            SatelliteImplBase.this.requestTimeForNextSatelliteVisibility(resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatellitePlmn(
                final int simSlot,
                final List<String> carrierPlmnList,
                final List<String> devicePlmnList,
                final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this.lambda$setSatellitePlmn$13(
                                    simSlot, carrierPlmnList, devicePlmnList, resultCallback);
                        }
                    },
                    "setSatellitePlmn");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatellitePlmn$13(
                int simSlot,
                List carrierPlmnList,
                List devicePlmnList,
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.setSatellitePlmn(
                    simSlot, carrierPlmnList, devicePlmnList, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteEnabledForCarrier(
                final int simSlot,
                final boolean enableSatellite,
                final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$setSatelliteEnabledForCarrier$14(
                                            simSlot, enableSatellite, resultCallback);
                        }
                    },
                    "setSatelliteEnabledForCarrier");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatelliteEnabledForCarrier$14(
                int simSlot, boolean enableSatellite, IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.setSatelliteEnabledForCarrier(
                    simSlot, enableSatellite, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabledForCarrier(
                final int simSlot,
                final IIntegerConsumer resultCallback,
                final IBooleanConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$requestIsSatelliteEnabledForCarrier$15(
                                            simSlot, resultCallback, callback);
                        }
                    },
                    "requestIsSatelliteEnabledForCarrier");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteEnabledForCarrier$15(
                int simSlot, IIntegerConsumer resultCallback, IBooleanConsumer callback) {
            SatelliteImplBase.this.requestIsSatelliteEnabledForCarrier(
                    simSlot, resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSignalStrength(
                final IIntegerConsumer resultCallback, final INtnSignalStrengthConsumer callback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda14
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this.lambda$requestSignalStrength$16(
                                    resultCallback, callback);
                        }
                    },
                    "requestSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSignalStrength$16(
                IIntegerConsumer resultCallback, INtnSignalStrengthConsumer callback) {
            SatelliteImplBase.this.requestSignalStrength(resultCallback, callback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingNtnSignalStrength(final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$startSendingNtnSignalStrength$17(resultCallback);
                        }
                    },
                    "startSendingNtnSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSendingNtnSignalStrength$17(
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.startSendingNtnSignalStrength(resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingNtnSignalStrength(final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$stopSendingNtnSignalStrength$18(resultCallback);
                        }
                    },
                    "stopSendingNtnSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopSendingNtnSignalStrength$18(
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.stopSendingNtnSignalStrength(resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void abortSendingSatelliteDatagrams(final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$abortSendingSatelliteDatagrams$19(resultCallback);
                        }
                    },
                    "abortSendingSatelliteDatagrams");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$abortSendingSatelliteDatagrams$19(
                IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.abortSendingSatelliteDatagrams(resultCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSatelliteSubscription$20(
                String iccId, IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.updateSatelliteSubscription(iccId, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void updateSatelliteSubscription(
                final String iccId, final IIntegerConsumer resultCallback) throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$updateSatelliteSubscription$20(iccId, resultCallback);
                        }
                    },
                    "updateSatelliteSubscription");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSystemSelectionChannels$21(
                List systemSelectionSpecifiers, IIntegerConsumer resultCallback) {
            SatelliteImplBase.this.updateSystemSelectionChannels(
                    systemSelectionSpecifiers, resultCallback);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void updateSystemSelectionChannels(
                final List<SystemSelectionSpecifier> systemSelectionSpecifiers,
                final IIntegerConsumer resultCallback)
                throws RemoteException {
            executeMethodAsync(
                    new Runnable() { // from class:
                                     // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            SatelliteImplBase.AnonymousClass1.this
                                    .lambda$updateSystemSelectionChannels$21(
                                            systemSelectionSpecifiers, resultCallback);
                        }
                    },
                    "updateSystemSelectionChannels");
        }

        private void executeMethodAsync(final Runnable r, String errorLogName)
                throws RemoteException {
            try {
                CompletableFuture.runAsync(
                                new Runnable() { // from class:
                                                 // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TelephonyUtils.runWithCleanCallingIdentity(r);
                                    }
                                },
                                SatelliteImplBase.this.mExecutor)
                        .join();
            } catch (CancellationException | CompletionException e) {
                Log.w(
                        SatelliteImplBase.TAG,
                        "SatelliteImplBase Binder - "
                                + errorLogName
                                + " exception: "
                                + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public void setSatelliteListener(ISatelliteListener listener) {}

    public void requestSatelliteListeningEnabled(
            boolean enable, int timeout, IIntegerConsumer resultCallback) {}

    public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(
            boolean enabled, IIntegerConsumer resultCallback) {}

    public void requestSatelliteEnabled(
            SatelliteModemEnableRequestAttributes enableAttributes,
            IIntegerConsumer resultCallback) {}

    public void requestIsSatelliteEnabled(
            IIntegerConsumer resultCallback, IBooleanConsumer callback) {}

    public void requestIsSatelliteSupported(
            IIntegerConsumer resultCallback, IBooleanConsumer callback) {}

    public void requestSatelliteCapabilities(
            IIntegerConsumer resultCallback, ISatelliteCapabilitiesConsumer callback) {}

    public void startSendingSatellitePointingInfo(IIntegerConsumer resultCallback) {}

    public void stopSendingSatellitePointingInfo(IIntegerConsumer resultCallback) {}

    public void pollPendingSatelliteDatagrams(IIntegerConsumer resultCallback) {}

    public void sendSatelliteDatagram(
            SatelliteDatagram datagram, boolean isEmergency, IIntegerConsumer resultCallback) {}

    public void requestSatelliteModemState(
            IIntegerConsumer resultCallback, IIntegerConsumer callback) {}

    public void requestTimeForNextSatelliteVisibility(
            IIntegerConsumer resultCallback, IIntegerConsumer callback) {}

    public void setSatellitePlmn(
            int simLogicalSlotIndex,
            List<String> carrierPlmnList,
            List<String> allSatellitePlmnList,
            IIntegerConsumer resultCallback) {}

    public void setSatelliteEnabledForCarrier(
            int simLogicalSlotIndex, boolean satelliteEnabled, IIntegerConsumer callback) {}

    public void requestIsSatelliteEnabledForCarrier(
            int simLogicalSlotIndex, IIntegerConsumer resultCallback, IBooleanConsumer callback) {}

    public void requestSignalStrength(
            IIntegerConsumer resultCallback, INtnSignalStrengthConsumer callback) {}

    public void startSendingNtnSignalStrength(IIntegerConsumer resultCallback) {}

    public void stopSendingNtnSignalStrength(IIntegerConsumer resultCallback) {}

    public void abortSendingSatelliteDatagrams(IIntegerConsumer resultCallback) {}

    public void updateSatelliteSubscription(String iccId, IIntegerConsumer resultCallback) {}

    public void updateSystemSelectionChannels(
            List<SystemSelectionSpecifier> systemSelectionSpecifiers,
            IIntegerConsumer resultCallback) {}
}
