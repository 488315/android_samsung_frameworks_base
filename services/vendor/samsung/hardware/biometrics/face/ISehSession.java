package vendor.samsung.hardware.biometrics.face;

import android.hardware.biometrics.common.ICancellationSignal;
import android.hardware.common.Ashmem;
import android.hardware.common.NativeHandle;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISehSession extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$biometrics$face$ISehSession".replace('$', '.');

    public class Default implements ISehSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public ICancellationSignal authenticateExtension(long j, int i, byte[] bArr) {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public ICancellationSignal authenticateForIssuance(long j, int i, byte[] bArr, boolean z, NativeHandle nativeHandle) {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public void close() {
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public String getTaInfo() {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public byte[] getWrappedData() {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public Ashmem getWrappedDataFromMemory() {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public boolean isTAUnloaded() {
            return false;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public int loadTA() {
            return 0;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public int pause() {
            return 0;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public int resume() {
            return 0;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public int setFaceTag(int i, byte[] bArr) {
            return 0;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public int setRotation(int i) {
            return 0;
        }

        @Override // vendor.samsung.hardware.biometrics.face.ISehSession
        public int unloadTA() {
            return 0;
        }
    }

    ICancellationSignal authenticateExtension(long j, int i, byte[] bArr);

    ICancellationSignal authenticateForIssuance(long j, int i, byte[] bArr, boolean z, NativeHandle nativeHandle);

    void close();

    SehFaceTag getFaceTag(int i);

    int[] getFaceTagList();

    String getInterfaceHash();

    int getInterfaceVersion();

    String getTaInfo();

    byte[] getWrappedData();

    Ashmem getWrappedDataFromMemory();

    boolean isTAUnloaded();

    int loadTA();

    int pause();

    int resume();

    int setFaceTag(int i, byte[] bArr);

    int setRotation(int i);

    int unloadTA();

    public abstract class Stub extends Binder implements ISehSession {
        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "authenticateExtension";
                case 2:
                    return "getWrappedData";
                case 3:
                    return "loadTA";
                case 4:
                    return "unloadTA";
                case 5:
                    return "isTAUnloaded";
                case 6:
                    return "pause";
                case 7:
                    return "resume";
                case 8:
                    return "getFaceTagList";
                case 9:
                    return "getFaceTag";
                case 10:
                    return "setFaceTag";
                case 11:
                    return "setRotation";
                case 12:
                    return "close";
                case 13:
                    return "authenticateForIssuance";
                case 14:
                    return "getWrappedDataFromMemory";
                case 15:
                    return "getTaInfo";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehSession.DESCRIPTOR);
        }

        public static ISehSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehSession)) {
                return (ISehSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehSession.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    switch (i) {
                        case 1:
                            long readLong = parcel.readLong();
                            int readInt = parcel.readInt();
                            byte[] createByteArray = parcel.createByteArray();
                            parcel.enforceNoDataAvail();
                            ICancellationSignal authenticateExtension = authenticateExtension(readLong, readInt, createByteArray);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(authenticateExtension);
                            return true;
                        case 2:
                            byte[] wrappedData = getWrappedData();
                            parcel2.writeNoException();
                            parcel2.writeByteArray(wrappedData);
                            return true;
                        case 3:
                            int loadTA = loadTA();
                            parcel2.writeNoException();
                            parcel2.writeInt(loadTA);
                            return true;
                        case 4:
                            int unloadTA = unloadTA();
                            parcel2.writeNoException();
                            parcel2.writeInt(unloadTA);
                            return true;
                        case 5:
                            boolean isTAUnloaded = isTAUnloaded();
                            parcel2.writeNoException();
                            parcel2.writeBoolean(isTAUnloaded);
                            return true;
                        case 6:
                            int pause = pause();
                            parcel2.writeNoException();
                            parcel2.writeInt(pause);
                            return true;
                        case 7:
                            int resume = resume();
                            parcel2.writeNoException();
                            parcel2.writeInt(resume);
                            return true;
                        case 8:
                            int[] faceTagList = getFaceTagList();
                            parcel2.writeNoException();
                            parcel2.writeIntArray(faceTagList);
                            return true;
                        case 9:
                            int readInt2 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            SehFaceTag faceTag = getFaceTag(readInt2);
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(faceTag, 1);
                            return true;
                        case 10:
                            int readInt3 = parcel.readInt();
                            byte[] createByteArray2 = parcel.createByteArray();
                            parcel.enforceNoDataAvail();
                            int faceTag2 = setFaceTag(readInt3, createByteArray2);
                            parcel2.writeNoException();
                            parcel2.writeInt(faceTag2);
                            return true;
                        case 11:
                            int readInt4 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            int rotation = setRotation(readInt4);
                            parcel2.writeNoException();
                            parcel2.writeInt(rotation);
                            return true;
                        case 12:
                            close();
                            parcel2.writeNoException();
                            return true;
                        case 13:
                            long readLong2 = parcel.readLong();
                            int readInt5 = parcel.readInt();
                            byte[] createByteArray3 = parcel.createByteArray();
                            boolean readBoolean = parcel.readBoolean();
                            NativeHandle nativeHandle = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                            parcel.enforceNoDataAvail();
                            ICancellationSignal authenticateForIssuance = authenticateForIssuance(readLong2, readInt5, createByteArray3, readBoolean, nativeHandle);
                            parcel2.writeNoException();
                            parcel2.writeStrongInterface(authenticateForIssuance);
                            return true;
                        case 14:
                            Ashmem wrappedDataFromMemory = getWrappedDataFromMemory();
                            parcel2.writeNoException();
                            parcel2.writeTypedObject(wrappedDataFromMemory, 1);
                            return true;
                        case 15:
                            String taInfo = getTaInfo();
                            parcel2.writeNoException();
                            parcel2.writeString(taInfo);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements ISehSession {
            public IBinder mRemote;
            public int mCachedVersion = -1;
            public String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public ICancellationSignal authenticateExtension(long j, int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method authenticateExtension is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public byte[] getWrappedData() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getWrappedData is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public int loadTA() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method loadTA is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public int unloadTA() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method unloadTA is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public boolean isTAUnloaded() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isTAUnloaded is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public int pause() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method pause is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public int resume() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method resume is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public int setFaceTag(int i, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setFaceTag is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public int setRotation(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setRotation is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public void close() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public ICancellationSignal authenticateForIssuance(long j, int i, byte[] bArr, boolean z, NativeHandle nativeHandle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(nativeHandle, 0);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method authenticateForIssuance is unimplemented.");
                    }
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public Ashmem getWrappedDataFromMemory() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getWrappedDataFromMemory is unimplemented.");
                    }
                    obtain2.readException();
                    return (Ashmem) obtain2.readTypedObject(Ashmem.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.biometrics.face.ISehSession
            public String getTaInfo() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehSession.DESCRIPTOR);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getTaInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
