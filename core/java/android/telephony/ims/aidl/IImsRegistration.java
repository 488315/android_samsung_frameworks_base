package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsRegistration extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistration";

    void addEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback)
            throws RemoteException;

    void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback)
            throws RemoteException;

    int getRegistrationTechnology() throws RemoteException;

    void removeEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback)
            throws RemoteException;

    void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback)
            throws RemoteException;

    void triggerDeregistration(int i) throws RemoteException;

    void triggerFullNetworkRegistration(int i, String str) throws RemoteException;

    void triggerSipDelegateDeregistration() throws RemoteException;

    void triggerUpdateSipDelegateRegistration() throws RemoteException;

    public static class Default implements IImsRegistration {
        @Override // android.telephony.ims.aidl.IImsRegistration
        public int getRegistrationTechnology() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addRegistrationCallback(IImsRegistrationCallback c) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeRegistrationCallback(IImsRegistrationCallback c) throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addEmergencyRegistrationCallback(IImsRegistrationCallback c)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeEmergencyRegistrationCallback(IImsRegistrationCallback c)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerFullNetworkRegistration(int sipCode, String sipReason)
                throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerUpdateSipDelegateRegistration() throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerSipDelegateDeregistration() throws RemoteException {}

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerDeregistration(int reason) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IImsRegistration {
        static final int TRANSACTION_addEmergencyRegistrationCallback = 4;
        static final int TRANSACTION_addRegistrationCallback = 2;
        static final int TRANSACTION_getRegistrationTechnology = 1;
        static final int TRANSACTION_removeEmergencyRegistrationCallback = 5;
        static final int TRANSACTION_removeRegistrationCallback = 3;
        static final int TRANSACTION_triggerDeregistration = 9;
        static final int TRANSACTION_triggerFullNetworkRegistration = 6;
        static final int TRANSACTION_triggerSipDelegateDeregistration = 8;
        static final int TRANSACTION_triggerUpdateSipDelegateRegistration = 7;

        public Stub() {
            attachInterface(this, IImsRegistration.DESCRIPTOR);
        }

        public static IImsRegistration asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsRegistration.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsRegistration)) {
                return (IImsRegistration) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IImsRegistration.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImsRegistration.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _result = getRegistrationTechnology();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    IImsRegistrationCallback _arg0 =
                            IImsRegistrationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addRegistrationCallback(_arg0);
                    return true;
                case 3:
                    IImsRegistrationCallback _arg02 =
                            IImsRegistrationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeRegistrationCallback(_arg02);
                    return true;
                case 4:
                    IImsRegistrationCallback _arg03 =
                            IImsRegistrationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addEmergencyRegistrationCallback(_arg03);
                    return true;
                case 5:
                    IImsRegistrationCallback _arg04 =
                            IImsRegistrationCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeEmergencyRegistrationCallback(_arg04);
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    triggerFullNetworkRegistration(_arg05, _arg1);
                    return true;
                case 7:
                    triggerUpdateSipDelegateRegistration();
                    return true;
                case 8:
                    triggerSipDelegateDeregistration();
                    return true;
                case 9:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    triggerDeregistration(_arg06);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImsRegistration {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addRegistrationCallback(IImsRegistrationCallback c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeRegistrationCallback(IImsRegistrationCallback c)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void addEmergencyRegistrationCallback(IImsRegistrationCallback c)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void removeEmergencyRegistrationCallback(IImsRegistrationCallback c)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    _data.writeStrongInterface(c);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerFullNetworkRegistration(int sipCode, String sipReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    _data.writeInt(sipCode);
                    _data.writeString(sipReason);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerUpdateSipDelegateRegistration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerSipDelegateDeregistration() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsRegistration
            public void triggerDeregistration(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsRegistration.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
