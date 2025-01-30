package vendor.samsung.hardware.tlc.ddar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface ISehDdar extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$tlc$ddar$ISehDdar".replace('$', '.');

    public class Default implements ISehDdar {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    int authenticate(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    int changePassword(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    String getInterfaceHash();

    int getInterfaceVersion();

    int initializeDdar(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    int initiate(int i, String str, String str2, String str3, int i2, int i3);

    int setPassword(int i, byte[] bArr, byte[] bArr2, byte[] bArr3);

    int terminate(int i, String str);

    public abstract class Stub extends Binder implements ISehDdar {
        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initiate";
                case 2:
                    return "terminate";
                case 3:
                    return "initializeDdar";
                case 4:
                    return "setPassword";
                case 5:
                    return "changePassword";
                case 6:
                    return "authenticate";
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
            attachInterface(this, ISehDdar.DESCRIPTOR);
        }

        public static ISehDdar asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehDdar.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehDdar)) {
                return (ISehDdar) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            byte[] bArr;
            String str = ISehDdar.DESCRIPTOR;
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
                            int readInt = parcel.readInt();
                            String readString = parcel.readString();
                            String readString2 = parcel.readString();
                            String readString3 = parcel.readString();
                            int readInt2 = parcel.readInt();
                            int readInt3 = parcel.readInt();
                            parcel.enforceNoDataAvail();
                            int initiate = initiate(readInt, readString, readString2, readString3, readInt2, readInt3);
                            parcel2.writeNoException();
                            parcel2.writeInt(initiate);
                            return true;
                        case 2:
                            int readInt4 = parcel.readInt();
                            String readString4 = parcel.readString();
                            parcel.enforceNoDataAvail();
                            int terminate = terminate(readInt4, readString4);
                            parcel2.writeNoException();
                            parcel2.writeInt(terminate);
                            return true;
                        case 3:
                            int readInt5 = parcel.readInt();
                            byte[] createByteArray = parcel.createByteArray();
                            byte[] createByteArray2 = parcel.createByteArray();
                            int readInt6 = parcel.readInt();
                            bArr = readInt6 >= 0 ? new byte[readInt6] : null;
                            parcel.enforceNoDataAvail();
                            int initializeDdar = initializeDdar(readInt5, createByteArray, createByteArray2, bArr);
                            parcel2.writeNoException();
                            parcel2.writeInt(initializeDdar);
                            parcel2.writeByteArray(bArr);
                            return true;
                        case 4:
                            int readInt7 = parcel.readInt();
                            byte[] createByteArray3 = parcel.createByteArray();
                            byte[] createByteArray4 = parcel.createByteArray();
                            int readInt8 = parcel.readInt();
                            bArr = readInt8 >= 0 ? new byte[readInt8] : null;
                            parcel.enforceNoDataAvail();
                            int password = setPassword(readInt7, createByteArray3, createByteArray4, bArr);
                            parcel2.writeNoException();
                            parcel2.writeInt(password);
                            parcel2.writeByteArray(bArr);
                            return true;
                        case 5:
                            int readInt9 = parcel.readInt();
                            byte[] createByteArray5 = parcel.createByteArray();
                            byte[] createByteArray6 = parcel.createByteArray();
                            int readInt10 = parcel.readInt();
                            bArr = readInt10 >= 0 ? new byte[readInt10] : null;
                            parcel.enforceNoDataAvail();
                            int changePassword = changePassword(readInt9, createByteArray5, createByteArray6, bArr);
                            parcel2.writeNoException();
                            parcel2.writeInt(changePassword);
                            parcel2.writeByteArray(bArr);
                            return true;
                        case 6:
                            int readInt11 = parcel.readInt();
                            byte[] createByteArray7 = parcel.createByteArray();
                            byte[] createByteArray8 = parcel.createByteArray();
                            int readInt12 = parcel.readInt();
                            bArr = readInt12 >= 0 ? new byte[readInt12] : null;
                            parcel.enforceNoDataAvail();
                            int authenticate = authenticate(readInt11, createByteArray7, createByteArray8, bArr);
                            parcel2.writeNoException();
                            parcel2.writeInt(authenticate);
                            parcel2.writeByteArray(bArr);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements ISehDdar {
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
        }
    }
}
