package android.os;

/* loaded from: classes3.dex */
public interface IThermalHeadroomListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IThermalHeadroomListener";

    public static class Default implements IThermalHeadroomListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IThermalHeadroomListener
        public void onHeadroomChange(float f, float f2, int i, float[] fArr) throws RemoteException {
        }
    }

    void onHeadroomChange(float f, float f2, int i, float[] fArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IThermalHeadroomListener {
        static final int TRANSACTION_onHeadroomChange = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IThermalHeadroomListener.DESCRIPTOR);
        }

        public static IThermalHeadroomListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IThermalHeadroomListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IThermalHeadroomListener)) {
                return (IThermalHeadroomListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onHeadroomChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IThermalHeadroomListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IThermalHeadroomListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float f = parcel.readFloat();
                float f2 = parcel.readFloat();
                int i3 = parcel.readInt();
                float[] fArrCreateFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                onHeadroomChange(f, f2, i3, fArrCreateFloatArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IThermalHeadroomListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IThermalHeadroomListener.DESCRIPTOR;
            }

            @Override // android.os.IThermalHeadroomListener
            public void onHeadroomChange(float f, float f2, int i, float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IThermalHeadroomListener.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
