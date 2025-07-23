package android.media;

import android.media.ISpatializerHeadTrackingCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializer extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializer";

    public static class Default implements ISpatializer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISpatializer
        public byte getActualHeadTrackingMode() throws RemoteException {
            return (byte) 0;
        }

        @Override // android.media.ISpatializer
        public byte getLevel() throws RemoteException {
            return (byte) 0;
        }

        @Override // android.media.ISpatializer
        public int getOutput() throws RemoteException {
            return 0;
        }

        @Override // android.media.ISpatializer
        public void getParameter(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public int[] getSpatializedChannelMasks() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedHeadTrackingModes() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedLevels() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public byte[] getSupportedModes() throws RemoteException {
            return null;
        }

        @Override // android.media.ISpatializer
        public boolean isHeadTrackingSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.ISpatializer
        public void recenterHeadTracker() throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void registerHeadTrackingCallback(ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void release() throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setDesiredHeadTrackingMode(byte b) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setDisplayOrientation(float f) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setFoldState(boolean z) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setGlobalTransform(float[] fArr) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setHeadSensor(int i) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setHingeAngle(float f) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setLevel(byte b) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setParameter(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.ISpatializer
        public void setScreenSensor(int i) throws RemoteException {
        }
    }

    byte getActualHeadTrackingMode() throws RemoteException;

    byte getLevel() throws RemoteException;

    int getOutput() throws RemoteException;

    void getParameter(int i, byte[] bArr) throws RemoteException;

    int[] getSpatializedChannelMasks() throws RemoteException;

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

    public static abstract class Stub extends Binder implements ISpatializer {
        static final int TRANSACTION_getActualHeadTrackingMode = 8;
        static final int TRANSACTION_getLevel = 4;
        static final int TRANSACTION_getOutput = 20;
        static final int TRANSACTION_getParameter = 19;
        static final int TRANSACTION_getSpatializedChannelMasks = 21;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISpatializer.DESCRIPTOR);
        }

        public static ISpatializer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpatializer.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpatializer)) {
                return (ISpatializer) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpatializer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpatializer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    release();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    byte[] supportedLevels = getSupportedLevels();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedLevels);
                    return true;
                case 3:
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setLevel(readByte);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    byte level = getLevel();
                    parcel2.writeNoException();
                    parcel2.writeByte(level);
                    return true;
                case 5:
                    boolean isHeadTrackingSupported = isHeadTrackingSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadTrackingSupported);
                    return true;
                case 6:
                    byte[] supportedHeadTrackingModes = getSupportedHeadTrackingModes();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedHeadTrackingModes);
                    return true;
                case 7:
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setDesiredHeadTrackingMode(readByte2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    byte actualHeadTrackingMode = getActualHeadTrackingMode();
                    parcel2.writeNoException();
                    parcel2.writeByte(actualHeadTrackingMode);
                    return true;
                case 9:
                    recenterHeadTracker();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setGlobalTransform(createFloatArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHeadSensor(readInt);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenSensor(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setDisplayOrientation(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setHingeAngle(readFloat2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFoldState(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    byte[] supportedModes = getSupportedModes();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedModes);
                    return true;
                case 17:
                    ISpatializerHeadTrackingCallback asInterface = ISpatializerHeadTrackingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHeadTrackingCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setParameter(readInt3, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getParameter(readInt4, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(createByteArray2);
                    return true;
                case 20:
                    int output = getOutput();
                    parcel2.writeNoException();
                    parcel2.writeInt(output);
                    return true;
                case 21:
                    int[] spatializedChannelMasks = getSpatializedChannelMasks();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(spatializedChannelMasks);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISpatializer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializer.DESCRIPTOR;
            }

            @Override // android.media.ISpatializer
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedLevels() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setLevel(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public boolean isHeadTrackingSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedHeadTrackingModes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDesiredHeadTrackingMode(byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getActualHeadTrackingMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void recenterHeadTracker() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setGlobalTransform(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHeadSensor(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setScreenSensor(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDisplayOrientation(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHingeAngle(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setFoldState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedModes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void registerHeadTrackingCallback(ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadTrackingCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setParameter(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void getParameter(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readByteArray(bArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public int getOutput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public int[] getSpatializedChannelMasks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
