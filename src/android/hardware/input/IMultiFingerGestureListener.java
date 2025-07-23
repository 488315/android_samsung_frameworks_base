package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMultiFingerGestureListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IMultiFingerGestureListener";

    public static class Default implements IMultiFingerGestureListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IMultiFingerGestureListener
        public void onMultiFingerGesture(int i, int i2) throws RemoteException {
        }
    }

    void onMultiFingerGesture(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMultiFingerGestureListener {
        static final int TRANSACTION_onMultiFingerGesture = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IMultiFingerGestureListener.DESCRIPTOR);
        }

        public static IMultiFingerGestureListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMultiFingerGestureListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMultiFingerGestureListener)) {
                return (IMultiFingerGestureListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onMultiFingerGesture";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMultiFingerGestureListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMultiFingerGestureListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onMultiFingerGesture(readInt, readInt2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMultiFingerGestureListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMultiFingerGestureListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IMultiFingerGestureListener
            public void onMultiFingerGesture(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMultiFingerGestureListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
