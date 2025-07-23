package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.PointerIcon;

/* loaded from: classes2.dex */
public interface IPointerIconChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IPointerIconChangedListener";

    public static class Default implements IPointerIconChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IPointerIconChangedListener
        public void onPointerIconChanged(int i, PointerIcon pointerIcon) throws RemoteException {
        }
    }

    void onPointerIconChanged(int i, PointerIcon pointerIcon) throws RemoteException;

    public static abstract class Stub extends Binder implements IPointerIconChangedListener {
        static final int TRANSACTION_onPointerIconChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPointerIconChangedListener.DESCRIPTOR);
        }

        public static IPointerIconChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPointerIconChangedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPointerIconChangedListener)) {
                return (IPointerIconChangedListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onPointerIconChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPointerIconChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPointerIconChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                PointerIcon pointerIcon = (PointerIcon) parcel.readTypedObject(PointerIcon.CREATOR);
                parcel.enforceNoDataAvail();
                onPointerIconChanged(readInt, pointerIcon);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPointerIconChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPointerIconChangedListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IPointerIconChangedListener
            public void onPointerIconChanged(int i, PointerIcon pointerIcon) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPointerIconChangedListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointerIcon, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
