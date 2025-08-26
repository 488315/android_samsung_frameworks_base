package android.hardware.contexthub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubEndpointDiscoveryCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.contexthub.IContextHubEndpointDiscoveryCallback";

    public static class Default implements IContextHubEndpointDiscoveryCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
        public void onEndpointsStarted(HubEndpointInfo[] hubEndpointInfoArr) throws RemoteException {
        }

        @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
        public void onEndpointsStopped(HubEndpointInfo[] hubEndpointInfoArr, int i) throws RemoteException {
        }
    }

    void onEndpointsStarted(HubEndpointInfo[] hubEndpointInfoArr) throws RemoteException;

    void onEndpointsStopped(HubEndpointInfo[] hubEndpointInfoArr, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubEndpointDiscoveryCallback {
        static final int TRANSACTION_onEndpointsStarted = 1;
        static final int TRANSACTION_onEndpointsStopped = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
        }

        public static IContextHubEndpointDiscoveryCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IContextHubEndpointDiscoveryCallback)) {
                return (IContextHubEndpointDiscoveryCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEndpointsStarted";
            }
            if (i != 2) {
                return null;
            }
            return "onEndpointsStopped";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                HubEndpointInfo[] hubEndpointInfoArr = (HubEndpointInfo[]) parcel.createTypedArray(HubEndpointInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onEndpointsStarted(hubEndpointInfoArr);
            } else if (i == 2) {
                HubEndpointInfo[] hubEndpointInfoArr2 = (HubEndpointInfo[]) parcel.createTypedArray(HubEndpointInfo.CREATOR);
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEndpointsStopped(hubEndpointInfoArr2, i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContextHubEndpointDiscoveryCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextHubEndpointDiscoveryCallback.DESCRIPTOR;
            }

            @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
            public void onEndpointsStarted(HubEndpointInfo[] hubEndpointInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
                    parcelObtain.writeTypedArray(hubEndpointInfoArr, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
            public void onEndpointsStopped(HubEndpointInfo[] hubEndpointInfoArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
                    parcelObtain.writeTypedArray(hubEndpointInfoArr, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
