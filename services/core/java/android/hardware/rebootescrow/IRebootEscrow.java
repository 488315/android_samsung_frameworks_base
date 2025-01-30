package android.hardware.rebootescrow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRebootEscrow extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$rebootescrow$IRebootEscrow".replace('$', '.');

    public class Default implements IRebootEscrow {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.rebootescrow.IRebootEscrow
        public byte[] retrieveKey() {
            return null;
        }

        @Override // android.hardware.rebootescrow.IRebootEscrow
        public void storeKey(byte[] bArr) {
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    byte[] retrieveKey();

    void storeKey(byte[] bArr);

    public abstract class Stub extends Binder implements IRebootEscrow {
        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "storeKey";
            }
            if (i == 2) {
                return "retrieveKey";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
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
            attachInterface(this, IRebootEscrow.DESCRIPTOR);
        }

        public static IRebootEscrow asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRebootEscrow.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRebootEscrow)) {
                return (IRebootEscrow) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IRebootEscrow.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case 16777214:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    break;
                case 16777215:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    break;
                case 1598968902:
                    parcel2.writeString(str);
                    break;
                default:
                    if (i == 1) {
                        byte[] createByteArray = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        storeKey(createByteArray);
                        parcel2.writeNoException();
                        break;
                    } else if (i == 2) {
                        byte[] retrieveKey = retrieveKey();
                        parcel2.writeNoException();
                        parcel2.writeByteArray(retrieveKey);
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements IRebootEscrow {
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

            @Override // android.hardware.rebootescrow.IRebootEscrow
            public void storeKey(byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRebootEscrow.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method storeKey is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.rebootescrow.IRebootEscrow
            public byte[] retrieveKey() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRebootEscrow.DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method retrieveKey is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
