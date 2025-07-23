package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadToSoundStagePoseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISpatializerHeadToSoundStagePoseCallback";

    public static class Default implements ISpatializerHeadToSoundStagePoseCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISpatializerHeadToSoundStagePoseCallback
        public void dispatchPoseChanged(float[] fArr) throws RemoteException {
        }
    }

    void dispatchPoseChanged(float[] fArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpatializerHeadToSoundStagePoseCallback {
        static final int TRANSACTION_dispatchPoseChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
        }

        public static ISpatializerHeadToSoundStagePoseCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpatializerHeadToSoundStagePoseCallback)) {
                return (ISpatializerHeadToSoundStagePoseCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchPoseChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float[] createFloatArray = parcel.createFloatArray();
                parcel.enforceNoDataAvail();
                dispatchPoseChanged(createFloatArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISpatializerHeadToSoundStagePoseCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadToSoundStagePoseCallback
            public void dispatchPoseChanged(float[] fArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISpatializerHeadToSoundStagePoseCallback.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
