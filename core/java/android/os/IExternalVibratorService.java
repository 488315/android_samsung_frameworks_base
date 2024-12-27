package android.os;

/* loaded from: classes3.dex */
public interface IExternalVibratorService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IExternalVibratorService";

    ExternalVibrationScale onExternalVibrationStart(ExternalVibration externalVibration)
            throws RemoteException;

    void onExternalVibrationStop(ExternalVibration externalVibration) throws RemoteException;

    boolean shouldIgnoreExternalVibrationLocked(int i, int i2, int i3, int i4)
            throws RemoteException;

    public static class Default implements IExternalVibratorService {
        @Override // android.os.IExternalVibratorService
        public ExternalVibrationScale onExternalVibrationStart(ExternalVibration vib)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IExternalVibratorService
        public void onExternalVibrationStop(ExternalVibration vib) throws RemoteException {}

        @Override // android.os.IExternalVibratorService
        public boolean shouldIgnoreExternalVibrationLocked(
                int content_type, int usage, int source, int flags) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IExternalVibratorService {
        static final int TRANSACTION_onExternalVibrationStart = 1;
        static final int TRANSACTION_onExternalVibrationStop = 2;
        static final int TRANSACTION_shouldIgnoreExternalVibrationLocked = 3;

        public Stub() {
            attachInterface(this, IExternalVibratorService.DESCRIPTOR);
        }

        public static IExternalVibratorService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IExternalVibratorService.DESCRIPTOR);
            if (iin != null && (iin instanceof IExternalVibratorService)) {
                return (IExternalVibratorService) iin;
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
                    return "onExternalVibrationStart";
                case 2:
                    return "onExternalVibrationStop";
                case 3:
                    return "shouldIgnoreExternalVibrationLocked";
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
                data.enforceInterface(IExternalVibratorService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IExternalVibratorService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ExternalVibration _arg0 =
                            (ExternalVibration) data.readTypedObject(ExternalVibration.CREATOR);
                    data.enforceNoDataAvail();
                    ExternalVibrationScale _result = onExternalVibrationStart(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    ExternalVibration _arg02 =
                            (ExternalVibration) data.readTypedObject(ExternalVibration.CREATOR);
                    data.enforceNoDataAvail();
                    onExternalVibrationStop(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 =
                            shouldIgnoreExternalVibrationLocked(_arg03, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IExternalVibratorService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExternalVibratorService.DESCRIPTOR;
            }

            @Override // android.os.IExternalVibratorService
            public ExternalVibrationScale onExternalVibrationStart(ExternalVibration vib)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExternalVibratorService.DESCRIPTOR);
                    _data.writeTypedObject(vib, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ExternalVibrationScale _result =
                            (ExternalVibrationScale)
                                    _reply.readTypedObject(ExternalVibrationScale.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IExternalVibratorService
            public void onExternalVibrationStop(ExternalVibration vib) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExternalVibratorService.DESCRIPTOR);
                    _data.writeTypedObject(vib, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IExternalVibratorService
            public boolean shouldIgnoreExternalVibrationLocked(
                    int content_type, int usage, int source, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IExternalVibratorService.DESCRIPTOR);
                    _data.writeInt(content_type);
                    _data.writeInt(usage);
                    _data.writeInt(source);
                    _data.writeInt(flags);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
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
