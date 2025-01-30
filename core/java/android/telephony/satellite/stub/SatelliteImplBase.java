package android.telephony.satellite.stub;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.telephony.IBooleanConsumer;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SatelliteImplBase extends SatelliteService {
  private static final String TAG = "SatelliteImplBase";
  private final IBinder mBinder = new BinderC35291();
  protected final Executor mExecutor;

  public SatelliteImplBase(Executor executor) {
    this.mExecutor = executor;
  }

  public final IBinder getBinder() {
    return this.mBinder;
  }

  /* renamed from: android.telephony.satellite.stub.SatelliteImplBase$1 */
  class BinderC35291 extends ISatellite.Stub {
    BinderC35291() {}

    @Override // android.telephony.satellite.stub.ISatellite
    public void setSatelliteListener(final ISatelliteListener listener) throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$setSatelliteListener$0(listener);
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
        final boolean enable, final int timeout, final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestSatelliteListeningEnabled$1(
                  enable, timeout, errorCallback);
            }
          },
          "requestSatelliteListeningEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSatelliteListeningEnabled$1(
        boolean enable, int timeout, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.requestSatelliteListeningEnabled(enable, timeout, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void enableCellularModemWhileSatelliteModeIsOn(
        final boolean enabled, final IIntegerConsumer errorCallback) throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this
                  .lambda$enableCellularModemWhileSatelliteModeIsOn$2(enabled, errorCallback);
            }
          },
          "enableCellularModemWhileSatelliteModeIsOn");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$enableCellularModemWhileSatelliteModeIsOn$2(
        boolean enabled, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.enableCellularModemWhileSatelliteModeIsOn(enabled, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteEnabled(
        final boolean enableSatellite,
        final boolean enableDemoMode,
        final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestSatelliteEnabled$3(
                  enableSatellite, enableDemoMode, errorCallback);
            }
          },
          "requestSatelliteEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSatelliteEnabled$3(
        boolean enableSatellite, boolean enableDemoMode, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.requestSatelliteEnabled(
          enableSatellite, enableDemoMode, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteEnabled(
        final IIntegerConsumer errorCallback, final IBooleanConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestIsSatelliteEnabled$4(
                  errorCallback, callback);
            }
          },
          "requestIsSatelliteEnabled");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestIsSatelliteEnabled$4(
        IIntegerConsumer errorCallback, IBooleanConsumer callback) {
      SatelliteImplBase.this.requestIsSatelliteEnabled(errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteSupported(
        final IIntegerConsumer errorCallback, final IBooleanConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestIsSatelliteSupported$5(
                  errorCallback, callback);
            }
          },
          "requestIsSatelliteSupported");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestIsSatelliteSupported$5(
        IIntegerConsumer errorCallback, IBooleanConsumer callback) {
      SatelliteImplBase.this.requestIsSatelliteSupported(errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteCapabilities(
        final IIntegerConsumer errorCallback, final ISatelliteCapabilitiesConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestSatelliteCapabilities$6(
                  errorCallback, callback);
            }
          },
          "requestSatelliteCapabilities");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSatelliteCapabilities$6(
        IIntegerConsumer errorCallback, ISatelliteCapabilitiesConsumer callback) {
      SatelliteImplBase.this.requestSatelliteCapabilities(errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void startSendingSatellitePointingInfo(final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$startSendingSatellitePointingInfo$7(
                  errorCallback);
            }
          },
          "startSendingSatellitePointingInfo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSendingSatellitePointingInfo$7(
        IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.startSendingSatellitePointingInfo(errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void stopSendingSatellitePointingInfo(final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$stopSendingSatellitePointingInfo$8(
                  errorCallback);
            }
          },
          "stopSendingSatellitePointingInfo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopSendingSatellitePointingInfo$8(
        IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.stopSendingSatellitePointingInfo(errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void provisionSatelliteService(
        final String token, final byte[] provisionData, final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$provisionSatelliteService$9(
                  token, provisionData, errorCallback);
            }
          },
          "provisionSatelliteService");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$provisionSatelliteService$9(
        String token, byte[] provisionData, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.provisionSatelliteService(token, provisionData, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void deprovisionSatelliteService(
        final String token, final IIntegerConsumer errorCallback) throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$deprovisionSatelliteService$10(
                  token, errorCallback);
            }
          },
          "deprovisionSatelliteService");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deprovisionSatelliteService$10(
        String token, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.deprovisionSatelliteService(token, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteProvisioned(
        final IIntegerConsumer errorCallback, final IBooleanConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestIsSatelliteProvisioned$11(
                  errorCallback, callback);
            }
          },
          "requestIsSatelliteProvisioned");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestIsSatelliteProvisioned$11(
        IIntegerConsumer errorCallback, IBooleanConsumer callback) {
      SatelliteImplBase.this.requestIsSatelliteProvisioned(errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void pollPendingSatelliteDatagrams(final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$pollPendingSatelliteDatagrams$12(
                  errorCallback);
            }
          },
          "pollPendingSatelliteDatagrams");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pollPendingSatelliteDatagrams$12(
        IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.pollPendingSatelliteDatagrams(errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void sendSatelliteDatagram(
        final SatelliteDatagram datagram,
        final boolean isEmergency,
        final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$sendSatelliteDatagram$13(
                  datagram, isEmergency, errorCallback);
            }
          },
          "sendDatagram");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendSatelliteDatagram$13(
        SatelliteDatagram datagram, boolean isEmergency, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.sendSatelliteDatagram(datagram, isEmergency, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestSatelliteModemState(
        final IIntegerConsumer errorCallback, final IIntegerConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestSatelliteModemState$14(
                  errorCallback, callback);
            }
          },
          "requestSatelliteModemState");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestSatelliteModemState$14(
        IIntegerConsumer errorCallback, IIntegerConsumer callback) {
      SatelliteImplBase.this.requestSatelliteModemState(errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteCommunicationAllowedForCurrentLocation(
        final IIntegerConsumer errorCallback, final IBooleanConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.m88xdcaabeb2(errorCallback, callback);
            }
          },
          "requestIsCommunicationAllowedForCurrentLocation");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: lambda$requestIsSatelliteCommunicationAllowedForCurrentLocation$15 */
    public /* synthetic */ void m88xdcaabeb2(
        IIntegerConsumer errorCallback, IBooleanConsumer callback) {
      SatelliteImplBase.this.requestIsSatelliteCommunicationAllowedForCurrentLocation(
          errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestTimeForNextSatelliteVisibility(
        final IIntegerConsumer errorCallback, final IIntegerConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestTimeForNextSatelliteVisibility$16(
                  errorCallback, callback);
            }
          },
          "requestTimeForNextSatelliteVisibility");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestTimeForNextSatelliteVisibility$16(
        IIntegerConsumer errorCallback, IIntegerConsumer callback) {
      SatelliteImplBase.this.requestTimeForNextSatelliteVisibility(errorCallback, callback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void setSatellitePlmn(
        final int simSlot,
        final List<String> carrierPlmnList,
        final List<String> devicePlmnList,
        final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$setSatellitePlmn$17(
                  simSlot, carrierPlmnList, devicePlmnList, errorCallback);
            }
          },
          "setSatellitePlmn");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSatellitePlmn$17(
        int simSlot, List carrierPlmnList, List devicePlmnList, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.setSatellitePlmn(
          simSlot, carrierPlmnList, devicePlmnList, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void setSatelliteEnabledForCarrier(
        final int simSlot, final boolean enableSatellite, final IIntegerConsumer errorCallback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$setSatelliteEnabledForCarrier$18(
                  simSlot, enableSatellite, errorCallback);
            }
          },
          "setSatelliteEnabledForCarrier");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSatelliteEnabledForCarrier$18(
        int simSlot, boolean enableSatellite, IIntegerConsumer errorCallback) {
      SatelliteImplBase.this.setSatelliteEnabledForCarrier(simSlot, enableSatellite, errorCallback);
    }

    @Override // android.telephony.satellite.stub.ISatellite
    public void requestIsSatelliteEnabledForCarrier(
        final int simSlot, final IIntegerConsumer errorCallback, final IBooleanConsumer callback)
        throws RemoteException {
      executeMethodAsync(
          new Runnable() { // from class:
                           // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
              SatelliteImplBase.BinderC35291.this.lambda$requestIsSatelliteEnabledForCarrier$19(
                  simSlot, errorCallback, callback);
            }
          },
          "requestIsSatelliteEnabledForCarrier");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestIsSatelliteEnabledForCarrier$19(
        int simSlot, IIntegerConsumer errorCallback, IBooleanConsumer callback) {
      SatelliteImplBase.this.requestIsSatelliteEnabledForCarrier(simSlot, errorCallback, callback);
    }

    private void executeMethodAsync(final Runnable r, String errorLogName) throws RemoteException {
      try {
        CompletableFuture.runAsync(
                new Runnable() { // from class:
                                 // android.telephony.satellite.stub.SatelliteImplBase$1$$ExternalSyntheticLambda10
                  @Override // java.lang.Runnable
                  public final void run() {
                    TelephonyUtils.runWithCleanCallingIdentity(r);
                  }
                },
                SatelliteImplBase.this.mExecutor)
            .join();
      } catch (CancellationException | CompletionException e) {
        Log.m102w(
            SatelliteImplBase.TAG,
            "SatelliteImplBase Binder - " + errorLogName + " exception: " + e.getMessage());
        throw new RemoteException(e.getMessage());
      }
    }
  }

  public void setSatelliteListener(ISatelliteListener listener) {}

  public void requestSatelliteListeningEnabled(
      boolean enable, int timeout, IIntegerConsumer errorCallback) {}

  public void enableCellularModemWhileSatelliteModeIsOn(
      boolean enabled, IIntegerConsumer errorCallback) {}

  public void requestSatelliteEnabled(
      boolean enableSatellite, boolean enableDemoMode, IIntegerConsumer errorCallback) {}

  public void requestIsSatelliteEnabled(
      IIntegerConsumer errorCallback, IBooleanConsumer callback) {}

  public void requestIsSatelliteSupported(
      IIntegerConsumer errorCallback, IBooleanConsumer callback) {}

  public void requestSatelliteCapabilities(
      IIntegerConsumer errorCallback, ISatelliteCapabilitiesConsumer callback) {}

  public void startSendingSatellitePointingInfo(IIntegerConsumer errorCallback) {}

  public void stopSendingSatellitePointingInfo(IIntegerConsumer errorCallback) {}

  public void provisionSatelliteService(
      String token, byte[] provisionData, IIntegerConsumer errorCallback) {}

  public void deprovisionSatelliteService(String token, IIntegerConsumer errorCallback) {}

  public void requestIsSatelliteProvisioned(
      IIntegerConsumer errorCallback, IBooleanConsumer callback) {}

  public void pollPendingSatelliteDatagrams(IIntegerConsumer errorCallback) {}

  public void sendSatelliteDatagram(
      SatelliteDatagram datagram, boolean isEmergency, IIntegerConsumer errorCallback) {}

  public void requestSatelliteModemState(
      IIntegerConsumer errorCallback, IIntegerConsumer callback) {}

  public void requestIsSatelliteCommunicationAllowedForCurrentLocation(
      IIntegerConsumer errorCallback, IBooleanConsumer callback) {}

  public void requestTimeForNextSatelliteVisibility(
      IIntegerConsumer errorCallback, IIntegerConsumer callback) {}

  public void setSatellitePlmn(
      int simLogicalSlotIndex,
      List<String> carrierPlmnList,
      List<String> allSatellitePlmnList,
      IIntegerConsumer errorCallback) {}

  public void setSatelliteEnabledForCarrier(
      int simLogicalSlotIndex, boolean satelliteEnabled, IIntegerConsumer callback) {}

  public void requestIsSatelliteEnabledForCarrier(
      int simLogicalSlotIndex, IIntegerConsumer errorCallback, IBooleanConsumer callback) {}
}
