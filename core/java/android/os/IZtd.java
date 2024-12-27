package android.os;

import java.util.List;

/* loaded from: classes3.dex */
public interface IZtd extends IInterface {
    public static final String DESCRIPTOR = "android.os.IZtd";

    int startMonitoringDomains(int i, int[] iArr, List<String> list, IZtdListener iZtdListener)
            throws RemoteException;

    int startMonitoringFiles(
            int i, int[] iArr, List<String> list, List<String> list2, IZtdListener iZtdListener)
            throws RemoteException;

    void startTracing(int i, int i2, long j, IZtdListener iZtdListener) throws RemoteException;

    int stopMonitoringDomains(int i) throws RemoteException;

    int stopMonitoringFiles(int i) throws RemoteException;

    void stopTracing(int i, int i2) throws RemoteException;

    public static class Default implements IZtd {
        @Override // android.os.IZtd
        public void startTracing(int traceType, int uid, long period, IZtdListener listener)
                throws RemoteException {}

        @Override // android.os.IZtd
        public void stopTracing(int traceType, int uid) throws RemoteException {}

        @Override // android.os.IZtd
        public int startMonitoringFiles(
                int requestorUid,
                int[] allowedUids,
                List<String> files,
                List<String> inodes,
                IZtdListener listener)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public int stopMonitoringFiles(int requestorUid) throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public int startMonitoringDomains(
                int requestorUid, int[] allowedUids, List<String> domains, IZtdListener listener)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public int stopMonitoringDomains(int requestorUid) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IZtd {
        static final int TRANSACTION_startMonitoringDomains = 5;
        static final int TRANSACTION_startMonitoringFiles = 3;
        static final int TRANSACTION_startTracing = 1;
        static final int TRANSACTION_stopMonitoringDomains = 6;
        static final int TRANSACTION_stopMonitoringFiles = 4;
        static final int TRANSACTION_stopTracing = 2;

        public Stub() {
            attachInterface(this, IZtd.DESCRIPTOR);
        }

        public static IZtd asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IZtd.DESCRIPTOR);
            if (iin != null && (iin instanceof IZtd)) {
                return (IZtd) iin;
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
                    return "startTracing";
                case 2:
                    return "stopTracing";
                case 3:
                    return "startMonitoringFiles";
                case 4:
                    return "stopMonitoringFiles";
                case 5:
                    return "startMonitoringDomains";
                case 6:
                    return "stopMonitoringDomains";
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
                data.enforceInterface(IZtd.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IZtd.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    long _arg2 = data.readLong();
                    IZtdListener _arg3 = IZtdListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startTracing(_arg0, _arg1, _arg2, _arg3);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    stopTracing(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int[] _arg13 = data.createIntArray();
                    List<String> _arg22 = data.createStringArrayList();
                    List<String> _arg32 = data.createStringArrayList();
                    IZtdListener _arg4 = IZtdListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = startMonitoringFiles(_arg03, _arg13, _arg22, _arg32, _arg4);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result2 = stopMonitoringFiles(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int[] _arg14 = data.createIntArray();
                    List<String> _arg23 = data.createStringArrayList();
                    IZtdListener _arg33 = IZtdListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result3 = startMonitoringDomains(_arg05, _arg14, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = stopMonitoringDomains(_arg06);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IZtd {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IZtd.DESCRIPTOR;
            }

            @Override // android.os.IZtd
            public void startTracing(int traceType, int uid, long period, IZtdListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IZtd.DESCRIPTOR);
                    _data.writeInt(traceType);
                    _data.writeInt(uid);
                    _data.writeLong(period);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IZtd
            public void stopTracing(int traceType, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IZtd.DESCRIPTOR);
                    _data.writeInt(traceType);
                    _data.writeInt(uid);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.IZtd
            public int startMonitoringFiles(
                    int requestorUid,
                    int[] allowedUids,
                    List<String> files,
                    List<String> inodes,
                    IZtdListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IZtd.DESCRIPTOR);
                    _data.writeInt(requestorUid);
                    _data.writeIntArray(allowedUids);
                    _data.writeStringList(files);
                    _data.writeStringList(inodes);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IZtd
            public int stopMonitoringFiles(int requestorUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IZtd.DESCRIPTOR);
                    _data.writeInt(requestorUid);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IZtd
            public int startMonitoringDomains(
                    int requestorUid,
                    int[] allowedUids,
                    List<String> domains,
                    IZtdListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IZtd.DESCRIPTOR);
                    _data.writeInt(requestorUid);
                    _data.writeIntArray(allowedUids);
                    _data.writeStringList(domains);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IZtd
            public int stopMonitoringDomains(int requestorUid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IZtd.DESCRIPTOR);
                    _data.writeInt(requestorUid);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
