package com.samsung.android.hardware.context;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.Map;

public interface ISemContextService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.hardware.context.ISemContextService";

    boolean changeParameters(IBinder iBinder, int i, SemContextAttribute semContextAttribute)
            throws RemoteException;

    Map getAvailableServiceMap() throws RemoteException;

    String getCurrentServiceList() throws RemoteException;

    void initializeService(IBinder iBinder, int i) throws RemoteException;

    void registerCallback(
            IBinder iBinder, int i, SemContextAttribute semContextAttribute, String str)
            throws RemoteException;

    void requestHistoryData(IBinder iBinder, int i, String str) throws RemoteException;

    void requestToUpdate(IBinder iBinder, int i, String str) throws RemoteException;

    boolean setReferenceData(int i, int i2, byte[] bArr) throws RemoteException;

    boolean unregisterCallback(IBinder iBinder, int i) throws RemoteException;

    public static class Default implements ISemContextService {
        @Override // com.samsung.android.hardware.context.ISemContextService
        public void registerCallback(
                IBinder binder, int service, SemContextAttribute property, String packageName)
                throws RemoteException {}

        @Override // com.samsung.android.hardware.context.ISemContextService
        public boolean unregisterCallback(IBinder binder, int service) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void initializeService(IBinder binder, int service) throws RemoteException {}

        @Override // com.samsung.android.hardware.context.ISemContextService
        public boolean changeParameters(IBinder binder, int service, SemContextAttribute attribute)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public Map getAvailableServiceMap() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public boolean setReferenceData(int service, int data_type, byte[] data)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void requestToUpdate(IBinder binder, int service, String packageName)
                throws RemoteException {}

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void requestHistoryData(IBinder binder, int service, String packageName)
                throws RemoteException {}

        @Override // com.samsung.android.hardware.context.ISemContextService
        public String getCurrentServiceList() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemContextService {
        static final int TRANSACTION_changeParameters = 4;
        static final int TRANSACTION_getAvailableServiceMap = 5;
        static final int TRANSACTION_getCurrentServiceList = 9;
        static final int TRANSACTION_initializeService = 3;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_requestHistoryData = 8;
        static final int TRANSACTION_requestToUpdate = 7;
        static final int TRANSACTION_setReferenceData = 6;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            attachInterface(this, ISemContextService.DESCRIPTOR);
        }

        public static ISemContextService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemContextService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemContextService)) {
                return (ISemContextService) iin;
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
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "initializeService";
                case 4:
                    return "changeParameters";
                case 5:
                    return "getAvailableServiceMap";
                case 6:
                    return "setReferenceData";
                case 7:
                    return "requestToUpdate";
                case 8:
                    return "requestHistoryData";
                case 9:
                    return "getCurrentServiceList";
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
                data.enforceInterface(ISemContextService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemContextService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    SemContextAttribute _arg2 =
                            (SemContextAttribute) data.readTypedObject(SemContextAttribute.CREATOR);
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    registerCallback(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                case 2:
                    IBinder _arg02 = data.readStrongBinder();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result = unregisterCallback(_arg02, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    initializeService(_arg03, _arg13);
                    reply.writeNoException();
                    return true;
                case 4:
                    IBinder _arg04 = data.readStrongBinder();
                    int _arg14 = data.readInt();
                    SemContextAttribute _arg22 =
                            (SemContextAttribute) data.readTypedObject(SemContextAttribute.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result2 = changeParameters(_arg04, _arg14, _arg22);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 5:
                    Map _result3 = getAvailableServiceMap();
                    reply.writeNoException();
                    reply.writeMap(_result3);
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    int _arg15 = data.readInt();
                    byte[] _arg23 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result4 = setReferenceData(_arg05, _arg15, _arg23);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 7:
                    IBinder _arg06 = data.readStrongBinder();
                    int _arg16 = data.readInt();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    requestToUpdate(_arg06, _arg16, _arg24);
                    reply.writeNoException();
                    return true;
                case 8:
                    IBinder _arg07 = data.readStrongBinder();
                    int _arg17 = data.readInt();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    requestHistoryData(_arg07, _arg17, _arg25);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _result5 = getCurrentServiceList();
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemContextService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContextService.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void registerCallback(
                    IBinder binder, int service, SemContextAttribute property, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(service);
                    _data.writeTypedObject(property, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean unregisterCallback(IBinder binder, int service) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(service);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void initializeService(IBinder binder, int service) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(service);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean changeParameters(
                    IBinder binder, int service, SemContextAttribute attribute)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(service);
                    _data.writeTypedObject(attribute, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public Map getAvailableServiceMap() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean setReferenceData(int service, int data_type, byte[] data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeInt(service);
                    _data.writeInt(data_type);
                    _data.writeByteArray(data);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void requestToUpdate(IBinder binder, int service, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(service);
                    _data.writeString(packageName);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void requestHistoryData(IBinder binder, int service, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(service);
                    _data.writeString(packageName);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public String getCurrentServiceList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
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
