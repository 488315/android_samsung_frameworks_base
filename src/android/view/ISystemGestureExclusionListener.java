package android.view;

import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISystemGestureExclusionListener extends IInterface {
    public static final String DESCRIPTOR = "android.view.ISystemGestureExclusionListener";

    public static class Default implements ISystemGestureExclusionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.ISystemGestureExclusionListener
        public void onSystemGestureExclusionChanged(int i, Region region, Region region2) throws RemoteException {
        }
    }

    void onSystemGestureExclusionChanged(int i, Region region, Region region2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISystemGestureExclusionListener {
        static final int TRANSACTION_onSystemGestureExclusionChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISystemGestureExclusionListener.DESCRIPTOR);
        }

        public static ISystemGestureExclusionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISystemGestureExclusionListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISystemGestureExclusionListener)) {
                return (ISystemGestureExclusionListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSystemGestureExclusionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISystemGestureExclusionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISystemGestureExclusionListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                parcel.enforceNoDataAvail();
                onSystemGestureExclusionChanged(i3, region, region2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISystemGestureExclusionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemGestureExclusionListener.DESCRIPTOR;
            }

            @Override // android.view.ISystemGestureExclusionListener
            public void onSystemGestureExclusionChanged(int i, Region region, Region region2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemGestureExclusionListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(region, 0);
                    parcelObtain.writeTypedObject(region2, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
