package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsRegistrationCallback;

/* loaded from: classes4.dex */
public interface IImsRegistration extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistration";

    public static class Default implements IImsRegistration {
        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public int getRegistrationTechnology() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerDeregistration(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerFullNetworkRegistration(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerSipDelegateDeregistration() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerUpdateSipDelegateRegistration() throws RemoteException {
        }
    }

    void addEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    int getRegistrationTechnology() throws RemoteException;

    void removeEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException;

    void triggerDeregistration(int i) throws RemoteException;

    void triggerFullNetworkRegistration(int i, String str) throws RemoteException;

    void triggerSipDelegateDeregistration() throws RemoteException;

    void triggerUpdateSipDelegateRegistration() throws RemoteException;

    public static abstract class Stub extends Binder implements IImsRegistration {
        static final int TRANSACTION_addEmergencyRegistrationCallback = 4;
        static final int TRANSACTION_addRegistrationCallback = 2;
        static final int TRANSACTION_getRegistrationTechnology = 1;
        static final int TRANSACTION_removeEmergencyRegistrationCallback = 5;
        static final int TRANSACTION_removeRegistrationCallback = 3;
        static final int TRANSACTION_triggerDeregistration = 9;
        static final int TRANSACTION_triggerFullNetworkRegistration = 6;
        static final int TRANSACTION_triggerSipDelegateDeregistration = 8;
        static final int TRANSACTION_triggerUpdateSipDelegateRegistration = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IImsRegistration.DESCRIPTOR);
        }

        public static IImsRegistration asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsRegistration.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsRegistration)) {
                return (IImsRegistration) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getRegistrationTechnology";
                case 2:
                    return "addRegistrationCallback";
                case 3:
                    return "removeRegistrationCallback";
                case 4:
                    return "addEmergencyRegistrationCallback";
                case 5:
                    return "removeEmergencyRegistrationCallback";
                case 6:
                    return "triggerFullNetworkRegistration";
                case 7:
                    return "triggerUpdateSipDelegateRegistration";
                case 8:
                    return "triggerSipDelegateDeregistration";
                case 9:
                    return "triggerDeregistration";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsRegistration.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsRegistration.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int registrationTechnology = getRegistrationTechnology();
                    parcel2.writeNoException();
                    parcel2.writeInt(registrationTechnology);
                    return true;
                case 2:
                    IImsRegistrationCallback asInterface = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationCallback(asInterface);
                    return true;
                case 3:
                    IImsRegistrationCallback asInterface2 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRegistrationCallback(asInterface2);
                    return true;
                case 4:
                    IImsRegistrationCallback asInterface3 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addEmergencyRegistrationCallback(asInterface3);
                    return true;
                case 5:
                    IImsRegistrationCallback asInterface4 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeEmergencyRegistrationCallback(asInterface4);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerFullNetworkRegistration(readInt, readString);
                    return true;
                case 7:
                    triggerUpdateSipDelegateRegistration();
                    return true;
                case 8:
                    triggerSipDelegateDeregistration();
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerDeregistration(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsRegistration {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsRegistration.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public int getRegistrationTechnology() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerFullNetworkRegistration(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerUpdateSipDelegateRegistration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerSipDelegateDeregistration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerDeregistration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
