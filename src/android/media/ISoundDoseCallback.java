package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundDoseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISoundDoseCallback";

    public static class Default implements ISoundDoseCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISoundDoseCallback
        public void onMomentaryExposure(float f, int i) throws RemoteException {
        }

        @Override // android.media.ISoundDoseCallback
        public void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException {
        }
    }

    void onMomentaryExposure(float f, int i) throws RemoteException;

    void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundDoseCallback {
        static final int TRANSACTION_onMomentaryExposure = 1;
        static final int TRANSACTION_onNewCsdValue = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundDoseCallback.DESCRIPTOR);
        }

        public static ISoundDoseCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISoundDoseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundDoseCallback)) {
                return (ISoundDoseCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundDoseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundDoseCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float readFloat = parcel.readFloat();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onMomentaryExposure(readFloat, readInt);
            } else if (i == 2) {
                float readFloat2 = parcel.readFloat();
                SoundDoseRecord[] soundDoseRecordArr = (SoundDoseRecord[]) parcel.createTypedArray(SoundDoseRecord.CREATOR);
                parcel.enforceNoDataAvail();
                onNewCsdValue(readFloat2, soundDoseRecordArr);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundDoseCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundDoseCallback.DESCRIPTOR;
            }

            @Override // android.media.ISoundDoseCallback
            public void onMomentaryExposure(float f, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundDoseCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.ISoundDoseCallback
            public void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundDoseCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeTypedArray(soundDoseRecordArr, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
