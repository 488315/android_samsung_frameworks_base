package android.os;

/* loaded from: classes3.dex */
public interface IScreenTimeoutPolicyListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IScreenTimeoutPolicyListener";

    public static class Default implements IScreenTimeoutPolicyListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IScreenTimeoutPolicyListener
        public void onScreenTimeoutPolicyChanged(int i) throws RemoteException {
        }
    }

    void onScreenTimeoutPolicyChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IScreenTimeoutPolicyListener {
        static final int TRANSACTION_onScreenTimeoutPolicyChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IScreenTimeoutPolicyListener.DESCRIPTOR);
        }

        public static IScreenTimeoutPolicyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IScreenTimeoutPolicyListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IScreenTimeoutPolicyListener)) {
                return (IScreenTimeoutPolicyListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onScreenTimeoutPolicyChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IScreenTimeoutPolicyListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IScreenTimeoutPolicyListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onScreenTimeoutPolicyChanged(i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IScreenTimeoutPolicyListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScreenTimeoutPolicyListener.DESCRIPTOR;
            }

            @Override // android.os.IScreenTimeoutPolicyListener
            public void onScreenTimeoutPolicyChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IScreenTimeoutPolicyListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
