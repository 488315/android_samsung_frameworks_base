package android.telephony.satellite.stub;

import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.IBooleanConsumer;
import android.telephony.IIntegerConsumer;
import android.telephony.satellite.stub.ISatellite;
import android.telephony.satellite.stub.SatelliteImplBase;
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

    public void abortSendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) {
    }

    public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(boolean z, IIntegerConsumer iIntegerConsumer) {
    }

    public void pollPendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) {
    }

    public void requestIsSatelliteEnabled(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) {
    }

    @Deprecated
    public void requestIsSatelliteEnabledForCarrier(int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) {
    }

    public void requestIsSatelliteSupported(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) {
    }

    public void requestSatelliteCapabilities(IIntegerConsumer iIntegerConsumer, ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) {
    }

    public void requestSatelliteEnabled(SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, IIntegerConsumer iIntegerConsumer) {
    }

    public void requestSatelliteListeningEnabled(boolean z, int i, IIntegerConsumer iIntegerConsumer) {
    }

    public void requestSatelliteModemState(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) {
    }

    public void requestSignalStrength(IIntegerConsumer iIntegerConsumer, INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) {
    }

    public void requestTimeForNextSatelliteVisibility(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) {
    }

    public void sendSatelliteDatagram(SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) {
    }

    @Deprecated
    public void setSatelliteEnabledForCarrier(int i, boolean z, IIntegerConsumer iIntegerConsumer) {
    }

    public void setSatelliteListener(ISatelliteListener iSatelliteListener) {
    }

    @Deprecated
    public void setSatellitePlmn(int i, List<String> list, List<String> list2, IIntegerConsumer iIntegerConsumer) {
    }

    public void startSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) {
    }

    public void startSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) {
    }

    public void stopSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) {
    }

    public void stopSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) {
    }

    public void updateSatelliteSubscription(String str, IIntegerConsumer iIntegerConsumer) {
    }

    public void updateSystemSelectionChannels(List<SystemSelectionSpecifier> list, IIntegerConsumer iIntegerConsumer) {
    }

    public SatelliteImplBase(Executor executor) {
        this.mExecutor = executor;
    }

    public final IBinder getBinder() {
        return this.mBinder;
    }

    /* renamed from: android.telephony.satellite.stub.SatelliteImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends ISatellite.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteListener(final ISatelliteListener iSatelliteListener) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$setSatelliteListener$0(iSatelliteListener);
                }
            }, "setSatelliteListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatelliteListener$0(ISatelliteListener iSatelliteListener) {
            SatelliteImplBase.this.setSatelliteListener(iSatelliteListener);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteListeningEnabled(final boolean z, final int i, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteListeningEnabled$1(z, i, iIntegerConsumer);
                }
            }, "requestSatelliteListeningEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteListeningEnabled$1(boolean z, int i, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.requestSatelliteListeningEnabled(z, i, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(final boolean z, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$enableTerrestrialNetworkScanWhileSatelliteModeIsOn$2(z, iIntegerConsumer);
                }
            }, "enableTerrestrialNetworkScanWhileSatelliteModeIsOn");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$enableTerrestrialNetworkScanWhileSatelliteModeIsOn$2(boolean z, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.enableTerrestrialNetworkScanWhileSatelliteModeIsOn(z, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteEnabled(final SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteEnabled$3(satelliteModemEnableRequestAttributes, iIntegerConsumer);
                }
            }, "requestSatelliteEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteEnabled$3(SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.requestSatelliteEnabled(satelliteModemEnableRequestAttributes, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabled(final IIntegerConsumer iIntegerConsumer, final IBooleanConsumer iBooleanConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteEnabled$4(iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteEnabled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteEnabled$4(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) {
            SatelliteImplBase.this.requestIsSatelliteEnabled(iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteSupported(final IIntegerConsumer iIntegerConsumer, final IBooleanConsumer iBooleanConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteSupported$5(iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteSupported");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteSupported$5(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) {
            SatelliteImplBase.this.requestIsSatelliteSupported(iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteCapabilities(final IIntegerConsumer iIntegerConsumer, final ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteCapabilities$6(iIntegerConsumer, iSatelliteCapabilitiesConsumer);
                }
            }, "requestSatelliteCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteCapabilities$6(IIntegerConsumer iIntegerConsumer, ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) {
            SatelliteImplBase.this.requestSatelliteCapabilities(iIntegerConsumer, iSatelliteCapabilitiesConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingSatellitePointingInfo(final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$startSendingSatellitePointingInfo$7(iIntegerConsumer);
                }
            }, "startSendingSatellitePointingInfo");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSendingSatellitePointingInfo$7(IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.startSendingSatellitePointingInfo(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingSatellitePointingInfo(final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$stopSendingSatellitePointingInfo$8(iIntegerConsumer);
                }
            }, "stopSendingSatellitePointingInfo");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopSendingSatellitePointingInfo$8(IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.stopSendingSatellitePointingInfo(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void pollPendingSatelliteDatagrams(final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$pollPendingSatelliteDatagrams$9(iIntegerConsumer);
                }
            }, "pollPendingSatelliteDatagrams");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$pollPendingSatelliteDatagrams$9(IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.pollPendingSatelliteDatagrams(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void sendSatelliteDatagram(final SatelliteDatagram satelliteDatagram, final boolean z, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$sendSatelliteDatagram$10(satelliteDatagram, z, iIntegerConsumer);
                }
            }, "sendDatagram");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendSatelliteDatagram$10(SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.sendSatelliteDatagram(satelliteDatagram, z, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteModemState(final IIntegerConsumer iIntegerConsumer, final IIntegerConsumer iIntegerConsumer2) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestSatelliteModemState$11(iIntegerConsumer, iIntegerConsumer2);
                }
            }, "requestSatelliteModemState");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSatelliteModemState$11(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) {
            SatelliteImplBase.this.requestSatelliteModemState(iIntegerConsumer, iIntegerConsumer2);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestTimeForNextSatelliteVisibility(final IIntegerConsumer iIntegerConsumer, final IIntegerConsumer iIntegerConsumer2) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestTimeForNextSatelliteVisibility$12(iIntegerConsumer, iIntegerConsumer2);
                }
            }, "requestTimeForNextSatelliteVisibility");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestTimeForNextSatelliteVisibility$12(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) {
            SatelliteImplBase.this.requestTimeForNextSatelliteVisibility(iIntegerConsumer, iIntegerConsumer2);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatellitePlmn(final int i, final List<String> list, final List<String> list2, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$setSatellitePlmn$13(i, list, list2, iIntegerConsumer);
                }
            }, "setSatellitePlmn");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatellitePlmn$13(int i, List list, List list2, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.setSatellitePlmn(i, list, list2, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteEnabledForCarrier(final int i, final boolean z, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$setSatelliteEnabledForCarrier$14(i, z, iIntegerConsumer);
                }
            }, "setSatelliteEnabledForCarrier");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSatelliteEnabledForCarrier$14(int i, boolean z, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.setSatelliteEnabledForCarrier(i, z, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabledForCarrier(final int i, final IIntegerConsumer iIntegerConsumer, final IBooleanConsumer iBooleanConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestIsSatelliteEnabledForCarrier$15(i, iIntegerConsumer, iBooleanConsumer);
                }
            }, "requestIsSatelliteEnabledForCarrier");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestIsSatelliteEnabledForCarrier$15(int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) {
            SatelliteImplBase.this.requestIsSatelliteEnabledForCarrier(i, iIntegerConsumer, iBooleanConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSignalStrength(final IIntegerConsumer iIntegerConsumer, final INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$requestSignalStrength$16(iIntegerConsumer, iNtnSignalStrengthConsumer);
                }
            }, "requestSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestSignalStrength$16(IIntegerConsumer iIntegerConsumer, INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) {
            SatelliteImplBase.this.requestSignalStrength(iIntegerConsumer, iNtnSignalStrengthConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingNtnSignalStrength(final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$startSendingNtnSignalStrength$17(iIntegerConsumer);
                }
            }, "startSendingNtnSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSendingNtnSignalStrength$17(IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.startSendingNtnSignalStrength(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingNtnSignalStrength(final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$stopSendingNtnSignalStrength$18(iIntegerConsumer);
                }
            }, "stopSendingNtnSignalStrength");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopSendingNtnSignalStrength$18(IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.stopSendingNtnSignalStrength(iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void abortSendingSatelliteDatagrams(final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$abortSendingSatelliteDatagrams$19(iIntegerConsumer);
                }
            }, "abortSendingSatelliteDatagrams");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$abortSendingSatelliteDatagrams$19(IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.abortSendingSatelliteDatagrams(iIntegerConsumer);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSatelliteSubscription$20(String str, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.updateSatelliteSubscription(str, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void updateSatelliteSubscription(final String str, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$updateSatelliteSubscription$20(str, iIntegerConsumer);
                }
            }, "updateSatelliteSubscription");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSystemSelectionChannels$21(List list, IIntegerConsumer iIntegerConsumer) {
            SatelliteImplBase.this.updateSystemSelectionChannels(list, iIntegerConsumer);
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void updateSystemSelectionChannels(final List<SystemSelectionSpecifier> list, final IIntegerConsumer iIntegerConsumer) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    SatelliteImplBase.AnonymousClass1.this.lambda$updateSystemSelectionChannels$21(list, iIntegerConsumer);
                }
            }, "updateSystemSelectionChannels");
        }

        private void executeMethodAsync(final Runnable runnable, String str) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, SatelliteImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(SatelliteImplBase.TAG, "SatelliteImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }
}
