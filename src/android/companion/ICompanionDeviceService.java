package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICompanionDeviceService extends IInterface {
    public static final String DESCRIPTOR = "android.companion.ICompanionDeviceService";

    public static class Default implements ICompanionDeviceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceAppeared(AssociationInfo associationInfo) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDeviceDisappeared(AssociationInfo associationInfo) throws RemoteException {
        }

        @Override // android.companion.ICompanionDeviceService
        public void onDevicePresenceEvent(DevicePresenceEvent devicePresenceEvent) throws RemoteException {
        }
    }

    void onDeviceAppeared(AssociationInfo associationInfo) throws RemoteException;

    void onDeviceDisappeared(AssociationInfo associationInfo) throws RemoteException;

    void onDevicePresenceEvent(DevicePresenceEvent devicePresenceEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements ICompanionDeviceService {
        static final int TRANSACTION_onDeviceAppeared = 1;
        static final int TRANSACTION_onDeviceDisappeared = 2;
        static final int TRANSACTION_onDevicePresenceEvent = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ICompanionDeviceService.DESCRIPTOR);
        }

        public static ICompanionDeviceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICompanionDeviceService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICompanionDeviceService)) {
                return (ICompanionDeviceService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDeviceAppeared";
            }
            if (i == 2) {
                return "onDeviceDisappeared";
            }
            if (i != 3) {
                return null;
            }
            return "onDevicePresenceEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICompanionDeviceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICompanionDeviceService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AssociationInfo associationInfo = (AssociationInfo) parcel.readTypedObject(AssociationInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDeviceAppeared(associationInfo);
            } else if (i == 2) {
                AssociationInfo associationInfo2 = (AssociationInfo) parcel.readTypedObject(AssociationInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onDeviceDisappeared(associationInfo2);
            } else if (i == 3) {
                DevicePresenceEvent devicePresenceEvent = (DevicePresenceEvent) parcel.readTypedObject(DevicePresenceEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onDevicePresenceEvent(devicePresenceEvent);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICompanionDeviceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICompanionDeviceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDeviceDisappeared(AssociationInfo associationInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICompanionDeviceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(associationInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.ICompanionDeviceService
            public void onDevicePresenceEvent(DevicePresenceEvent devicePresenceEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICompanionDeviceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(devicePresenceEvent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
