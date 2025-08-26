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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpatializer.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpatializer)) {
                return (ISpatializer) iInterfaceQueryLocalInterface;
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
                    byte b = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setLevel(b);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    byte level = getLevel();
                    parcel2.writeNoException();
                    parcel2.writeByte(level);
                    return true;
                case 5:
                    boolean zIsHeadTrackingSupported = isHeadTrackingSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHeadTrackingSupported);
                    return true;
                case 6:
                    byte[] supportedHeadTrackingModes = getSupportedHeadTrackingModes();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedHeadTrackingModes);
                    return true;
                case 7:
                    byte b2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setDesiredHeadTrackingMode(b2);
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
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setGlobalTransform(fArrCreateFloatArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setHeadSensor(i3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setScreenSensor(i4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setDisplayOrientation(f);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setHingeAngle(f2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFoldState(z);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    byte[] supportedModes = getSupportedModes();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supportedModes);
                    return true;
                case 17:
                    ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallbackAsInterface = ISpatializerHeadTrackingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHeadTrackingCallback(iSpatializerHeadTrackingCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int i5 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setParameter(i5, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i6 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getParameter(i6, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrCreateByteArray2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedLevels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setLevel(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readByte();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public boolean isHeadTrackingSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedHeadTrackingModes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDesiredHeadTrackingMode(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte getActualHeadTrackingMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readByte();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void recenterHeadTracker() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setGlobalTransform(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHeadSensor(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setScreenSensor(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setDisplayOrientation(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setHingeAngle(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setFoldState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public byte[] getSupportedModes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void registerHeadTrackingCallback(ISpatializerHeadTrackingCallback iSpatializerHeadTrackingCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSpatializerHeadTrackingCallback);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void setParameter(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public void getParameter(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.readByteArray(bArr);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public int getOutput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializer
            public int[] getSpatializedChannelMasks() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpatializer.DESCRIPTOR);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
