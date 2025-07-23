package android.media.tv.extension.scan;

import android.media.tv.extension.scan.IScanListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScanInterface extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IScanInterface";

    public static class Default implements IScanInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanInterface
        public IBinder createSession(int i, String str, String str2, IScanListener iScanListener, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.scan.IScanInterface
        public Bundle getParameters(int i, String str, String str2, Bundle bundle) throws RemoteException {
            return null;
        }
    }

    IBinder createSession(int i, String str, String str2, IScanListener iScanListener, Bundle bundle) throws RemoteException;

    Bundle getParameters(int i, String str, String str2, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IScanInterface {
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_getParameters = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IScanInterface");
        }

        public static IScanInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IScanInterface");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScanInterface)) {
                return (IScanInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createSession";
            }
            if (i != 2) {
                return null;
            }
            return "getParameters";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.IScanInterface");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IScanInterface");
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                IScanListener asInterface = IScanListener.Stub.asInterface(parcel.readStrongBinder());
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                IBinder createSession = createSession(readInt, readString, readString2, asInterface, bundle);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(createSession);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle parameters = getParameters(readInt2, readString3, readString4, bundle2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(parameters, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScanInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IScanInterface";
            }

            @Override // android.media.tv.extension.scan.IScanInterface
            public IBinder createSession(int i, String str, String str2, IScanListener iScanListener, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanInterface");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iScanListener);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IScanInterface
            public Bundle getParameters(int i, String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IScanInterface");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
