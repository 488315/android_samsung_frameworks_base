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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsRegistration.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsRegistration)) {
                return (IImsRegistration) iInterfaceQueryLocalInterface;
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
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationCallback(iImsRegistrationCallbackAsInterface);
                    return true;
                case 3:
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface2 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRegistrationCallback(iImsRegistrationCallbackAsInterface2);
                    return true;
                case 4:
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface3 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addEmergencyRegistrationCallback(iImsRegistrationCallbackAsInterface3);
                    return true;
                case 5:
                    IImsRegistrationCallback iImsRegistrationCallbackAsInterface4 = IImsRegistrationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeEmergencyRegistrationCallback(iImsRegistrationCallbackAsInterface4);
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    triggerFullNetworkRegistration(i3, string);
                    return true;
                case 7:
                    triggerUpdateSipDelegateRegistration();
                    return true;
                case 8:
                    triggerSipDelegateDeregistration();
                    return true;
                case 9:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerDeregistration(i4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerFullNetworkRegistration(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerUpdateSipDelegateRegistration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerSipDelegateDeregistration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerDeregistration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
