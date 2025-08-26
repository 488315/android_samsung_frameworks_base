package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiCecVolumeControlFeatureListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener";

    public static class Default implements IHdmiCecVolumeControlFeatureListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
        public void onHdmiCecVolumeControlFeature(int i) throws RemoteException {
        }
    }

    void onHdmiCecVolumeControlFeature(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiCecVolumeControlFeatureListener {
        static final int TRANSACTION_onHdmiCecVolumeControlFeature = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
        }

        public static IHdmiCecVolumeControlFeatureListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHdmiCecVolumeControlFeatureListener)) {
                return (IHdmiCecVolumeControlFeatureListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onHdmiCecVolumeControlFeature";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onHdmiCecVolumeControlFeature(i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IHdmiCecVolumeControlFeatureListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHdmiCecVolumeControlFeatureListener.DESCRIPTOR;
            }

            @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
            public void onHdmiCecVolumeControlFeature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
