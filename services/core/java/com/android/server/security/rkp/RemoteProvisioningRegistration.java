package com.android.server.security.rkp;

import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.os.OutcomeReceiver;
import android.security.rkp.IGetKeyCallback;
import android.security.rkp.IRegistration;
import android.security.rkp.IStoreUpgradedKeyCallback;
import android.security.rkp.service.RegistrationProxy;
import android.security.rkp.service.RemotelyProvisionedKey;
import android.security.rkp.service.RkpProxyException;
import android.util.Log;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class RemoteProvisioningRegistration extends IRegistration.Stub {
  public final Executor mExecutor;
  public final RegistrationProxy mRegistration;
  public final ConcurrentHashMap mGetKeyOperations = new ConcurrentHashMap();
  public final Set mStoreUpgradedKeyOperations = ConcurrentHashMap.newKeySet();

  public interface CallbackRunner {
    void run();
  }

  public class GetKeyReceiver implements OutcomeReceiver {
    public IGetKeyCallback mCallback;

    public GetKeyReceiver(IGetKeyCallback iGetKeyCallback) {
      this.mCallback = iGetKeyCallback;
    }

    @Override // android.os.OutcomeReceiver
    public void onResult(RemotelyProvisionedKey remotelyProvisionedKey) {
      RemoteProvisioningRegistration.this.mGetKeyOperations.remove(this.mCallback.asBinder());
      Log.i(
          "RemoteProvisionSysSvc",
          "Successfully fetched key for client " + this.mCallback.asBinder().hashCode());
      final android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey2 =
          new android.security.rkp.RemotelyProvisionedKey();
      remotelyProvisionedKey2.keyBlob = remotelyProvisionedKey.getKeyBlob();
      remotelyProvisionedKey2.encodedCertChain = remotelyProvisionedKey.getEncodedCertChain();
      RemoteProvisioningRegistration.this.wrapCallback(
          new CallbackRunner() { // from class:
                                 // com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda3
            @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
            public final void run() {
              RemoteProvisioningRegistration.GetKeyReceiver.this.lambda$onResult$0(
                  remotelyProvisionedKey2);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResult$0(
        android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey) {
      this.mCallback.onSuccess(remotelyProvisionedKey);
    }

    @Override // android.os.OutcomeReceiver
    public void onError(final Exception exc) {
      RemoteProvisioningRegistration.this.mGetKeyOperations.remove(this.mCallback.asBinder());
      if (exc instanceof OperationCanceledException) {
        Log.i(
            "RemoteProvisionSysSvc",
            "Operation cancelled for client " + this.mCallback.asBinder().hashCode());
        RemoteProvisioningRegistration remoteProvisioningRegistration =
            RemoteProvisioningRegistration.this;
        final IGetKeyCallback iGetKeyCallback = this.mCallback;
        Objects.requireNonNull(iGetKeyCallback);
        remoteProvisioningRegistration.wrapCallback(
            new CallbackRunner() { // from class:
                                   // com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda0
              @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
              public final void run() {
                iGetKeyCallback.onCancel();
              }
            });
        return;
      }
      if (exc instanceof RkpProxyException) {
        Log.e(
            "RemoteProvisionSysSvc",
            "RKP error fetching key for client "
                + this.mCallback.asBinder().hashCode()
                + ": "
                + exc.getMessage());
        final RkpProxyException rkpProxyException = (RkpProxyException) exc;
        RemoteProvisioningRegistration.this.wrapCallback(
            new CallbackRunner() { // from class:
                                   // com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda1
              @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
              public final void run() {
                RemoteProvisioningRegistration.GetKeyReceiver.this.lambda$onError$1(
                    rkpProxyException, exc);
              }
            });
        return;
      }
      Log.e(
          "RemoteProvisionSysSvc",
          "Unknown error fetching key for client "
              + this.mCallback.asBinder().hashCode()
              + ": "
              + exc.getMessage());
      RemoteProvisioningRegistration.this.wrapCallback(
          new CallbackRunner() { // from class:
                                 // com.android.server.security.rkp.RemoteProvisioningRegistration$GetKeyReceiver$$ExternalSyntheticLambda2
            @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
            public final void run() {
              RemoteProvisioningRegistration.GetKeyReceiver.this.lambda$onError$2(exc);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$1(
        RkpProxyException rkpProxyException, Exception exc) {
      this.mCallback.onError(
          RemoteProvisioningRegistration.this.toGetKeyError(rkpProxyException), exc.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$2(Exception exc) {
      this.mCallback.onError((byte) 1, exc.getMessage());
    }
  }

  public final byte toGetKeyError(RkpProxyException rkpProxyException) {
    int error = rkpProxyException.getError();
    if (error == 0) {
      return (byte) 1;
    }
    if (error == 1) {
      return (byte) 2;
    }
    if (error == 2) {
      return (byte) 3;
    }
    if (error == 3) {
      return (byte) 5;
    }
    Log.e("RemoteProvisionSysSvc", "Unexpected error code in RkpProxyException", rkpProxyException);
    return (byte) 1;
  }

  public RemoteProvisioningRegistration(RegistrationProxy registrationProxy, Executor executor) {
    this.mRegistration = registrationProxy;
    this.mExecutor = executor;
  }

  public void getKey(int i, final IGetKeyCallback iGetKeyCallback) {
    CancellationSignal cancellationSignal = new CancellationSignal();
    if (this.mGetKeyOperations.putIfAbsent(iGetKeyCallback.asBinder(), cancellationSignal)
        != null) {
      Log.e(
          "RemoteProvisionSysSvc",
          "Client can only request one call at a time " + iGetKeyCallback.asBinder().hashCode());
      throw new IllegalArgumentException(
          "Callback is already associated with an existing operation: "
              + iGetKeyCallback.asBinder().hashCode());
    }
    try {
      Log.i(
          "RemoteProvisionSysSvc",
          "Fetching key " + i + " for client " + iGetKeyCallback.asBinder().hashCode());
      this.mRegistration.getKeyAsync(
          i, cancellationSignal, this.mExecutor, new GetKeyReceiver(iGetKeyCallback));
    } catch (Exception e) {
      Log.e(
          "RemoteProvisionSysSvc",
          "getKeyAsync threw an exception for client " + iGetKeyCallback.asBinder().hashCode(),
          e);
      this.mGetKeyOperations.remove(iGetKeyCallback.asBinder());
      wrapCallback(
          new CallbackRunner() { // from class:
                                 // com.android.server.security.rkp.RemoteProvisioningRegistration$$ExternalSyntheticLambda0
            @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
            public final void run() {
              RemoteProvisioningRegistration.lambda$getKey$0(iGetKeyCallback, e);
            }
          });
    }
  }

  public static /* synthetic */ void lambda$getKey$0(
      IGetKeyCallback iGetKeyCallback, Exception exc) {
    iGetKeyCallback.onError((byte) 1, exc.getMessage());
  }

  public void cancelGetKey(IGetKeyCallback iGetKeyCallback) {
    CancellationSignal cancellationSignal =
        (CancellationSignal) this.mGetKeyOperations.remove(iGetKeyCallback.asBinder());
    if (cancellationSignal == null) {
      throw new IllegalArgumentException(
          "Invalid client in cancelGetKey: " + iGetKeyCallback.asBinder().hashCode());
    }
    Log.i(
        "RemoteProvisionSysSvc",
        "Requesting cancellation for client " + iGetKeyCallback.asBinder().hashCode());
    cancellationSignal.cancel();
  }

  public void storeUpgradedKeyAsync(
      byte[] bArr, byte[] bArr2, final IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) {
    if (!this.mStoreUpgradedKeyOperations.add(iStoreUpgradedKeyCallback.asBinder())) {
      throw new IllegalArgumentException(
          "Callback is already associated with an existing operation: "
              + iStoreUpgradedKeyCallback.asBinder().hashCode());
    }
    try {
      this.mRegistration.storeUpgradedKeyAsync(
          bArr, bArr2, this.mExecutor, new C24851(iStoreUpgradedKeyCallback));
    } catch (Exception e) {
      Log.e(
          "RemoteProvisionSysSvc",
          "storeUpgradedKeyAsync threw an exception for client "
              + iStoreUpgradedKeyCallback.asBinder().hashCode(),
          e);
      this.mStoreUpgradedKeyOperations.remove(iStoreUpgradedKeyCallback.asBinder());
      wrapCallback(
          new CallbackRunner() { // from class:
                                 // com.android.server.security.rkp.RemoteProvisioningRegistration$$ExternalSyntheticLambda1
            @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
            public final void run() {
              RemoteProvisioningRegistration.lambda$storeUpgradedKeyAsync$1(
                  iStoreUpgradedKeyCallback, e);
            }
          });
    }
  }

  /* renamed from: com.android.server.security.rkp.RemoteProvisioningRegistration$1 */
  public class C24851 implements OutcomeReceiver {
    public final /* synthetic */ IStoreUpgradedKeyCallback val$callback;

    public C24851(IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) {
      this.val$callback = iStoreUpgradedKeyCallback;
    }

    @Override // android.os.OutcomeReceiver
    public void onResult(Void r2) {
      RemoteProvisioningRegistration.this.mStoreUpgradedKeyOperations.remove(
          this.val$callback.asBinder());
      RemoteProvisioningRegistration remoteProvisioningRegistration =
          RemoteProvisioningRegistration.this;
      final IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback = this.val$callback;
      Objects.requireNonNull(iStoreUpgradedKeyCallback);
      remoteProvisioningRegistration.wrapCallback(
          new CallbackRunner() { // from class:
                                 // com.android.server.security.rkp.RemoteProvisioningRegistration$1$$ExternalSyntheticLambda0
            @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
            public final void run() {
              iStoreUpgradedKeyCallback.onSuccess();
            }
          });
    }

    @Override // android.os.OutcomeReceiver
    public void onError(final Exception exc) {
      RemoteProvisioningRegistration.this.mStoreUpgradedKeyOperations.remove(
          this.val$callback.asBinder());
      RemoteProvisioningRegistration remoteProvisioningRegistration =
          RemoteProvisioningRegistration.this;
      final IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback = this.val$callback;
      remoteProvisioningRegistration.wrapCallback(
          new CallbackRunner() { // from class:
                                 // com.android.server.security.rkp.RemoteProvisioningRegistration$1$$ExternalSyntheticLambda1
            @Override // com.android.server.security.rkp.RemoteProvisioningRegistration.CallbackRunner
            public final void run() {
              RemoteProvisioningRegistration.C24851.lambda$onError$0(
                  iStoreUpgradedKeyCallback, exc);
            }
          });
    }

    public static /* synthetic */ void lambda$onError$0(
        IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback, Exception exc) {
      iStoreUpgradedKeyCallback.onError(exc.getMessage());
    }
  }

  public static /* synthetic */ void lambda$storeUpgradedKeyAsync$1(
      IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback, Exception exc) {
    iStoreUpgradedKeyCallback.onError(exc.getMessage());
  }

  public final void wrapCallback(CallbackRunner callbackRunner) {
    try {
      callbackRunner.run();
    } catch (Exception e) {
      Log.e("RemoteProvisionSysSvc", "Error invoking callback on client binder", e);
    }
  }
}
