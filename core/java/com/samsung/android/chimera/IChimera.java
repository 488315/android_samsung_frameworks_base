package com.samsung.android.chimera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.chimera.genie.MemRequest;

import java.util.List;

public interface IChimera extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.chimera.IChimera";

    List<PSIAvailableMem> getAvailableMemInfo(long j, long j2) throws RemoteException;

    void prepareMemory(MemRequest memRequest) throws RemoteException;

    void setGenieSessionEnd() throws RemoteException;

    void setGenieSessionStart() throws RemoteException;

    public static class Default implements IChimera {
        @Override // com.samsung.android.chimera.IChimera
        public List<PSIAvailableMem> getAvailableMemInfo(long startTime, long endTime)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.chimera.IChimera
        public void prepareMemory(MemRequest memRequest) throws RemoteException {}

        @Override // com.samsung.android.chimera.IChimera
        public void setGenieSessionStart() throws RemoteException {}

        @Override // com.samsung.android.chimera.IChimera
        public void setGenieSessionEnd() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IChimera {
        static final int TRANSACTION_getAvailableMemInfo = 1;
        static final int TRANSACTION_prepareMemory = 2;
        static final int TRANSACTION_setGenieSessionEnd = 4;
        static final int TRANSACTION_setGenieSessionStart = 3;

        public Stub() {
            attachInterface(this, IChimera.DESCRIPTOR);
        }

        public static IChimera asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IChimera.DESCRIPTOR);
            if (iin != null && (iin instanceof IChimera)) {
                return (IChimera) iin;
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
                    return "getAvailableMemInfo";
                case 2:
                    return "prepareMemory";
                case 3:
                    return "setGenieSessionStart";
                case 4:
                    return "setGenieSessionEnd";
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
                data.enforceInterface(IChimera.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IChimera.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    long _arg1 = data.readLong();
                    data.enforceNoDataAvail();
                    List<PSIAvailableMem> _result = getAvailableMemInfo(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeTypedList(_result, 1);
                    return true;
                case 2:
                    MemRequest _arg02 = (MemRequest) data.readTypedObject(MemRequest.CREATOR);
                    data.enforceNoDataAvail();
                    prepareMemory(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    setGenieSessionStart();
                    reply.writeNoException();
                    return true;
                case 4:
                    setGenieSessionEnd();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IChimera {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IChimera.DESCRIPTOR;
            }

            @Override // com.samsung.android.chimera.IChimera
            public List<PSIAvailableMem> getAvailableMemInfo(long startTime, long endTime)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IChimera.DESCRIPTOR);
                    _data.writeLong(startTime);
                    _data.writeLong(endTime);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    List<PSIAvailableMem> _result =
                            _reply.createTypedArrayList(PSIAvailableMem.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.chimera.IChimera
            public void prepareMemory(MemRequest memRequest) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IChimera.DESCRIPTOR);
                    _data.writeTypedObject(memRequest, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.chimera.IChimera
            public void setGenieSessionStart() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IChimera.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.chimera.IChimera
            public void setGenieSessionEnd() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IChimera.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
