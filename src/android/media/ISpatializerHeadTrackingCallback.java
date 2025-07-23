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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpatializerHeadTrackingCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpatializerHeadTrackingCallback)) {
                return (ISpatializerHeadTrackingCallback) queryLocalInterface;
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
                byte readByte = parcel.readByte();
                parcel.enforceNoDataAvail();
                onHeadTrackingModeChanged(readByte);
            } else if (i == 2) {
                float[] createFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                onHeadToSoundStagePoseUpdated(createFloatArray);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpatializerHeadTrackingCallback.DESCRIPTOR);
                    obtain.writeByte(b);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadToSoundStagePoseUpdated(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpatializerHeadTrackingCallback.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
