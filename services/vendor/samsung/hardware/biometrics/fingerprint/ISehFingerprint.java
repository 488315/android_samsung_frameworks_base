package vendor.samsung.hardware.biometrics.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISehFingerprint extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$biometrics$fingerprint$ISehFingerprint".replace('$', '.');

    public class Default implements ISehFingerprint {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
        public SehResult sehRequest(int i, int i2, int i3, byte[] bArr) {
            return null;
        }
    }

    String getInterfaceHash();

    int getInterfaceVersion();

    SehResult sehRequest(int i, int i2, int i3, byte[] bArr);

    public abstract class Stub extends Binder implements ISehFingerprint {
        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "sehRequest";
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

        public IBinder asBinder() {
            return this;
        }

        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, ISehFingerprint.DESCRIPTOR);
        }

        public static ISehFingerprint asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehFingerprint.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehFingerprint)) {
                return (ISehFingerprint) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehFingerprint.DESCRIPTOR;
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
                    if (i == 1) {
                        int readInt = parcel.readInt();
                        int readInt2 = parcel.readInt();
                        int readInt3 = parcel.readInt();
                        byte[] createByteArray = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        SehResult sehRequest = sehRequest(readInt, readInt2, readInt3, createByteArray);
                        parcel2.writeNoException();
                        parcel2.writeTypedObject(sehRequest, 1);
                        return true;
                    }
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        public class Proxy implements ISehFingerprint {
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

            @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
            public SehResult sehRequest(int i, int i2, int i3, byte[] bArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISehFingerprint.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method sehRequest is unimplemented.");
                    }
                    obtain2.readException();
                    return (SehResult) obtain2.readTypedObject(SehResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
