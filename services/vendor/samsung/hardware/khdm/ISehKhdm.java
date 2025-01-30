package vendor.samsung.hardware.khdm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISehKhdm extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$khdm$ISehKhdm".replace('$', '.');

    public class Default implements ISehKhdm {
        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int applyPolicy(byte[] bArr, SehDeviceInfo sehDeviceInfo, byte[] bArr2) {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int checkKey() {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int deleteKey() {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int executeHdmCmd(int i) {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int getDeviceId(SehDeviceInfo sehDeviceInfo, byte[] bArr) {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int getPolicy(boolean z, byte[] bArr, byte[] bArr2, byte[] bArr3) {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int initiate() {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int setKey(byte[] bArr, boolean z) {
            return 0;
        }

        @Override // vendor.samsung.hardware.khdm.ISehKhdm
        public int terminate() {
            return 0;
        }
    }

    int applyPolicy(byte[] bArr, SehDeviceInfo sehDeviceInfo, byte[] bArr2);

    int checkKey();

    int deleteKey();

    int executeHdmCmd(int i);

    int getDeviceId(SehDeviceInfo sehDeviceInfo, byte[] bArr);

    String getInterfaceHash();

    int getInterfaceVersion();

    int getPolicy(boolean z, byte[] bArr, byte[] bArr2, byte[] bArr3);

    int initiate();

    int setKey(byte[] bArr, boolean z);

    int terminate();

    public abstract class Stub extends Binder implements ISehKhdm {
        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initiate";
                case 2:
                    return "getDeviceId";
                case 3:
                    return "applyPolicy";
                case 4:
                    return "terminate";
                case 5:
                    return "setKey";
                case 6:
                    return "checkKey";
                case 7:
                    return "deleteKey";
                case 8:
                    return "getPolicy";
                case 9:
                    return "executeHdmCmd";
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
            attachInterface(this, ISehKhdm.DESCRIPTOR);
        }

        public static ISehKhdm asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehKhdm.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehKhdm)) {
                return (ISehKhdm) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehKhdm.DESCRIPTOR;
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
                            int initiate = initiate();
                            parcel2.writeNoException();
                            parcel2.writeInt(initiate);
                            return true;
                        case 2:
                            SehDeviceInfo sehDeviceInfo = (SehDeviceInfo) parcel.readTypedObject(SehDeviceInfo.CREATOR);
                            byte[] createByteArray = parcel.createByteArray();
                            parcel.enforceNoDataAvail();
                            int deviceId = getDeviceId(sehDeviceInfo, createByteArray);
                            parcel2.writeNoException();
                            parcel2.writeInt(deviceId);
                            parcel2.writeTypedObject(sehDeviceInfo, 1);
                            parcel2.writeByteArray(createByteArray);
                            return true;
                        case 3:
                            byte[] createByteArray2 = parcel.createByteArray();
                            SehDeviceInfo sehDeviceInfo2 = (SehDeviceInfo) parcel.readTypedObject(SehDeviceInfo.CREATOR);
                            byte[] createByteArray3 = parcel.createByteArray();
                            parcel.enforceNoDataAvail();
                            int applyPolicy = applyPolicy(createByteArray2, sehDeviceInfo2, createByteArray3);
                            parcel2.writeNoException();
                            parcel2.writeInt(applyPolicy);
                            parcel2.writeByteArray(createByteArray2);
                            parcel2.writeTypedObject(sehDeviceInfo2, 1);
                            parcel2.writeByteArray(createByteArray3);
                            return true;
                        case 4:
                            int terminate = terminate();
                            parcel2.writeNoException();
                            parcel2.writeInt(terminate);
                            return true;
                        case 5:
                            byte[] createByteArray4 = parcel.createByteArray();
                            boolean readBoolean = parcel.readBoolean();
                            parcel.enforceNoDataAvail();
                            int key = setKey(createByteArray4, readBoolean);
                            parcel2.writeNoException();
                            parcel2.writeInt(key);
                            parcel2.writeByteArray(createByteArray4);
                            return true;
                        case 6:
                            int checkKey = checkKey();
                            parcel2.writeNoException();
                            parcel2.writeInt(checkKey);
                            return true;
                        case 7:
                            int deleteKey = deleteKey();
                            parcel2.writeNoException();
                            parcel2.writeInt(deleteKey);
                            return true;
                        case 8:
                            boolean readBoolean2 = parcel.readBoolean();
                            byte[] createByteArray5 = parcel.createByteArray();
                            byte[] createByteArray6 = parcel.createByteArray();
                            byte[] createByteArray7 = parcel.createByteArray();
                            parcel.enforceNoDataAvail();
                            int policy = getPolicy(readBoolean2, createByteArray5, createByteArray6, createByteArray7);
                            parcel2.writeNoException();
                            parcel2.writeInt(policy);
                            parcel2.writeByteArray(createByteArray5);
                            parcel2.writeByteArray(createByteArray6);
                            parcel2.writeByteArray(createByteArray7);
                            return true;
                        case 9:
                            int readInt = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            int executeHdmCmd = executeHdmCmd(readInt);
                            parcel2.writeNoException();
                            parcel2.writeInt(executeHdmCmd);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements ISehKhdm {
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

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int initiate() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method initiate is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int getDeviceId(SehDeviceInfo sehDeviceInfo, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    obtain.writeTypedObject(sehDeviceInfo, 0);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getDeviceId is unimplemented.");
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        sehDeviceInfo.readFromParcel(obtain2);
                    }
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int applyPolicy(byte[] bArr, SehDeviceInfo sehDeviceInfo, byte[] bArr2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeTypedObject(sehDeviceInfo, 0);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method applyPolicy is unimplemented.");
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    if (obtain2.readInt() != 0) {
                        sehDeviceInfo.readFromParcel(obtain2);
                    }
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int terminate() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method terminate is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int setKey(byte[] bArr, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setKey is unimplemented.");
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int checkKey() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method checkKey is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int deleteKey() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method deleteKey is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int getPolicy(boolean z, byte[] bArr, byte[] bArr2, byte[] bArr3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPolicy is unimplemented.");
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    obtain2.readByteArray(bArr2);
                    obtain2.readByteArray(bArr3);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.khdm.ISehKhdm
            public int executeHdmCmd(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehKhdm.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method executeHdmCmd is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
