package android.telephony.satellite;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.ICancellationSignal;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyFrameworkInitializer;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.IVoidConsumer;
import com.android.internal.util.FunctionalUtils;
import com.android.telephony.Rlog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class SatelliteManager {
  public static final int DATAGRAM_TYPE_LOCATION_SHARING = 2;
  public static final int DATAGRAM_TYPE_SOS_MESSAGE = 1;
  public static final int DATAGRAM_TYPE_UNKNOWN = 0;
  public static final int DEVICE_HOLD_POSITION_LANDSCAPE_LEFT = 2;
  public static final int DEVICE_HOLD_POSITION_LANDSCAPE_RIGHT = 3;
  public static final int DEVICE_HOLD_POSITION_PORTRAIT = 1;
  public static final int DEVICE_HOLD_POSITION_UNKNOWN = 0;
  public static final int DISPLAY_MODE_CLOSED = 3;
  public static final int DISPLAY_MODE_FIXED = 1;
  public static final int DISPLAY_MODE_OPENED = 2;
  public static final int DISPLAY_MODE_UNKNOWN = 0;
  public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_SOS = 1;
  public static final int EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE_T911 = 2;
  public static final String KEY_DEMO_MODE_ENABLED = "demo_mode_enabled";
  public static final String KEY_SATELLITE_CAPABILITIES = "satellite_capabilities";
  public static final String KEY_SATELLITE_COMMUNICATION_ALLOWED =
      "satellite_communication_allowed";
  public static final String KEY_SATELLITE_ENABLED = "satellite_enabled";
  public static final String KEY_SATELLITE_NEXT_VISIBILITY = "satellite_next_visibility";
  public static final String KEY_SATELLITE_PROVISIONED = "satellite_provisioned";
  public static final String KEY_SATELLITE_SUPPORTED = "satellite_supported";
  public static final int NT_RADIO_TECHNOLOGY_EMTC_NTN = 3;
  public static final int NT_RADIO_TECHNOLOGY_NB_IOT_NTN = 1;
  public static final int NT_RADIO_TECHNOLOGY_NR_NTN = 2;
  public static final int NT_RADIO_TECHNOLOGY_PROPRIETARY = 4;
  public static final int NT_RADIO_TECHNOLOGY_UNKNOWN = 0;
  public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_ENTITLEMENT = 2;
  public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_GEOLOCATION = 1;
  public static final int SATELLITE_COMMUNICATION_RESTRICTION_REASON_USER = 0;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_IDLE = 0;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_FAILED = 7;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_NONE = 6;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVE_SUCCESS = 5;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_RECEIVING = 4;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SENDING = 1;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_FAILED = 3;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_SEND_SUCCESS = 2;
  public static final int SATELLITE_DATAGRAM_TRANSFER_STATE_UNKNOWN = -1;
  public static final int SATELLITE_MODEM_STATE_DATAGRAM_RETRYING = 3;
  public static final int SATELLITE_MODEM_STATE_DATAGRAM_TRANSFERRING = 2;
  public static final int SATELLITE_MODEM_STATE_IDLE = 0;
  public static final int SATELLITE_MODEM_STATE_LISTENING = 1;
  public static final int SATELLITE_MODEM_STATE_OFF = 4;
  public static final int SATELLITE_MODEM_STATE_UNAVAILABLE = 5;
  public static final int SATELLITE_MODEM_STATE_UNKNOWN = -1;
  public static final int SATELLITE_RESULT_ACCESS_BARRED = 16;
  public static final int SATELLITE_RESULT_ERROR = 1;
  public static final int SATELLITE_RESULT_INVALID_ARGUMENTS = 8;
  public static final int SATELLITE_RESULT_INVALID_MODEM_STATE = 7;
  public static final int SATELLITE_RESULT_INVALID_TELEPHONY_STATE = 6;
  public static final int SATELLITE_RESULT_MODEM_BUSY = 22;
  public static final int SATELLITE_RESULT_MODEM_ERROR = 4;
  public static final int SATELLITE_RESULT_NETWORK_ERROR = 5;
  public static final int SATELLITE_RESULT_NETWORK_TIMEOUT = 17;
  public static final int SATELLITE_RESULT_NOT_AUTHORIZED = 19;
  public static final int SATELLITE_RESULT_NOT_REACHABLE = 18;
  public static final int SATELLITE_RESULT_NOT_SUPPORTED = 20;
  public static final int SATELLITE_RESULT_NO_RESOURCES = 12;
  public static final int SATELLITE_RESULT_RADIO_NOT_AVAILABLE = 10;
  public static final int SATELLITE_RESULT_REQUEST_ABORTED = 15;
  public static final int SATELLITE_RESULT_REQUEST_FAILED = 9;
  public static final int SATELLITE_RESULT_REQUEST_IN_PROGRESS = 21;
  public static final int SATELLITE_RESULT_REQUEST_NOT_SUPPORTED = 11;
  public static final int SATELLITE_RESULT_SERVER_ERROR = 2;
  public static final int SATELLITE_RESULT_SERVICE_ERROR = 3;
  public static final int SATELLITE_RESULT_SERVICE_NOT_PROVISIONED = 13;
  public static final int SATELLITE_RESULT_SERVICE_PROVISION_IN_PROGRESS = 14;
  public static final int SATELLITE_RESULT_SUCCESS = 0;
  private static final String TAG = "SatelliteManager";
  private final Context mContext;
  private final int mSubId;
  private static final ConcurrentHashMap<SatelliteDatagramCallback, ISatelliteDatagramCallback>
      sSatelliteDatagramCallbackMap = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<
          SatelliteProvisionStateCallback, ISatelliteProvisionStateCallback>
      sSatelliteProvisionStateCallbackMap = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<SatelliteModemStateCallback, ISatelliteModemStateCallback>
      sSatelliteModemStateCallbackMap = new ConcurrentHashMap<>();
  private static final ConcurrentHashMap<
          SatelliteTransmissionUpdateCallback, ISatelliteTransmissionUpdateCallback>
      sSatelliteTransmissionUpdateCallbackMap = new ConcurrentHashMap<>();

  @Retention(RetentionPolicy.SOURCE)
  public @interface DatagramType {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface DeviceHoldPosition {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface DisplayMode {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface NTRadioTechnology {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SatelliteCommunicationRestrictionReason {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SatelliteDatagramTransferState {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SatelliteModemState {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SatelliteResult {}

  public SatelliteManager(Context context) {
    this(context, Integer.MAX_VALUE);
  }

  private SatelliteManager(Context context, int subId) {
    this.mContext = context;
    this.mSubId = subId;
  }

  public static class SatelliteException extends Exception {
    private final int mErrorCode;

    public SatelliteException(int errorCode) {
      this.mErrorCode = errorCode;
    }

    public int getErrorCode() {
      return this.mErrorCode;
    }
  }

  public void requestEnabled(
      boolean enableSatellite,
      boolean enableDemoMode,
      Executor executor,
      Consumer<Integer> resultListener) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer errorCallback = new BinderC35051(executor, resultListener);
        telephony.requestSatelliteEnabled(
            this.mSubId, enableSatellite, enableDemoMode, errorCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      Rlog.m241e(TAG, "requestSatelliteEnabled() RemoteException: ", ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$1 */
  class BinderC35051 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC35051(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$1$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public void requestIsEnabled(
      Executor executor, OutcomeReceiver<Boolean, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC35162(null, executor, callback);
        telephony.requestIsSatelliteEnabled(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestIsSatelliteEnabled() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$2 */
  class ResultReceiverC35162 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC35162(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey("satellite_enabled")) {
          final boolean isSatelliteEnabled = resultData.getBoolean("satellite_enabled");
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(Boolean.valueOf(r2));
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_SATELLITE_ENABLED does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda5
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$2$$ExternalSyntheticLambda4
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public void requestIsDemoModeEnabled(
      Executor executor, OutcomeReceiver<Boolean, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC35183(null, executor, callback);
        telephony.requestIsDemoModeEnabled(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestIsDemoModeEnabled() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$3 */
  class ResultReceiverC35183 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC35183(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey(SatelliteManager.KEY_DEMO_MODE_ENABLED)) {
          final boolean isDemoModeEnabled =
              resultData.getBoolean(SatelliteManager.KEY_DEMO_MODE_ENABLED);
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(Boolean.valueOf(r2));
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_DEMO_MODE_ENABLED does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda0
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$3$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public void requestIsSupported(
      Executor executor, OutcomeReceiver<Boolean, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC35194(null, executor, callback);
        telephony.requestIsSatelliteSupported(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestIsSatelliteSupported() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$4 */
  class ResultReceiverC35194 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC35194(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_SUPPORTED)) {
          final boolean isSatelliteSupported =
              resultData.getBoolean(SatelliteManager.KEY_SATELLITE_SUPPORTED);
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(Boolean.valueOf(r2));
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_SATELLITE_SUPPORTED does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda0
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$4$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public void requestCapabilities(
      Executor executor, OutcomeReceiver<SatelliteCapabilities, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC35205(null, executor, callback);
        telephony.requestSatelliteCapabilities(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestSatelliteCapabilities() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$5 */
  class ResultReceiverC35205 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC35205(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_CAPABILITIES)) {
          final SatelliteCapabilities capabilities =
              (SatelliteCapabilities)
                  resultData.getParcelable(
                      SatelliteManager.KEY_SATELLITE_CAPABILITIES, SatelliteCapabilities.class);
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(r2);
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_SATELLITE_CAPABILITIES does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda4
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$5$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public void startTransmissionUpdates(
      Executor executor,
      Consumer<Integer> resultListener,
      SatelliteTransmissionUpdateCallback callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer errorCallback = new BinderC35216(executor, resultListener);
        ISatelliteTransmissionUpdateCallback internalCallback =
            new BinderC35227(executor, callback);
        sSatelliteTransmissionUpdateCallbackMap.put(callback, internalCallback);
        telephony.startSatelliteTransmissionUpdates(this.mSubId, errorCallback, internalCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("startSatelliteTransmissionUpdates() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$6 */
  class BinderC35216 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC35216(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$6$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$7 */
  class BinderC35227 extends ISatelliteTransmissionUpdateCallback.Stub {
    final /* synthetic */ SatelliteTransmissionUpdateCallback val$callback;
    final /* synthetic */ Executor val$executor;

    BinderC35227(
        Executor executor,
        SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback) {
      this.val$executor = executor;
      this.val$callback = satelliteTransmissionUpdateCallback;
    }

    @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
    public void onSatellitePositionChanged(final PointingInfo pointingInfo) {
      Executor executor = this.val$executor;
      final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
          this.val$callback;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      SatelliteTransmissionUpdateCallback.this.onSatellitePositionChanged(r2);
                    }
                  });
            }
          });
    }

    @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
    public void onSendDatagramStateChanged(
        final int state, final int sendPendingCount, final int errorCode) {
      Executor executor = this.val$executor;
      final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
          this.val$callback;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      SatelliteTransmissionUpdateCallback.this.onSendDatagramStateChanged(
                          r2, r3, r4);
                    }
                  });
            }
          });
    }

    @Override // android.telephony.satellite.ISatelliteTransmissionUpdateCallback
    public void onReceiveDatagramStateChanged(
        final int state, final int receivePendingCount, final int errorCode) {
      Executor executor = this.val$executor;
      final SatelliteTransmissionUpdateCallback satelliteTransmissionUpdateCallback =
          this.val$callback;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$7$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      SatelliteTransmissionUpdateCallback.this.onReceiveDatagramStateChanged(
                          r2, r3, r4);
                    }
                  });
            }
          });
    }
  }

  public void stopTransmissionUpdates(
      SatelliteTransmissionUpdateCallback callback,
      Executor executor,
      final Consumer<Integer> resultListener) {
    Objects.requireNonNull(callback);
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    ISatelliteTransmissionUpdateCallback internalCallback =
        sSatelliteTransmissionUpdateCallbackMap.remove(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        if (internalCallback != null) {
          IIntegerConsumer errorCallback = new BinderC35238(executor, resultListener);
          telephony.stopSatelliteTransmissionUpdates(this.mSubId, errorCallback, internalCallback);
        } else {
          loge("stopSatelliteTransmissionUpdates: No internal callback.");
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          r1.accept(8);
                        }
                      });
                }
              });
        }
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("stopSatelliteTransmissionUpdates() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$8 */
  class BinderC35238 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC35238(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$8$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public void provisionService(
      String token,
      byte[] provisionData,
      CancellationSignal cancellationSignal,
      Executor executor,
      Consumer<Integer> resultListener) {
    ITelephony telephony;
    Objects.requireNonNull(token);
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    Objects.requireNonNull(provisionData);
    ICancellationSignal cancelRemote = null;
    try {
      telephony = getITelephony();
    } catch (RemoteException ex) {
      loge("provisionSatelliteService() RemoteException=" + ex);
      ex.rethrowFromSystemServer();
    }
    if (telephony != null) {
      IIntegerConsumer errorCallback = new BinderC35249(executor, resultListener);
      cancelRemote =
          telephony.provisionSatelliteService(this.mSubId, token, provisionData, errorCallback);
      if (cancellationSignal != null) {
        cancellationSignal.setRemote(cancelRemote);
        return;
      }
      return;
    }
    throw new IllegalStateException("telephony service is null.");
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$9 */
  class BinderC35249 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC35249(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$9$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public void deprovisionService(
      String token, Executor executor, Consumer<Integer> resultListener) {
    Objects.requireNonNull(token);
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer errorCallback = new BinderC350610(executor, resultListener);
        telephony.deprovisionSatelliteService(this.mSubId, token, errorCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("deprovisionSatelliteService() RemoteException=" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$10 */
  class BinderC350610 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC350610(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$10$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public int registerForProvisionStateChanged(
      Executor executor, SatelliteProvisionStateCallback callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ISatelliteProvisionStateCallback internalCallback = new BinderC350711(executor, callback);
        sSatelliteProvisionStateCallbackMap.put(callback, internalCallback);
        return telephony.registerForSatelliteProvisionStateChanged(this.mSubId, internalCallback);
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("registerForSatelliteProvisionStateChanged() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
      return 9;
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$11 */
  class BinderC350711 extends ISatelliteProvisionStateCallback.Stub {
    final /* synthetic */ SatelliteProvisionStateCallback val$callback;
    final /* synthetic */ Executor val$executor;

    BinderC350711(
        Executor executor, SatelliteProvisionStateCallback satelliteProvisionStateCallback) {
      this.val$executor = executor;
      this.val$callback = satelliteProvisionStateCallback;
    }

    @Override // android.telephony.satellite.ISatelliteProvisionStateCallback
    public void onSatelliteProvisionStateChanged(final boolean provisioned) {
      Executor executor = this.val$executor;
      final SatelliteProvisionStateCallback satelliteProvisionStateCallback = this.val$callback;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$11$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      SatelliteProvisionStateCallback.this.onSatelliteProvisionStateChanged(r2);
                    }
                  });
            }
          });
    }
  }

  public void unregisterForProvisionStateChanged(SatelliteProvisionStateCallback callback) {
    Objects.requireNonNull(callback);
    ISatelliteProvisionStateCallback internalCallback =
        sSatelliteProvisionStateCallbackMap.remove(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        if (internalCallback != null) {
          telephony.unregisterForSatelliteProvisionStateChanged(this.mSubId, internalCallback);
        } else {
          loge("unregisterForProvisionStateChanged: No internal callback.");
        }
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("unregisterForProvisionStateChanged() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  public void requestIsProvisioned(
      Executor executor, OutcomeReceiver<Boolean, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC350812(null, executor, callback);
        telephony.requestIsSatelliteProvisioned(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestIsSatelliteProvisioned() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$12 */
  class ResultReceiverC350812 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC350812(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_PROVISIONED)) {
          final boolean isSatelliteProvisioned =
              resultData.getBoolean(SatelliteManager.KEY_SATELLITE_PROVISIONED);
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(Boolean.valueOf(r2));
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_SATELLITE_PROVISIONED does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda0
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$12$$ExternalSyntheticLambda4
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public int registerForModemStateChanged(Executor executor, SatelliteModemStateCallback callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ISatelliteModemStateCallback internalCallback = new BinderC350913(executor, callback);
        sSatelliteModemStateCallbackMap.put(callback, internalCallback);
        return telephony.registerForSatelliteModemStateChanged(this.mSubId, internalCallback);
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("registerForSatelliteModemStateChanged() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
      return 9;
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$13 */
  class BinderC350913 extends ISatelliteModemStateCallback.Stub {
    final /* synthetic */ SatelliteModemStateCallback val$callback;
    final /* synthetic */ Executor val$executor;

    BinderC350913(Executor executor, SatelliteModemStateCallback satelliteModemStateCallback) {
      this.val$executor = executor;
      this.val$callback = satelliteModemStateCallback;
    }

    @Override // android.telephony.satellite.ISatelliteModemStateCallback
    public void onSatelliteModemStateChanged(final int state) {
      Executor executor = this.val$executor;
      final SatelliteModemStateCallback satelliteModemStateCallback = this.val$callback;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$13$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      SatelliteModemStateCallback.this.onSatelliteModemStateChanged(r2);
                    }
                  });
            }
          });
    }
  }

  public void unregisterForModemStateChanged(SatelliteModemStateCallback callback) {
    Objects.requireNonNull(callback);
    ISatelliteModemStateCallback internalCallback =
        sSatelliteModemStateCallbackMap.remove(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        if (internalCallback != null) {
          telephony.unregisterForModemStateChanged(this.mSubId, internalCallback);
        } else {
          loge("unregisterForModemStateChanged: No internal callback.");
        }
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("unregisterForModemStateChanged() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  public int registerForIncomingDatagram(Executor executor, SatelliteDatagramCallback callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ISatelliteDatagramCallback internalCallback = new BinderC351014(executor, callback);
        sSatelliteDatagramCallbackMap.put(callback, internalCallback);
        return telephony.registerForIncomingDatagram(this.mSubId, internalCallback);
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("registerForIncomingDatagram() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
      return 9;
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$14 */
  class BinderC351014 extends ISatelliteDatagramCallback.Stub {
    final /* synthetic */ SatelliteDatagramCallback val$callback;
    final /* synthetic */ Executor val$executor;

    BinderC351014(Executor executor, SatelliteDatagramCallback satelliteDatagramCallback) {
      this.val$executor = executor;
      this.val$callback = satelliteDatagramCallback;
    }

    @Override // android.telephony.satellite.ISatelliteDatagramCallback
    public void onSatelliteDatagramReceived(
        final long datagramId,
        final SatelliteDatagram datagram,
        final int pendingCount,
        final IVoidConsumer internalAck) {
      final Consumer<Void> externalAck =
          new Consumer<Void>() { // from class: android.telephony.satellite.SatelliteManager.14.1
            @Override // java.util.function.Consumer
            public void accept(Void result) {
              try {
                internalAck.accept();
              } catch (RemoteException e) {
                SatelliteManager.logd("onSatelliteDatagramReceived RemoteException: " + e);
              }
            }
          };
      Executor executor = this.val$executor;
      final SatelliteDatagramCallback satelliteDatagramCallback = this.val$callback;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$14$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      SatelliteDatagramCallback.this.onSatelliteDatagramReceived(r2, r4, r5, r6);
                    }
                  });
            }
          });
    }
  }

  public void unregisterForIncomingDatagram(SatelliteDatagramCallback callback) {
    Objects.requireNonNull(callback);
    ISatelliteDatagramCallback internalCallback = sSatelliteDatagramCallbackMap.remove(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        if (internalCallback != null) {
          telephony.unregisterForIncomingDatagram(this.mSubId, internalCallback);
        } else {
          loge("unregisterForIncomingDatagram: No internal callback.");
        }
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("unregisterForIncomingDatagram() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  public void pollPendingDatagrams(Executor executor, Consumer<Integer> resultListener) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer internalCallback = new BinderC351115(executor, resultListener);
        telephony.pollPendingDatagrams(this.mSubId, internalCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("pollPendingDatagrams() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$15 */
  class BinderC351115 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC351115(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$15$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public void sendDatagram(
      int datagramType,
      SatelliteDatagram datagram,
      boolean needFullScreenPointingUI,
      Executor executor,
      Consumer<Integer> resultListener) {
    Objects.requireNonNull(datagram);
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer internalCallback = new BinderC351216(executor, resultListener);
        telephony.sendDatagram(
            this.mSubId, datagramType, datagram, needFullScreenPointingUI, internalCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("sendDatagram() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$16 */
  class BinderC351216 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC351216(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$16$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public void requestIsCommunicationAllowedForCurrentLocation(
      Executor executor, OutcomeReceiver<Boolean, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC351317(null, executor, callback);
        telephony.requestIsCommunicationAllowedForCurrentLocation(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestIsCommunicationAllowedForCurrentLocation() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$17 */
  class ResultReceiverC351317 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC351317(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED)) {
          final boolean isSatelliteCommunicationAllowed =
              resultData.getBoolean(SatelliteManager.KEY_SATELLITE_COMMUNICATION_ALLOWED);
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda5
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(Boolean.valueOf(r2));
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_SATELLITE_COMMUNICATION_ALLOWED does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda4
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$17$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public void requestTimeForNextSatelliteVisibility(
      Executor executor, OutcomeReceiver<Duration, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        ResultReceiver receiver = new ResultReceiverC351418(null, executor, callback);
        telephony.requestTimeForNextSatelliteVisibility(this.mSubId, receiver);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("requestTimeForNextSatelliteVisibility() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$18 */
  class ResultReceiverC351418 extends ResultReceiver {
    final /* synthetic */ OutcomeReceiver val$callback;
    final /* synthetic */ Executor val$executor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ResultReceiverC351418(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
      super(handler);
      this.val$executor = executor;
      this.val$callback = outcomeReceiver;
    }

    @Override // android.os.ResultReceiver
    protected void onReceiveResult(final int resultCode, Bundle resultData) {
      if (resultCode == 0) {
        if (resultData.containsKey(SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY)) {
          final int nextVisibilityDuration =
              resultData.getInt(SatelliteManager.KEY_SATELLITE_NEXT_VISIBILITY);
          Executor executor = this.val$executor;
          final OutcomeReceiver outcomeReceiver = this.val$callback;
          executor.execute(
              new Runnable() { // from class:
                               // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                  Binder.withCleanCallingIdentity(
                      new FunctionalUtils
                          .ThrowingRunnable() { // from class:
                                                // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                          OutcomeReceiver.this.onResult(Duration.ofSeconds(r2));
                        }
                      });
                }
              });
          return;
        }
        SatelliteManager.loge("KEY_SATELLITE_NEXT_VISIBILITY does not exist.");
        Executor executor2 = this.val$executor;
        final OutcomeReceiver outcomeReceiver2 = this.val$callback;
        executor2.execute(
            new Runnable() { // from class:
                             // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda2
              @Override // java.lang.Runnable
              public final void run() {
                Binder.withCleanCallingIdentity(
                    new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda4
                      @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                      public final void runOrThrow() {
                        OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(9));
                      }
                    });
              }
            });
        return;
      }
      Executor executor3 = this.val$executor;
      final OutcomeReceiver outcomeReceiver3 = this.val$callback;
      executor3.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$18$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      OutcomeReceiver.this.onError(new SatelliteManager.SatelliteException(r2));
                    }
                  });
            }
          });
    }
  }

  public void onDeviceAlignedWithSatellite(boolean isAligned) {
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        telephony.onDeviceAlignedWithSatellite(this.mSubId, isAligned);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("informDeviceAlignedToSatellite() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  public void requestAttachEnabledForCarrier(
      int subId, boolean enableSatellite, Executor executor, Consumer<Integer> resultListener) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(resultListener);
    if (enableSatellite) {
      removeAttachRestrictionForCarrier(subId, 0, executor, resultListener);
    } else {
      addAttachRestrictionForCarrier(subId, 0, executor, resultListener);
    }
  }

  public void requestIsAttachEnabledForCarrier(
      int subId, Executor executor, final OutcomeReceiver<Boolean, SatelliteException> callback) {
    Objects.requireNonNull(executor);
    Objects.requireNonNull(callback);
    final Set<Integer> restrictionReason = getAttachRestrictionReasonsForCarrier(subId);
    executor.execute(
        new Runnable() { // from class:
                         // android.telephony.satellite.SatelliteManager$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            OutcomeReceiver outcomeReceiver = OutcomeReceiver.this;
            Set set = restrictionReason;
            outcomeReceiver.onResult(Boolean.valueOf(!set.contains(0)));
          }
        });
  }

  public void addAttachRestrictionForCarrier(
      int subId, int reason, Executor executor, Consumer<Integer> resultListener) {
    if (!SubscriptionManager.isValidSubscriptionId(subId)) {
      throw new IllegalArgumentException("Invalid subscription ID");
    }
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer errorCallback = new BinderC351519(executor, resultListener);
        telephony.addAttachRestrictionForCarrier(subId, reason, errorCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("addAttachRestrictionForCarrier() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$19 */
  class BinderC351519 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC351519(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$19$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public void removeAttachRestrictionForCarrier(
      int subId, int reason, Executor executor, Consumer<Integer> resultListener) {
    if (!SubscriptionManager.isValidSubscriptionId(subId)) {
      throw new IllegalArgumentException("Invalid subscription ID");
    }
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        IIntegerConsumer errorCallback = new BinderC351720(executor, resultListener);
        telephony.removeAttachRestrictionForCarrier(subId, reason, errorCallback);
        return;
      }
      throw new IllegalStateException("telephony service is null.");
    } catch (RemoteException ex) {
      loge("removeAttachRestrictionForCarrier() RemoteException:" + ex);
      ex.rethrowFromSystemServer();
    }
  }

  /* renamed from: android.telephony.satellite.SatelliteManager$20 */
  class BinderC351720 extends IIntegerConsumer.Stub {
    final /* synthetic */ Executor val$executor;
    final /* synthetic */ Consumer val$resultListener;

    BinderC351720(Executor executor, Consumer consumer) {
      this.val$executor = executor;
      this.val$resultListener = consumer;
    }

    @Override // com.android.internal.telephony.IIntegerConsumer
    public void accept(final int result) {
      Executor executor = this.val$executor;
      final Consumer consumer = this.val$resultListener;
      executor.execute(
          new Runnable() { // from class:
                           // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              Binder.withCleanCallingIdentity(
                  new FunctionalUtils
                      .ThrowingRunnable() { // from class:
                                            // android.telephony.satellite.SatelliteManager$20$$ExternalSyntheticLambda0
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() {
                      r1.accept(Integer.valueOf(r2));
                    }
                  });
            }
          });
    }
  }

  public Set<Integer> getAttachRestrictionReasonsForCarrier(int subId) {
    if (!SubscriptionManager.isValidSubscriptionId(subId)) {
      throw new IllegalArgumentException("Invalid subscription ID");
    }
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        int[] receivedArray = telephony.getAttachRestrictionReasonsForCarrier(subId);
        if (receivedArray.length == 0) {
          logd("receivedArray is empty, create empty set");
          return new HashSet();
        }
        return (Set) Arrays.stream(receivedArray).boxed().collect(Collectors.toSet());
      }
      throw new IllegalStateException("Telephony service is null.");
    } catch (RemoteException ex) {
      loge("getAttachRestrictionReasonsForCarrier() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
      return new HashSet();
    }
  }

  public List<String> getSatellitePlmnsForCarrier(int subId) {
    if (!SubscriptionManager.isValidSubscriptionId(subId)) {
      throw new IllegalArgumentException("Invalid subscription ID");
    }
    try {
      ITelephony telephony = getITelephony();
      if (telephony != null) {
        return telephony.getSatellitePlmnsForCarrier(subId);
      }
      throw new IllegalStateException("Telephony service is null.");
    } catch (RemoteException ex) {
      loge("getSatellitePlmnsForCarrier() RemoteException: " + ex);
      ex.rethrowFromSystemServer();
      return new ArrayList();
    }
  }

  private static ITelephony getITelephony() {
    ITelephony binder =
        ITelephony.Stub.asInterface(
            TelephonyFrameworkInitializer.getTelephonyServiceManager()
                .getTelephonyServiceRegisterer()
                .get());
    if (binder == null) {
      throw new RuntimeException("Could not find Telephony Service.");
    }
    return binder;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static void logd(String log) {
    Rlog.m238d(TAG, log);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static void loge(String log) {
    Rlog.m240e(TAG, log);
  }
}
