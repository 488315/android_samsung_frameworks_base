package android.media.quality;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISoundProfileCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.quality.ISoundProfileCallback";

    public static class Default implements ISoundProfileCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.quality.ISoundProfileCallback
        public void onError(String str, int i) throws RemoteException {
        }

        @Override // android.media.quality.ISoundProfileCallback
        public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException {
        }

        @Override // android.media.quality.ISoundProfileCallback
        public void onSoundProfileAdded(String str, SoundProfile soundProfile) throws RemoteException {
        }

        @Override // android.media.quality.ISoundProfileCallback
        public void onSoundProfileRemoved(String str, SoundProfile soundProfile) throws RemoteException {
        }

        @Override // android.media.quality.ISoundProfileCallback
        public void onSoundProfileUpdated(String str, SoundProfile soundProfile) throws RemoteException {
        }
    }

    void onError(String str, int i) throws RemoteException;

    void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException;

    void onSoundProfileAdded(String str, SoundProfile soundProfile) throws RemoteException;

    void onSoundProfileRemoved(String str, SoundProfile soundProfile) throws RemoteException;

    void onSoundProfileUpdated(String str, SoundProfile soundProfile) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundProfileCallback {
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onParameterCapabilitiesChanged = 4;
        static final int TRANSACTION_onSoundProfileAdded = 1;
        static final int TRANSACTION_onSoundProfileRemoved = 3;
        static final int TRANSACTION_onSoundProfileUpdated = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundProfileCallback.DESCRIPTOR);
        }

        public static ISoundProfileCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISoundProfileCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISoundProfileCallback)) {
                return (ISoundProfileCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundProfileCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundProfileCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                SoundProfile soundProfile = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onSoundProfileAdded(readString, soundProfile);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                SoundProfile soundProfile2 = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onSoundProfileUpdated(readString2, soundProfile2);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                SoundProfile soundProfile3 = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onSoundProfileRemoved(readString3, soundProfile3);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                ArrayList createTypedArrayList = parcel.createTypedArrayList(ParameterCapability.CREATOR);
                parcel.enforceNoDataAvail();
                onParameterCapabilitiesChanged(readString4, createTypedArrayList);
            } else if (i == 5) {
                String readString5 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readString5, readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISoundProfileCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundProfileCallback.DESCRIPTOR;
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileAdded(String str, SoundProfile soundProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(soundProfile, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileUpdated(String str, SoundProfile soundProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(soundProfile, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileRemoved(String str, SoundProfile soundProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(soundProfile, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onError(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
