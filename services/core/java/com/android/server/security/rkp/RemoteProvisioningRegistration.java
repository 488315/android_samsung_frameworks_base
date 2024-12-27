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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteProvisioningRegistration extends IRegistration.Stub {
    public final Executor mExecutor;
    public final RegistrationProxy mRegistration;
    public final ConcurrentHashMap mGetKeyOperations = new ConcurrentHashMap();
    public final Set mStoreUpgradedKeyOperations = ConcurrentHashMap.newKeySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.security.rkp.RemoteProvisioningRegistration$1, reason: invalid class name */
    public final class AnonymousClass1 implements OutcomeReceiver {
        public final /* synthetic */ int $r8$classId = 1;
        public final Object val$callback;

        public AnonymousClass1(IGetKeyCallback iGetKeyCallback) {
            this.val$callback = iGetKeyCallback;
        }

        public AnonymousClass1(IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) {
            this.val$callback = iStoreUpgradedKeyCallback;
        }

        @Override // android.os.OutcomeReceiver
        public final void onError(Throwable th) {
            switch (this.$r8$classId) {
                case 0:
                    Exception exc = (Exception) th;
                    RemoteProvisioningRegistration.this.mStoreUpgradedKeyOperations.remove(
                            ((IStoreUpgradedKeyCallback) this.val$callback).asBinder());
                    RemoteProvisioningRegistration remoteProvisioningRegistration =
                            RemoteProvisioningRegistration.this;
                    IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback =
                            (IStoreUpgradedKeyCallback) this.val$callback;
                    remoteProvisioningRegistration.getClass();
                    try {
                        iStoreUpgradedKeyCallback.onError(exc.getMessage());
                        break;
                    } catch (Exception e) {
                        Log.e(
                                "RemoteProvisionSysSvc",
                                "Error invoking callback on client binder",
                                e);
                        return;
                    }
                default:
                    RkpProxyException rkpProxyException = (Exception) th;
                    RemoteProvisioningRegistration.this.mGetKeyOperations.remove(
                            ((IGetKeyCallback) this.val$callback).asBinder());
                    if (rkpProxyException instanceof OperationCanceledException) {
                        Log.i(
                                "RemoteProvisionSysSvc",
                                "Operation cancelled for client "
                                        + ((IGetKeyCallback) this.val$callback)
                                                .asBinder()
                                                .hashCode());
                        RemoteProvisioningRegistration remoteProvisioningRegistration2 =
                                RemoteProvisioningRegistration.this;
                        IGetKeyCallback iGetKeyCallback = (IGetKeyCallback) this.val$callback;
                        Objects.requireNonNull(iGetKeyCallback);
                        remoteProvisioningRegistration2.getClass();
                        try {
                            iGetKeyCallback.onCancel();
                            break;
                        } catch (Exception e2) {
                            Log.e(
                                    "RemoteProvisionSysSvc",
                                    "Error invoking callback on client binder",
                                    e2);
                            return;
                        }
                    } else {
                        byte b = 1;
                        if (rkpProxyException instanceof RkpProxyException) {
                            Log.e(
                                    "RemoteProvisionSysSvc",
                                    "RKP error fetching key for client "
                                            + ((IGetKeyCallback) this.val$callback)
                                                    .asBinder()
                                                    .hashCode()
                                            + ": "
                                            + rkpProxyException.getMessage());
                            RkpProxyException rkpProxyException2 = rkpProxyException;
                            RemoteProvisioningRegistration.this.getClass();
                            try {
                                IGetKeyCallback iGetKeyCallback2 =
                                        (IGetKeyCallback) this.val$callback;
                                RemoteProvisioningRegistration.this.getClass();
                                int error = rkpProxyException2.getError();
                                if (error != 0) {
                                    if (error == 1) {
                                        b = 2;
                                    } else if (error == 2) {
                                        b = 3;
                                    } else if (error != 3) {
                                        Log.e(
                                                "RemoteProvisionSysSvc",
                                                "Unexpected error code in RkpProxyException",
                                                rkpProxyException2);
                                    } else {
                                        b = 5;
                                    }
                                }
                                iGetKeyCallback2.onError(b, rkpProxyException.getMessage());
                                break;
                            } catch (Exception e3) {
                                Log.e(
                                        "RemoteProvisionSysSvc",
                                        "Error invoking callback on client binder",
                                        e3);
                                return;
                            }
                        } else {
                            Log.e(
                                    "RemoteProvisionSysSvc",
                                    "Unknown error fetching key for client "
                                            + ((IGetKeyCallback) this.val$callback)
                                                    .asBinder()
                                                    .hashCode()
                                            + ": "
                                            + rkpProxyException.getMessage());
                            RemoteProvisioningRegistration.this.getClass();
                            try {
                                ((IGetKeyCallback) this.val$callback)
                                        .onError((byte) 1, rkpProxyException.getMessage());
                                break;
                            } catch (Exception e4) {
                                Log.e(
                                        "RemoteProvisionSysSvc",
                                        "Error invoking callback on client binder",
                                        e4);
                            }
                        }
                    }
            }
        }

        @Override // android.os.OutcomeReceiver
        public final void onResult(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    RemoteProvisioningRegistration.this.mStoreUpgradedKeyOperations.remove(
                            ((IStoreUpgradedKeyCallback) this.val$callback).asBinder());
                    RemoteProvisioningRegistration remoteProvisioningRegistration =
                            RemoteProvisioningRegistration.this;
                    IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback =
                            (IStoreUpgradedKeyCallback) this.val$callback;
                    Objects.requireNonNull(iStoreUpgradedKeyCallback);
                    remoteProvisioningRegistration.getClass();
                    try {
                        iStoreUpgradedKeyCallback.onSuccess();
                        break;
                    } catch (Exception e) {
                        Log.e(
                                "RemoteProvisionSysSvc",
                                "Error invoking callback on client binder",
                                e);
                        return;
                    }
                default:
                    RemotelyProvisionedKey remotelyProvisionedKey = (RemotelyProvisionedKey) obj;
                    RemoteProvisioningRegistration.this.mGetKeyOperations.remove(
                            ((IGetKeyCallback) this.val$callback).asBinder());
                    Log.i(
                            "RemoteProvisionSysSvc",
                            "Successfully fetched key for client "
                                    + ((IGetKeyCallback) this.val$callback).asBinder().hashCode());
                    android.security.rkp.RemotelyProvisionedKey remotelyProvisionedKey2 =
                            new android.security.rkp.RemotelyProvisionedKey();
                    remotelyProvisionedKey2.keyBlob = remotelyProvisionedKey.getKeyBlob();
                    remotelyProvisionedKey2.encodedCertChain =
                            remotelyProvisionedKey.getEncodedCertChain();
                    RemoteProvisioningRegistration.this.getClass();
                    try {
                        ((IGetKeyCallback) this.val$callback).onSuccess(remotelyProvisionedKey2);
                        break;
                    } catch (Exception e2) {
                        Log.e(
                                "RemoteProvisionSysSvc",
                                "Error invoking callback on client binder",
                                e2);
                    }
            }
        }
    }

    public RemoteProvisioningRegistration(RegistrationProxy registrationProxy, Executor executor) {
        this.mRegistration = registrationProxy;
        this.mExecutor = executor;
    }

    public final void cancelGetKey(IGetKeyCallback iGetKeyCallback) {
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

    public final void getKey(int i, IGetKeyCallback iGetKeyCallback) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (this.mGetKeyOperations.putIfAbsent(iGetKeyCallback.asBinder(), cancellationSignal)
                != null) {
            Log.e(
                    "RemoteProvisionSysSvc",
                    "Client can only request one call at a time "
                            + iGetKeyCallback.asBinder().hashCode());
            throw new IllegalArgumentException(
                    "Callback is already associated with an existing operation: "
                            + iGetKeyCallback.asBinder().hashCode());
        }
        try {
            Log.i(
                    "RemoteProvisionSysSvc",
                    "Fetching key " + i + " for client " + iGetKeyCallback.asBinder().hashCode());
            this.mRegistration.getKeyAsync(
                    i, cancellationSignal, this.mExecutor, new AnonymousClass1(iGetKeyCallback));
        } catch (Exception e) {
            Log.e(
                    "RemoteProvisionSysSvc",
                    "getKeyAsync threw an exception for client "
                            + iGetKeyCallback.asBinder().hashCode(),
                    e);
            this.mGetKeyOperations.remove(iGetKeyCallback.asBinder());
            try {
                iGetKeyCallback.onError((byte) 1, e.getMessage());
            } catch (Exception e2) {
                Log.e("RemoteProvisionSysSvc", "Error invoking callback on client binder", e2);
            }
        }
    }

    public final void storeUpgradedKeyAsync(
            byte[] bArr, byte[] bArr2, IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) {
        if (!this.mStoreUpgradedKeyOperations.add(iStoreUpgradedKeyCallback.asBinder())) {
            throw new IllegalArgumentException(
                    "Callback is already associated with an existing operation: "
                            + iStoreUpgradedKeyCallback.asBinder().hashCode());
        }
        try {
            this.mRegistration.storeUpgradedKeyAsync(
                    bArr, bArr2, this.mExecutor, new AnonymousClass1(iStoreUpgradedKeyCallback));
        } catch (Exception e) {
            Log.e(
                    "RemoteProvisionSysSvc",
                    "storeUpgradedKeyAsync threw an exception for client "
                            + iStoreUpgradedKeyCallback.asBinder().hashCode(),
                    e);
            this.mStoreUpgradedKeyOperations.remove(iStoreUpgradedKeyCallback.asBinder());
            try {
                iStoreUpgradedKeyCallback.onError(e.getMessage());
            } catch (Exception e2) {
                Log.e("RemoteProvisionSysSvc", "Error invoking callback on client binder", e2);
            }
        }
    }
}
