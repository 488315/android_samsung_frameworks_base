package android.hardware.usb;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IUsbSerialReader extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.usb.IUsbSerialReader";

    public static class Default implements IUsbSerialReader {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.usb.IUsbSerialReader
        public String getSerial(String str) throws RemoteException {
            return null;
        }
    }

    String getSerial(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IUsbSerialReader {
        static final int TRANSACTION_getSerial = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUsbSerialReader.DESCRIPTOR);
        }

        public static IUsbSerialReader asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUsbSerialReader.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUsbSerialReader)) {
                return (IUsbSerialReader) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getSerial";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUsbSerialReader.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUsbSerialReader.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                String serial = getSerial(readString);
                parcel2.writeNoException();
                parcel2.writeString(serial);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUsbSerialReader {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUsbSerialReader.DESCRIPTOR;
            }

            @Override // android.hardware.usb.IUsbSerialReader
            public String getSerial(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUsbSerialReader.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
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
