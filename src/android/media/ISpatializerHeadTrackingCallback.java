package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackingCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerHeadTrackingCallback";

    public static class Default implements ISpatializerHeadTrackingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISpatializerHeadTrackingCallback
        public void onHeadToSoundStagePoseUpdated(float[] fArr) throws RemoteException {
        }

        @Override // android.media.ISpatializerHeadTrackingCallback
        public void onHeadTrackingModeChanged(byte b) throws RemoteException {
        }
    }

    void onHeadToSoundStagePoseUpdated(float[] fArr) throws RemoteException;

    void onHeadTrackingModeChanged(byte b) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpatializerHeadTrackingCallback {
        static final int TRANSACTION_onHeadToSoundStagePoseUpdated = 2;
        static final int TRANSACTION_onHeadTrackingModeChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISpatializerHeadTrackingCallback.DESCRIPTOR);
        }

        public static ISpatializerHeadTrackingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpatializerHeadTrackingCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpatializerHeadTrackingCallback)) {
                return (ISpatializerHeadTrackingCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpatializerHeadTrackingCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpatializerHeadTrackingCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte b = parcel.readByte();
                parcel.enforceNoDataAvail();
                onHeadTrackingModeChanged(b);
            } else if (i == 2) {
                float[] fArrCreateFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                onHeadToSoundStagePoseUpdated(fArrCreateFloatArray);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISpatializerHeadTrackingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerHeadTrackingCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadTrackingModeChanged(byte b) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISpatializerHeadTrackingCallback.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadToSoundStagePoseUpdated(float[] fArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISpatializerHeadTrackingCallback.DESCRIPTOR);
                    parcelObtain.writeFloatArray(fArr);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
