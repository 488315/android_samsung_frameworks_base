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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiCecVolumeControlFeatureListener)) {
                return (IHdmiCecVolumeControlFeatureListener) queryLocalInterface;
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
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onHdmiCecVolumeControlFeature(readInt);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IHdmiCecVolumeControlFeatureListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
