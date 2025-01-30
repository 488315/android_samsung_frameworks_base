package vendor.samsung.hardware.mpos;

import android.hardware.common.NativeHandle;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface ISehMpos extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$mpos$ISehMpos".replace('$', '.');

    public class Default implements ISehMpos {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    int commandRequest(int i, byte[] bArr, byte[] bArr2);

    SehCreateContextResponse createContextWithFd(String str, String str2, int i, int i2, NativeHandle nativeHandle, int i3, int i4);

    int destroyContext(int i);

    String getInterfaceHash();

    int getInterfaceVersion();

    int getSharedMemorySize();

    boolean startSecureTouch(int i);

    public abstract class Stub extends Binder implements ISehMpos {
        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createContextWithFd";
            }
            if (i == 2) {
                return "commandRequest";
            }
            if (i == 3) {
                return "destroyContext";
            }
            if (i == 4) {
                return "startSecureTouch";
            }
            if (i == 5) {
                return "getSharedMemorySize";
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
            attachInterface(this, ISehMpos.DESCRIPTOR);
        }

        public static ISehMpos asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehMpos.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehMpos)) {
                return (ISehMpos) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = ISehMpos.DESCRIPTOR;
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
                        String readString = parcel.readString();
                        String readString2 = parcel.readString();
                        int readInt = parcel.readInt();
                        int readInt2 = parcel.readInt();
                        NativeHandle nativeHandle = (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR);
                        int readInt3 = parcel.readInt();
                        int readInt4 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        SehCreateContextResponse createContextWithFd = createContextWithFd(readString, readString2, readInt, readInt2, nativeHandle, readInt3, readInt4);
                        parcel2.writeNoException();
                        parcel2.writeTypedObject(createContextWithFd, 1);
                        break;
                    } else if (i == 2) {
                        int readInt5 = parcel.readInt();
                        byte[] createByteArray = parcel.createByteArray();
                        byte[] createByteArray2 = parcel.createByteArray();
                        parcel.enforceNoDataAvail();
                        int commandRequest = commandRequest(readInt5, createByteArray, createByteArray2);
                        parcel2.writeNoException();
                        parcel2.writeInt(commandRequest);
                        parcel2.writeByteArray(createByteArray);
                        parcel2.writeByteArray(createByteArray2);
                        break;
                    } else if (i == 3) {
                        int readInt6 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        int destroyContext = destroyContext(readInt6);
                        parcel2.writeNoException();
                        parcel2.writeInt(destroyContext);
                        break;
                    } else if (i == 4) {
                        int readInt7 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        boolean startSecureTouch = startSecureTouch(readInt7);
                        parcel2.writeNoException();
                        parcel2.writeBoolean(startSecureTouch);
                        break;
                    } else if (i == 5) {
                        int sharedMemorySize = getSharedMemorySize();
                        parcel2.writeNoException();
                        parcel2.writeInt(sharedMemorySize);
                        break;
                    } else {
                        break;
                    }
            }
            return true;
        }

        public class Proxy implements ISehMpos {
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
