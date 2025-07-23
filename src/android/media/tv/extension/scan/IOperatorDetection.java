package android.media.tv.extension.scan;

import android.media.tv.extension.scan.IOperatorDetectionListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IOperatorDetection extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.scan.IOperatorDetection";

    public static class Default implements IOperatorDetection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.scan.IOperatorDetection
        public int setListener(IOperatorDetectionListener iOperatorDetectionListener) throws RemoteException {
            return 0;
        }

        @Override // android.media.tv.extension.scan.IOperatorDetection
        public int setOperatorDetection(Bundle bundle) throws RemoteException {
            return 0;
        }
    }

    int setListener(IOperatorDetectionListener iOperatorDetectionListener) throws RemoteException;

    int setOperatorDetection(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IOperatorDetection {
        static final int TRANSACTION_setListener = 2;
        static final int TRANSACTION_setOperatorDetection = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.scan.IOperatorDetection");
        }

        public static IOperatorDetection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.scan.IOperatorDetection");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOperatorDetection)) {
                return (IOperatorDetection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setOperatorDetection";
            }
            if (i != 2) {
                return null;
            }
            return "setListener";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.scan.IOperatorDetection");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.scan.IOperatorDetection");
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                int operatorDetection = setOperatorDetection(bundle);
                parcel2.writeNoException();
                parcel2.writeInt(operatorDetection);
            } else if (i == 2) {
                IOperatorDetectionListener asInterface = IOperatorDetectionListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int listener = setListener(asInterface);
                parcel2.writeNoException();
                parcel2.writeInt(listener);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOperatorDetection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.scan.IOperatorDetection";
            }

            @Override // android.media.tv.extension.scan.IOperatorDetection
            public int setOperatorDetection(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IOperatorDetection");
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.scan.IOperatorDetection
            public int setListener(IOperatorDetectionListener iOperatorDetectionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.scan.IOperatorDetection");
                    obtain.writeStrongInterface(iOperatorDetectionListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
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
