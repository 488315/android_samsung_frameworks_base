package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IAudioDeviceVolumeDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IAudioDeviceVolumeDispatcher";

    public static class Default implements IAudioDeviceVolumeDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeAdjusted(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeChanged(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo) throws RemoteException {
        }
    }

    void dispatchDeviceVolumeAdjusted(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, int i, int i2) throws RemoteException;

    void dispatchDeviceVolumeChanged(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioDeviceVolumeDispatcher {
        static final int TRANSACTION_dispatchDeviceVolumeAdjusted = 2;
        static final int TRANSACTION_dispatchDeviceVolumeChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IAudioDeviceVolumeDispatcher.DESCRIPTOR);
        }

        public static IAudioDeviceVolumeDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAudioDeviceVolumeDispatcher.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioDeviceVolumeDispatcher)) {
                return (IAudioDeviceVolumeDispatcher) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "dispatchDeviceVolumeChanged";
            }
            if (i != 2) {
                return null;
            }
            return "dispatchDeviceVolumeAdjusted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioDeviceVolumeDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioDeviceVolumeDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                VolumeInfo volumeInfo = (VolumeInfo) parcel.readTypedObject(VolumeInfo.CREATOR);
                parcel.enforceNoDataAvail();
                dispatchDeviceVolumeChanged(audioDeviceAttributes, volumeInfo);
            } else if (i == 2) {
                AudioDeviceAttributes audioDeviceAttributes2 = (AudioDeviceAttributes) parcel.readTypedObject(AudioDeviceAttributes.CREATOR);
                VolumeInfo volumeInfo2 = (VolumeInfo) parcel.readTypedObject(VolumeInfo.CREATOR);
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispatchDeviceVolumeAdjusted(audioDeviceAttributes2, volumeInfo2, i3, i4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAudioDeviceVolumeDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioDeviceVolumeDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IAudioDeviceVolumeDispatcher
            public void dispatchDeviceVolumeChanged(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioDeviceVolumeDispatcher.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeTypedObject(volumeInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioDeviceVolumeDispatcher
            public void dispatchDeviceVolumeAdjusted(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioDeviceVolumeDispatcher.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceAttributes, 0);
                    parcelObtain.writeTypedObject(volumeInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
