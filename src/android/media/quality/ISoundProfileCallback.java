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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISoundProfileCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundProfileCallback)) {
                return (ISoundProfileCallback) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                SoundProfile soundProfile = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onSoundProfileAdded(string, soundProfile);
            } else if (i == 2) {
                String string2 = parcel.readString();
                SoundProfile soundProfile2 = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onSoundProfileUpdated(string2, soundProfile2);
            } else if (i == 3) {
                String string3 = parcel.readString();
                SoundProfile soundProfile3 = (SoundProfile) parcel.readTypedObject(SoundProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onSoundProfileRemoved(string3, soundProfile3);
            } else if (i == 4) {
                String string4 = parcel.readString();
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ParameterCapability.CREATOR);
                parcel.enforceNoDataAvail();
                onParameterCapabilitiesChanged(string4, arrayListCreateTypedArrayList);
            } else if (i == 5) {
                String string5 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(string5, i3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(soundProfile, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileUpdated(String str, SoundProfile soundProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(soundProfile, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onSoundProfileRemoved(String str, SoundProfile soundProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(soundProfile, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.ISoundProfileCallback
            public void onError(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
