package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICompanionDeviceService extends IInterface {
    public static final String DESCRIPTOR = "android.companion.ICompanionDeviceService";

    void onDeviceAppeared(AssociationInfo associationInfo) throws RemoteException;

    void onDeviceDisappeared(AssociationInfo associationInfo) throws RemoteException;

    void onDevicePresenceEvent(DevicePresenceEvent devicePresenceEvent) throws RemoteException;

    public static class Default implements ICompanionDeviceService {
        @Override // android.companion.ICompanionDeviceService
        public void onDeviceAppeared(AssociationInfo associationInfo) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceDisappeared(AssociationInfo associationInfo) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDevicePresenceEvent(DevicePresenceEvent event) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICompanionDeviceService {
        static final int TRANSACTION_onDeviceAppeared = 1;
        static final int TRANSACTION_onDeviceDisappeared = 2;
        static final int TRANSACTION_onDevicePresenceEvent = 3;

        public Stub() {
            attachInterface(this, ICompanionDeviceService.DESCRIPTOR);
        }

        public static ICompanionDeviceService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICompanionDeviceService.DESCRIPTOR);
            if (iin != null && (iin instanceof ICompanionDeviceService)) {
                return (ICompanionDeviceService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onDeviceAppeared";
                case 2:
                    return "onDeviceDisappeared";
                case 3:
                    return "onDevicePresenceEvent";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICompanionDeviceService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICompanionDeviceService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    AssociationInfo _arg0 = (AssociationInfo) data.readTypedObject(AssociationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onDeviceAppeared(_arg0);
                    return true;
                case 2:
                    AssociationInfo _arg02 = (AssociationInfo) data.readTypedObject(AssociationInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onDeviceDisappeared(_arg02);
                    return true;
                case 3:
                    DevicePresenceEvent _arg03 = (DevicePresenceEvent) data.readTypedObject(DevicePresenceEvent.CREATOR);
                    data.enforceNoDataAvail();
                    onDevicePresenceEvent(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICompanionDeviceService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICompanionDeviceService.DESCRIPTOR;
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDeviceAppeared(AssociationInfo associationInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICompanionDeviceService.DESCRIPTOR);
                    _data.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDeviceDisappeared(AssociationInfo associationInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICompanionDeviceService.DESCRIPTOR);
                    _data.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDevicePresenceEvent(DevicePresenceEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICompanionDeviceService.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
