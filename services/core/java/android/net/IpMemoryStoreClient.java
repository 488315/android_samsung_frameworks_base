package android.net;

import android.content.Context;
import android.net.ipmemorystore.Blob;
import android.net.ipmemorystore.NetworkAttributes;
import android.net.ipmemorystore.OnBlobRetrievedListener;
import android.net.ipmemorystore.OnDeleteStatusListener;
import android.net.ipmemorystore.OnL2KeyResponseListener;
import android.net.ipmemorystore.OnNetworkAttributesRetrievedListener;
import android.net.ipmemorystore.OnSameL3NetworkResponseListener;
import android.net.ipmemorystore.OnStatusListener;
import android.net.ipmemorystore.Status;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public abstract class IpMemoryStoreClient {
    private static final String TAG = "IpMemoryStoreClient";
    private final Context mContext;

    public interface ThrowingRunnable {
        void run();
    }

    public IpMemoryStoreClient(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("missing context");
        }
        this.mContext = context;
    }

    private void ignoringRemoteException(ThrowingRunnable throwingRunnable) {
        ignoringRemoteException("Failed to execute remote procedure call", throwingRunnable);
    }

    private void ignoringRemoteException(String str, ThrowingRunnable throwingRunnable) {
        try {
            throwingRunnable.run();
        } catch (RemoteException e) {
            Log.e(TAG, str, e);
        }
    }

    public static /* synthetic */ void lambda$delete$18(
            IIpMemoryStore iIpMemoryStore,
            String str,
            boolean z,
            OnDeleteStatusListener onDeleteStatusListener)
            throws RemoteException {
        iIpMemoryStore.delete(str, z, OnDeleteStatusListener.toAIDL(onDeleteStatusListener));
    }

    public /* synthetic */ void lambda$delete$19(
            String str,
            boolean z,
            OnDeleteStatusListener onDeleteStatusListener,
            IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda10(
                        iIpMemoryStore, str, z, onDeleteStatusListener, 0));
    }

    public static /* synthetic */ void lambda$delete$20(
            OnDeleteStatusListener onDeleteStatusListener) throws RemoteException {
        onDeleteStatusListener.onComplete(new Status(-5), 0);
    }

    public static /* synthetic */ void lambda$deleteCluster$21(
            IIpMemoryStore iIpMemoryStore,
            String str,
            boolean z,
            OnDeleteStatusListener onDeleteStatusListener)
            throws RemoteException {
        iIpMemoryStore.deleteCluster(str, z, OnDeleteStatusListener.toAIDL(onDeleteStatusListener));
    }

    public /* synthetic */ void lambda$deleteCluster$22(
            String str,
            boolean z,
            OnDeleteStatusListener onDeleteStatusListener,
            IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda10(
                        iIpMemoryStore, str, z, onDeleteStatusListener, 1));
    }

    public static /* synthetic */ void lambda$deleteCluster$23(
            OnDeleteStatusListener onDeleteStatusListener) throws RemoteException {
        onDeleteStatusListener.onComplete(new Status(-5), 0);
    }

    public /* synthetic */ void lambda$factoryReset$25(IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda5(1, iIpMemoryStore));
    }

    public static /* synthetic */ void lambda$findL2Key$6(
            IIpMemoryStore iIpMemoryStore,
            NetworkAttributes networkAttributes,
            OnL2KeyResponseListener onL2KeyResponseListener)
            throws RemoteException {
        iIpMemoryStore.findL2Key(
                networkAttributes.toParcelable(),
                OnL2KeyResponseListener.toAIDL(onL2KeyResponseListener));
    }

    public /* synthetic */ void lambda$findL2Key$7(
            NetworkAttributes networkAttributes,
            OnL2KeyResponseListener onL2KeyResponseListener,
            IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda6(
                        iIpMemoryStore, networkAttributes, onL2KeyResponseListener, 1));
    }

    public static /* synthetic */ void lambda$findL2Key$8(
            OnL2KeyResponseListener onL2KeyResponseListener) throws RemoteException {
        onL2KeyResponseListener.onL2KeyResponse(new Status(-5), null);
    }

    public /* synthetic */ void lambda$isSameNetwork$10(
            String str,
            String str2,
            OnSameL3NetworkResponseListener onSameL3NetworkResponseListener,
            IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda12(
                        iIpMemoryStore, str, str2, onSameL3NetworkResponseListener, 1));
    }

    public static /* synthetic */ void lambda$isSameNetwork$11(
            OnSameL3NetworkResponseListener onSameL3NetworkResponseListener)
            throws RemoteException {
        onSameL3NetworkResponseListener.onSameL3NetworkResponse(new Status(-5), null);
    }

    public static /* synthetic */ void lambda$isSameNetwork$9(
            IIpMemoryStore iIpMemoryStore,
            String str,
            String str2,
            OnSameL3NetworkResponseListener onSameL3NetworkResponseListener)
            throws RemoteException {
        iIpMemoryStore.isSameNetwork(
                str, str2, OnSameL3NetworkResponseListener.toAIDL(onSameL3NetworkResponseListener));
    }

    public static /* synthetic */ void lambda$retrieveBlob$15(
            IIpMemoryStore iIpMemoryStore,
            String str,
            String str2,
            String str3,
            OnBlobRetrievedListener onBlobRetrievedListener)
            throws RemoteException {
        iIpMemoryStore.retrieveBlob(
                str, str2, str3, OnBlobRetrievedListener.toAIDL(onBlobRetrievedListener));
    }

    public /* synthetic */ void lambda$retrieveBlob$16(
            final String str,
            final String str2,
            final String str3,
            final OnBlobRetrievedListener onBlobRetrievedListener,
            final IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new ThrowingRunnable() { // from class:
                                         // android.net.IpMemoryStoreClient$$ExternalSyntheticLambda13
                    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                    public final void run() {
                        IpMemoryStoreClient.lambda$retrieveBlob$15(
                                IIpMemoryStore.this, str, str2, str3, onBlobRetrievedListener);
                    }
                });
    }

    public static /* synthetic */ void lambda$retrieveBlob$17(
            OnBlobRetrievedListener onBlobRetrievedListener) throws RemoteException {
        onBlobRetrievedListener.onBlobRetrieved(new Status(-5), null, null, null);
    }

    public static /* synthetic */ void lambda$retrieveNetworkAttributes$12(
            IIpMemoryStore iIpMemoryStore,
            String str,
            OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener)
            throws RemoteException {
        iIpMemoryStore.retrieveNetworkAttributes(
                str,
                OnNetworkAttributesRetrievedListener.toAIDL(onNetworkAttributesRetrievedListener));
    }

    public /* synthetic */ void lambda$retrieveNetworkAttributes$13(
            String str,
            OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener,
            IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda6(
                        iIpMemoryStore, str, onNetworkAttributesRetrievedListener, 0));
    }

    public static /* synthetic */ void lambda$retrieveNetworkAttributes$14(
            OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener)
            throws RemoteException {
        onNetworkAttributesRetrievedListener.onNetworkAttributesRetrieved(
                new Status(-5), null, null);
    }

    public static /* synthetic */ void lambda$storeBlob$3(
            IIpMemoryStore iIpMemoryStore,
            String str,
            String str2,
            String str3,
            Blob blob,
            OnStatusListener onStatusListener)
            throws RemoteException {
        iIpMemoryStore.storeBlob(str, str2, str3, blob, OnStatusListener.toAIDL(onStatusListener));
    }

    public /* synthetic */ void lambda$storeBlob$4(
            final String str,
            final String str2,
            final String str3,
            final Blob blob,
            final OnStatusListener onStatusListener,
            final IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new ThrowingRunnable() { // from class:
                                         // android.net.IpMemoryStoreClient$$ExternalSyntheticLambda19
                    @Override // android.net.IpMemoryStoreClient.ThrowingRunnable
                    public final void run() {
                        IpMemoryStoreClient.lambda$storeBlob$3(
                                IIpMemoryStore.this, str, str2, str3, blob, onStatusListener);
                    }
                });
    }

    public static /* synthetic */ void lambda$storeBlob$5(OnStatusListener onStatusListener)
            throws RemoteException {
        onStatusListener.onComplete(new Status(-5));
    }

    public static /* synthetic */ void lambda$storeNetworkAttributes$0(
            IIpMemoryStore iIpMemoryStore,
            String str,
            NetworkAttributes networkAttributes,
            OnStatusListener onStatusListener)
            throws RemoteException {
        iIpMemoryStore.storeNetworkAttributes(
                str, networkAttributes.toParcelable(), OnStatusListener.toAIDL(onStatusListener));
    }

    public /* synthetic */ void lambda$storeNetworkAttributes$1(
            String str,
            NetworkAttributes networkAttributes,
            OnStatusListener onStatusListener,
            IIpMemoryStore iIpMemoryStore) {
        ignoringRemoteException(
                new IpMemoryStoreClient$$ExternalSyntheticLambda12(
                        iIpMemoryStore, str, networkAttributes, onStatusListener, 0));
    }

    public static /* synthetic */ void lambda$storeNetworkAttributes$2(
            OnStatusListener onStatusListener) throws RemoteException {
        onStatusListener.onComplete(new Status(-5));
    }

    public void delete(String str, boolean z, OnDeleteStatusListener onDeleteStatusListener) {
        try {
            runWhenServiceReady(
                    new IpMemoryStoreClient$$ExternalSyntheticLambda2(
                            this, str, z, onDeleteStatusListener, 0));
        } catch (ExecutionException unused) {
            if (onDeleteStatusListener == null) {
                return;
            }
            ignoringRemoteException(
                    "Error deleting from the memory store",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda3(onDeleteStatusListener, 0));
        }
    }

    public void deleteCluster(
            String str, boolean z, OnDeleteStatusListener onDeleteStatusListener) {
        try {
            runWhenServiceReady(
                    new IpMemoryStoreClient$$ExternalSyntheticLambda2(
                            this, str, z, onDeleteStatusListener, 1));
        } catch (ExecutionException unused) {
            if (onDeleteStatusListener == null) {
                return;
            }
            ignoringRemoteException(
                    "Error deleting from the memory store",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda3(onDeleteStatusListener, 1));
        }
    }

    public void factoryReset() {
        try {
            runWhenServiceReady(
                    new Consumer() { // from class:
                                     // android.net.IpMemoryStoreClient$$ExternalSyntheticLambda11
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            IpMemoryStoreClient.this.lambda$factoryReset$25((IIpMemoryStore) obj);
                        }
                    });
        } catch (ExecutionException e) {
            Log.e(TAG, "Error executing factory reset", e);
        }
    }

    public void findL2Key(
            NetworkAttributes networkAttributes, OnL2KeyResponseListener onL2KeyResponseListener) {
        try {
            runWhenServiceReady(
                    new IpMemoryStoreClient$$ExternalSyntheticLambda20(
                            this, networkAttributes, onL2KeyResponseListener, 0));
        } catch (ExecutionException unused) {
            ignoringRemoteException(
                    "Error finding L2 Key",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda5(2, onL2KeyResponseListener));
        }
    }

    public void isSameNetwork(
            String str,
            String str2,
            OnSameL3NetworkResponseListener onSameL3NetworkResponseListener) {
        try {
            runWhenServiceReady(
                    new IpMemoryStoreClient$$ExternalSyntheticLambda0(
                            this, str, str2, onSameL3NetworkResponseListener, 1));
        } catch (ExecutionException unused) {
            ignoringRemoteException(
                    "Error checking for network sameness",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda5(
                            0, onSameL3NetworkResponseListener));
        }
    }

    public void retrieveBlob(
            final String str,
            final String str2,
            final String str3,
            final OnBlobRetrievedListener onBlobRetrievedListener) {
        try {
            runWhenServiceReady(
                    new Consumer() { // from class:
                                     // android.net.IpMemoryStoreClient$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            IpMemoryStoreClient.this.lambda$retrieveBlob$16(
                                    str, str2, str3, onBlobRetrievedListener, (IIpMemoryStore) obj);
                        }
                    });
        } catch (ExecutionException unused) {
            ignoringRemoteException(
                    "Error retrieving blob",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda5(4, onBlobRetrievedListener));
        }
    }

    public void retrieveNetworkAttributes(
            String str, OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener) {
        try {
            runWhenServiceReady(
                    new IpMemoryStoreClient$$ExternalSyntheticLambda20(
                            this, str, onNetworkAttributesRetrievedListener, 1));
        } catch (ExecutionException unused) {
            ignoringRemoteException(
                    "Error retrieving network attributes",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda5(
                            3, onNetworkAttributesRetrievedListener));
        }
    }

    public abstract void runWhenServiceReady(Consumer consumer) throws ExecutionException;

    public void storeBlob(
            final String str,
            final String str2,
            final String str3,
            final Blob blob,
            final OnStatusListener onStatusListener) {
        try {
            runWhenServiceReady(
                    new Consumer() { // from class:
                                     // android.net.IpMemoryStoreClient$$ExternalSyntheticLambda17
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            IpMemoryStoreClient.this.lambda$storeBlob$4(
                                    str, str2, str3, blob, onStatusListener, (IIpMemoryStore) obj);
                        }
                    });
        } catch (ExecutionException unused) {
            if (onStatusListener == null) {
                return;
            }
            ignoringRemoteException(
                    "Error storing blob",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda1(onStatusListener, 1));
        }
    }

    public void storeNetworkAttributes(
            String str, NetworkAttributes networkAttributes, OnStatusListener onStatusListener) {
        try {
            runWhenServiceReady(
                    new IpMemoryStoreClient$$ExternalSyntheticLambda0(
                            this, str, networkAttributes, onStatusListener, 0));
        } catch (ExecutionException unused) {
            if (onStatusListener == null) {
                return;
            }
            ignoringRemoteException(
                    "Error storing network attributes",
                    new IpMemoryStoreClient$$ExternalSyntheticLambda1(onStatusListener, 0));
        }
    }
}
