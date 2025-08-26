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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISoundDoseCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundDoseCallback)) {
                return (ISoundDoseCallback) iInterfaceQueryLocalInterface;
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
                float f = parcel.readFloat();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onMomentaryExposure(f, i3);
            } else if (i == 2) {
                float f2 = parcel.readFloat();
                SoundDoseRecord[] soundDoseRecordArr = (SoundDoseRecord[]) parcel.createTypedArray(SoundDoseRecord.CREATOR);
                parcel.enforceNoDataAvail();
                onNewCsdValue(f2, soundDoseRecordArr);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDoseCallback.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDoseCallback
            public void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDoseCallback.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeTypedArray(soundDoseRecordArr, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
