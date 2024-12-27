package com.samsung.android.gesture;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMotionRecognitionService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.gesture.IMotionRecognitionService";

    void enableSARDevice(boolean z, long j, int i, int i2) throws RemoteException;

    String getEvLuxTableInfo(String str) throws RemoteException;

    float[] getEvToLux(float[] fArr) throws RemoteException;

    boolean getPickUpMotionStatus() throws RemoteException;

    boolean getSSPstatus() throws RemoteException;

    boolean isAvailable(int i) throws RemoteException;

    void registerCallback(IBinder iBinder, int i, int i2) throws RemoteException;

    int resetMotionEngine() throws RemoteException;

    void setMotionAngle(IBinder iBinder, int i) throws RemoteException;

    void setMotionTiltLevel(int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    boolean setTestSensor() throws RemoteException;

    void startAdaptiveBrightness() throws RemoteException;

    void stopAdaptiveBrightness() throws RemoteException;

    void unregisterCallback(IBinder iBinder) throws RemoteException;

    void useMotionAlways(IBinder iBinder, boolean z) throws RemoteException;

    public static class Default implements IMotionRecognitionService {
        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void registerCallback(IBinder binder, int motion_sensors, int motion_events)
                throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean getSSPstatus() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean getPickUpMotionStatus() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void unregisterCallback(IBinder binder) throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void useMotionAlways(IBinder binder, boolean bUseAlways) throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void setMotionAngle(IBinder binder, int status) throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void setMotionTiltLevel(
                int stopUp,
                int level1Up,
                int level2Up,
                int stopDown,
                int level1Down,
                int level2Down)
                throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public int resetMotionEngine() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean isAvailable(int type) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public float[] getEvToLux(float[] values) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public String getEvLuxTableInfo(String info) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public boolean setTestSensor() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void startAdaptiveBrightness() throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void stopAdaptiveBrightness() throws RemoteException {}

        @Override // com.samsung.android.gesture.IMotionRecognitionService
        public void enableSARDevice(boolean enable, long deviceId, int slot, int extId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMotionRecognitionService {
        static final int TRANSACTION_enableSARDevice = 15;
        static final int TRANSACTION_getEvLuxTableInfo = 11;
        static final int TRANSACTION_getEvToLux = 10;
        static final int TRANSACTION_getPickUpMotionStatus = 3;
        static final int TRANSACTION_getSSPstatus = 2;
        static final int TRANSACTION_isAvailable = 9;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_resetMotionEngine = 8;
        static final int TRANSACTION_setMotionAngle = 6;
        static final int TRANSACTION_setMotionTiltLevel = 7;
        static final int TRANSACTION_setTestSensor = 12;
        static final int TRANSACTION_startAdaptiveBrightness = 13;
        static final int TRANSACTION_stopAdaptiveBrightness = 14;
        static final int TRANSACTION_unregisterCallback = 4;
        static final int TRANSACTION_useMotionAlways = 5;

        public Stub() {
            attachInterface(this, IMotionRecognitionService.DESCRIPTOR);
        }

        public static IMotionRecognitionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMotionRecognitionService.DESCRIPTOR);
            if (iin != null && (iin instanceof IMotionRecognitionService)) {
                return (IMotionRecognitionService) iin;
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
                    return "getSSPstatus";
                case 3:
                    return "getPickUpMotionStatus";
                case 4:
                    return "unregisterCallback";
                case 5:
                    return "useMotionAlways";
                case 6:
                    return "setMotionAngle";
                case 7:
                    return "setMotionTiltLevel";
                case 8:
                    return "resetMotionEngine";
                case 9:
                    return "isAvailable";
                case 10:
                    return "getEvToLux";
                case 11:
                    return "getEvLuxTableInfo";
                case 12:
                    return "setTestSensor";
                case 13:
                    return "startAdaptiveBrightness";
                case 14:
                    return "stopAdaptiveBrightness";
                case 15:
                    return "enableSARDevice";
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
                data.enforceInterface(IMotionRecognitionService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMotionRecognitionService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    registerCallback(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    boolean _result = getSSPstatus();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 3:
                    boolean _result2 = getPickUpMotionStatus();
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 4:
                    IBinder _arg02 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unregisterCallback(_arg02);
                    reply.writeNoException();
                    return true;
                case 5:
                    IBinder _arg03 = data.readStrongBinder();
                    boolean _arg12 = data.readBoolean();
                    data.enforceNoDataAvail();
                    useMotionAlways(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 6:
                    IBinder _arg04 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    setMotionAngle(_arg04, _arg13);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg05 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    setMotionTiltLevel(_arg05, _arg14, _arg22, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _result3 = resetMotionEngine();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 9:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result4 = isAvailable(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result4);
                    return true;
                case 10:
                    float[] _arg07 = data.createFloatArray();
                    data.enforceNoDataAvail();
                    float[] _result5 = getEvToLux(_arg07);
                    reply.writeNoException();
                    reply.writeFloatArray(_result5);
                    return true;
                case 11:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    String _result6 = getEvLuxTableInfo(_arg08);
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 12:
                    boolean _result7 = setTestSensor();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 13:
                    startAdaptiveBrightness();
                    reply.writeNoException();
                    return true;
                case 14:
                    stopAdaptiveBrightness();
                    reply.writeNoException();
                    return true;
                case 15:
                    boolean _arg09 = data.readBoolean();
                    long _arg15 = data.readLong();
                    int _arg23 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    enableSARDevice(_arg09, _arg15, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMotionRecognitionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMotionRecognitionService.DESCRIPTOR;
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void registerCallback(IBinder binder, int motion_sensors, int motion_events)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(motion_sensors);
                    _data.writeInt(motion_events);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean getSSPstatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean getPickUpMotionStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void unregisterCallback(IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void useMotionAlways(IBinder binder, boolean bUseAlways) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeBoolean(bUseAlways);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void setMotionAngle(IBinder binder, int status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeInt(status);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void setMotionTiltLevel(
                    int stopUp,
                    int level1Up,
                    int level2Up,
                    int stopDown,
                    int level1Down,
                    int level2Down)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeInt(stopUp);
                    _data.writeInt(level1Up);
                    _data.writeInt(level2Up);
                    _data.writeInt(stopDown);
                    _data.writeInt(level1Down);
                    _data.writeInt(level2Down);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public int resetMotionEngine() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean isAvailable(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public float[] getEvToLux(float[] values) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeFloatArray(values);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public String getEvLuxTableInfo(String info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeString(info);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public boolean setTestSensor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void startAdaptiveBrightness() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void stopAdaptiveBrightness() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.gesture.IMotionRecognitionService
            public void enableSARDevice(boolean enable, long deviceId, int slot, int extId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMotionRecognitionService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    _data.writeLong(deviceId);
                    _data.writeInt(slot);
                    _data.writeInt(extId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
