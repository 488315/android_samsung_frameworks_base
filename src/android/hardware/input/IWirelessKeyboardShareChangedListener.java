package android.hardware.input;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IWirelessKeyboardShareChangedListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.input.IWirelessKeyboardShareChangedListener";

    public static class Default implements IWirelessKeyboardShareChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.input.IWirelessKeyboardShareChangedListener
        public void onWirelessKeyboardShareChanged(long j, int i, String str) throws RemoteException {
        }
    }

    void onWirelessKeyboardShareChanged(long j, int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IWirelessKeyboardShareChangedListener {
        static final int TRANSACTION_onWirelessKeyboardShareChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWirelessKeyboardShareChangedListener.DESCRIPTOR);
        }

        public static IWirelessKeyboardShareChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWirelessKeyboardShareChangedListener)) {
                return (IWirelessKeyboardShareChangedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onWirelessKeyboardShareChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onWirelessKeyboardShareChanged(j, i3, string);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWirelessKeyboardShareChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWirelessKeyboardShareChangedListener.DESCRIPTOR;
            }

            @Override // android.hardware.input.IWirelessKeyboardShareChangedListener
            public void onWirelessKeyboardShareChanged(long j, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWirelessKeyboardShareChangedListener.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
