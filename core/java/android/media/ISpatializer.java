package android.media;

import android.media.ISpatializerHeadTrackingCallback;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializer extends IInterface {
    public static final String DESCRIPTOR = "android$media$ISpatializer".replace('$', '.');

    byte getActualHeadTrackingMode() throws RemoteException;

    byte getLevel() throws RemoteException;

    int getOutput() throws RemoteException;

    void getParameter(int i, byte[] bArr) throws RemoteException;

    byte[] getSupportedHeadTrackingModes() throws RemoteException;

    byte[] getSupportedLevels() throws RemoteException;

    byte[] getSupportedModes() throws RemoteException;

    boolean isHeadTrackingSupported() throws RemoteException;

    void recenterHeadTracker() throws RemoteException;

    void registerHeadTrackingCallback(ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws RemoteException;

    void release() throws RemoteException;

    void setDesiredHeadTrackingMode(byte b) throws RemoteException;

    void setDisplayOrientation(float f) throws RemoteException;

    void setFoldState(boolean z) throws RemoteException;

    void setGlobalTransform(float[] fArr) throws RemoteException;

    void setHeadSensor(int i) throws RemoteException;

    void setHingeAngle(float f) throws RemoteException;

    void setLevel(byte b) throws RemoteException;

    void setParameter(int i, byte[] bArr) throws RemoteException;

    void setScreenSensor(int i) throws RemoteException;

    public static class Default implements ISpatializer {
        @Override // android.media.ISpatializer
        public void release() throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedLevels() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public void setLevel(byte level) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte getLevel() throws RemoteException {
            return (byte) 0;
        }

        @Override // android.media.ISpatializer
        public boolean isHeadTrackingSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedHeadTrackingModes() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public void setDesiredHeadTrackingMode(byte mode) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte getActualHeadTrackingMode() throws RemoteException {
            return (byte) 0;
        }

        @Override // android.media.ISpatializer
        public void recenterHeadTracker() throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setGlobalTransform(float[] screenToStage) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setHeadSensor(int sensorHandle) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setScreenSensor(int sensorHandle) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setDisplayOrientation(float physicalToLogicalAngle) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setHingeAngle(float hingeAngle) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setFoldState(boolean folded) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedModes() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public void registerHeadTrackingCallback(ISpatializerHeadTrackingCallback callback) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setParameter(int key, byte[] value) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void getParameter(int key, byte[] value) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public int getOutput() throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISpatializer {
        static final int TRANSACTION_getActualHeadTrackingMode = 8;
        static final int TRANSACTION_getLevel = 4;
        static final int TRANSACTION_getOutput = 20;
        static final int TRANSACTION_getParameter = 19;
        static final int TRANSACTION_getSupportedHeadTrackingModes = 6;
        static final int TRANSACTION_getSupportedLevels = 2;
        static final int TRANSACTION_getSupportedModes = 16;
        static final int TRANSACTION_isHeadTrackingSupported = 5;
        static final int TRANSACTION_recenterHeadTracker = 9;
        static final int TRANSACTION_registerHeadTrackingCallback = 17;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_setDesiredHeadTrackingMode = 7;
        static final int TRANSACTION_setDisplayOrientation = 13;
        static final int TRANSACTION_setFoldState = 15;
        static final int TRANSACTION_setGlobalTransform = 10;
        static final int TRANSACTION_setHeadSensor = 11;
        static final int TRANSACTION_setHingeAngle = 14;
        static final int TRANSACTION_setLevel = 3;
        static final int TRANSACTION_setParameter = 18;
        static final int TRANSACTION_setScreenSensor = 12;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpatializer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISpatializer)) {
                return (ISpatializer) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(descriptor);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            release();
                            reply.writeNoException();
                            return true;
                        case 2:
                            byte[] _result = getSupportedLevels();
                            reply.writeNoException();
                            reply.writeByteArray(_result);
                            return true;
                        case 3:
                            byte _arg0 = data.readByte();
                            data.enforceNoDataAvail();
                            setLevel(_arg0);
                            reply.writeNoException();
                            return true;
                        case 4:
                            byte _result2 = getLevel();
                            reply.writeNoException();
                            reply.writeByte(_result2);
                            return true;
                        case 5:
                            boolean _result3 = isHeadTrackingSupported();
                            reply.writeNoException();
                            reply.writeBoolean(_result3);
                            return true;
                        case 6:
                            byte[] _result4 = getSupportedHeadTrackingModes();
                            reply.writeNoException();
                            reply.writeByteArray(_result4);
                            return true;
                        case 7:
                            byte _arg02 = data.readByte();
                            data.enforceNoDataAvail();
                            setDesiredHeadTrackingMode(_arg02);
                            reply.writeNoException();
                            return true;
                        case 8:
                            byte _result5 = getActualHeadTrackingMode();
                            reply.writeNoException();
                            reply.writeByte(_result5);
                            return true;
                        case 9:
                            recenterHeadTracker();
                            reply.writeNoException();
                            return true;
                        case 10:
                            float[] _arg03 = data.createFloatArray();
                            data.enforceNoDataAvail();
                            setGlobalTransform(_arg03);
                            reply.writeNoException();
                            return true;
                        case 11:
                            int _arg04 = data.readInt();
                            data.enforceNoDataAvail();
                            setHeadSensor(_arg04);
                            reply.writeNoException();
                            return true;
                        case 12:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            setScreenSensor(_arg05);
                            reply.writeNoException();
                            return true;
                        case 13:
                            float _arg06 = data.readFloat();
                            data.enforceNoDataAvail();
                            setDisplayOrientation(_arg06);
                            reply.writeNoException();
                            return true;
                        case 14:
                            float _arg07 = data.readFloat();
                            data.enforceNoDataAvail();
                            setHingeAngle(_arg07);
                            reply.writeNoException();
                            return true;
                        case 15:
                            boolean _arg08 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setFoldState(_arg08);
                            reply.writeNoException();
                            return true;
                        case 16:
                            byte[] _result6 = getSupportedModes();
                            reply.writeNoException();
                            reply.writeByteArray(_result6);
                            return true;
                        case 17:
                            ISpatializerHeadTrackingCallback _arg09 = ISpatializerHeadTrackingCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            registerHeadTrackingCallback(_arg09);
                            reply.writeNoException();
                            return true;
                        case 18:
                            int _arg010 = data.readInt();
                            byte[] _arg1 = data.createByteArray();
                            data.enforceNoDataAvail();
                            setParameter(_arg010, _arg1);
                            reply.writeNoException();
                            return true;
                        case 19:
                            int _arg011 = data.readInt();
                            byte[] _arg12 = data.createByteArray();
                            data.enforceNoDataAvail();
                            getParameter(_arg011, _arg12);
                            reply.writeNoException();
                            reply.writeByteArray(_arg12);
                            return true;
                        case 20:
                            int _result7 = getOutput();
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISpatializer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.media.ISpatializer
            public void release() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedLevels() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setLevel(byte level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(level);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    byte _result = _reply.readByte();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public boolean isHeadTrackingSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedHeadTrackingModes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDesiredHeadTrackingMode(byte mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(mode);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getActualHeadTrackingMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    byte _result = _reply.readByte();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void recenterHeadTracker() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setGlobalTransform(float[] screenToStage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeFloatArray(screenToStage);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHeadSensor(int sensorHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(sensorHandle);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setScreenSensor(int sensorHandle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(sensorHandle);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDisplayOrientation(float physicalToLogicalAngle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeFloat(physicalToLogicalAngle);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHingeAngle(float hingeAngle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeFloat(hingeAngle);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setFoldState(boolean folded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeBoolean(folded);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedModes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void registerHeadTrackingCallback(ISpatializerHeadTrackingCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setParameter(int key, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeByteArray(value);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void getParameter(int key, byte[] value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(key);
                    _data.writeByteArray(value);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    _reply.readByteArray(value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public int getOutput() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
