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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubEndpointDiscoveryCallback)) {
                return (IContextHubEndpointDiscoveryCallback) queryLocalInterface;
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
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEndpointsStopped(hubEndpointInfoArr2, readInt);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
                    obtain.writeTypedArray(hubEndpointInfoArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.contexthub.IContextHubEndpointDiscoveryCallback
            public void onEndpointsStopped(HubEndpointInfo[] hubEndpointInfoArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContextHubEndpointDiscoveryCallback.DESCRIPTOR);
                    obtain.writeTypedArray(hubEndpointInfoArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
