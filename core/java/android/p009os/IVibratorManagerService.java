package android.p009os;

import android.p009os.IVibratorStateListener;
import com.samsung.android.edge.EdgeManagerInternal;

/* loaded from: classes3.dex */
public interface IVibratorManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IVibratorManagerService";

    void cancelVibrate(int i, IBinder iBinder) throws RemoteException;

    String executeVibrationDebugCommand(int i) throws RemoteException;

    int getSupportedVibratorGroup() throws RemoteException;

    int[] getVibratorIds() throws RemoteException;

    VibratorInfo getVibratorInfo(int i) throws RemoteException;

    boolean isVibrating(int i) throws RemoteException;

    boolean registerVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException;

    int semGetNumberOfSupportedPatterns() throws RemoteException;

    boolean setAlwaysOnEffect(int i, String str, int i2, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes) throws RemoteException;

    boolean unregisterVibratorStateListener(int i, IVibratorStateListener iVibratorStateListener) throws RemoteException;

    void vibrate(int i, int i2, String str, CombinedVibration combinedVibration, VibrationAttributes vibrationAttributes, String str2, IBinder iBinder) throws RemoteException;

    public static class Default implements IVibratorManagerService {
        @Override // android.p009os.IVibratorManagerService
        public int[] getVibratorIds() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVibratorManagerService
        public VibratorInfo getVibratorInfo(int vibratorId) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IVibratorManagerService
        public boolean isVibrating(int vibratorId) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVibratorManagerService
        public boolean registerVibratorStateListener(int vibratorId, IVibratorStateListener listener) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVibratorManagerService
        public boolean unregisterVibratorStateListener(int vibratorId, IVibratorStateListener listener) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVibratorManagerService
        public boolean setAlwaysOnEffect(int uid, String opPkg, int alwaysOnId, CombinedVibration vibration, VibrationAttributes attributes) throws RemoteException {
            return false;
        }

        @Override // android.p009os.IVibratorManagerService
        public void vibrate(int uid, int displayId, String opPkg, CombinedVibration vibration, VibrationAttributes attributes, String reason, IBinder token) throws RemoteException {
        }

        @Override // android.p009os.IVibratorManagerService
        public void cancelVibrate(int usageFilter, IBinder token) throws RemoteException {
        }

        @Override // android.p009os.IVibratorManagerService
        public int semGetNumberOfSupportedPatterns() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IVibratorManagerService
        public int getSupportedVibratorGroup() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IVibratorManagerService
        public String executeVibrationDebugCommand(int param) throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVibratorManagerService {
        static final int TRANSACTION_cancelVibrate = 8;
        static final int TRANSACTION_executeVibrationDebugCommand = 11;
        static final int TRANSACTION_getSupportedVibratorGroup = 10;
        static final int TRANSACTION_getVibratorIds = 1;
        static final int TRANSACTION_getVibratorInfo = 2;
        static final int TRANSACTION_isVibrating = 3;
        static final int TRANSACTION_registerVibratorStateListener = 4;
        static final int TRANSACTION_semGetNumberOfSupportedPatterns = 9;
        static final int TRANSACTION_setAlwaysOnEffect = 6;
        static final int TRANSACTION_unregisterVibratorStateListener = 5;
        static final int TRANSACTION_vibrate = 7;

        public Stub() {
            attachInterface(this, IVibratorManagerService.DESCRIPTOR);
        }

        public static IVibratorManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVibratorManagerService.DESCRIPTOR);
            if (iin != null && (iin instanceof IVibratorManagerService)) {
                return (IVibratorManagerService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getVibratorIds";
                case 2:
                    return "getVibratorInfo";
                case 3:
                    return "isVibrating";
                case 4:
                    return "registerVibratorStateListener";
                case 5:
                    return "unregisterVibratorStateListener";
                case 6:
                    return "setAlwaysOnEffect";
                case 7:
                    return EdgeManagerInternal.NOTIFICATION_KEY_VIBRATE;
                case 8:
                    return "cancelVibrate";
                case 9:
                    return "semGetNumberOfSupportedPatterns";
                case 10:
                    return "getSupportedVibratorGroup";
                case 11:
                    return "executeVibrationDebugCommand";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IVibratorManagerService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IVibratorManagerService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int[] _result = getVibratorIds();
                            reply.writeNoException();
                            reply.writeIntArray(_result);
                            return true;
                        case 2:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            VibratorInfo _result2 = getVibratorInfo(_arg0);
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 3:
                            int _arg02 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result3 = isVibrating(_arg02);
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 4:
                            int _arg03 = data.readInt();
                            IVibratorStateListener _arg1 = IVibratorStateListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            boolean _result4 = registerVibratorStateListener(_arg03, _arg1);
                            reply.writeNoException();
                            reply.writeBoolean(_result4);
                            return true;
                        case 5:
                            int _arg04 = data.readInt();
                            IVibratorStateListener _arg12 = IVibratorStateListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            boolean _result5 = unregisterVibratorStateListener(_arg04, _arg12);
                            reply.writeNoException();
                            reply.writeBoolean(_result5);
                            return true;
                        case 6:
                            int _arg05 = data.readInt();
                            String _arg13 = data.readString();
                            int _arg2 = data.readInt();
                            CombinedVibration _arg3 = (CombinedVibration) data.readTypedObject(CombinedVibration.CREATOR);
                            VibrationAttributes _arg4 = (VibrationAttributes) data.readTypedObject(VibrationAttributes.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result6 = setAlwaysOnEffect(_arg05, _arg13, _arg2, _arg3, _arg4);
                            reply.writeNoException();
                            reply.writeBoolean(_result6);
                            return true;
                        case 7:
                            int _arg06 = data.readInt();
                            int _arg14 = data.readInt();
                            String _arg22 = data.readString();
                            CombinedVibration _arg32 = (CombinedVibration) data.readTypedObject(CombinedVibration.CREATOR);
                            VibrationAttributes _arg42 = (VibrationAttributes) data.readTypedObject(VibrationAttributes.CREATOR);
                            String _arg5 = data.readString();
                            IBinder _arg6 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            vibrate(_arg06, _arg14, _arg22, _arg32, _arg42, _arg5, _arg6);
                            reply.writeNoException();
                            return true;
                        case 8:
                            int _arg07 = data.readInt();
                            IBinder _arg15 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            cancelVibrate(_arg07, _arg15);
                            reply.writeNoException();
                            return true;
                        case 9:
                            int _result7 = semGetNumberOfSupportedPatterns();
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 10:
                            int _result8 = getSupportedVibratorGroup();
                            reply.writeNoException();
                            reply.writeInt(_result8);
                            return true;
                        case 11:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result9 = executeVibrationDebugCommand(_arg08);
                            reply.writeNoException();
                            reply.writeString(_result9);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IVibratorManagerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVibratorManagerService.DESCRIPTOR;
            }

            @Override // android.p009os.IVibratorManagerService
            public int[] getVibratorIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public VibratorInfo getVibratorInfo(int vibratorId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(vibratorId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    VibratorInfo _result = (VibratorInfo) _reply.readTypedObject(VibratorInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public boolean isVibrating(int vibratorId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(vibratorId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public boolean registerVibratorStateListener(int vibratorId, IVibratorStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(vibratorId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public boolean unregisterVibratorStateListener(int vibratorId, IVibratorStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(vibratorId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public boolean setAlwaysOnEffect(int uid, String opPkg, int alwaysOnId, CombinedVibration vibration, VibrationAttributes attributes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(opPkg);
                    _data.writeInt(alwaysOnId);
                    _data.writeTypedObject(vibration, 0);
                    _data.writeTypedObject(attributes, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public void vibrate(int uid, int displayId, String opPkg, CombinedVibration vibration, VibrationAttributes attributes, String reason, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(displayId);
                    _data.writeString(opPkg);
                    _data.writeTypedObject(vibration, 0);
                    _data.writeTypedObject(attributes, 0);
                    _data.writeString(reason);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public void cancelVibrate(int usageFilter, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(usageFilter);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public int semGetNumberOfSupportedPatterns() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public int getSupportedVibratorGroup() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.p009os.IVibratorManagerService
            public String executeVibrationDebugCommand(int param) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVibratorManagerService.DESCRIPTOR);
                    _data.writeInt(param);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
