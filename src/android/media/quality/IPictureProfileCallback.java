package android.media.quality;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IPictureProfileCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.quality.IPictureProfileCallback";

    public static class Default implements IPictureProfileCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.quality.IPictureProfileCallback
        public void onError(String str, int i) throws RemoteException {
        }

        @Override // android.media.quality.IPictureProfileCallback
        public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException {
        }

        @Override // android.media.quality.IPictureProfileCallback
        public void onPictureProfileAdded(String str, PictureProfile pictureProfile) throws RemoteException {
        }

        @Override // android.media.quality.IPictureProfileCallback
        public void onPictureProfileRemoved(String str, PictureProfile pictureProfile) throws RemoteException {
        }

        @Override // android.media.quality.IPictureProfileCallback
        public void onPictureProfileUpdated(String str, PictureProfile pictureProfile) throws RemoteException {
        }
    }

    void onError(String str, int i) throws RemoteException;

    void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException;

    void onPictureProfileAdded(String str, PictureProfile pictureProfile) throws RemoteException;

    void onPictureProfileRemoved(String str, PictureProfile pictureProfile) throws RemoteException;

    void onPictureProfileUpdated(String str, PictureProfile pictureProfile) throws RemoteException;

    public static abstract class Stub extends Binder implements IPictureProfileCallback {
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onParameterCapabilitiesChanged = 4;
        static final int TRANSACTION_onPictureProfileAdded = 1;
        static final int TRANSACTION_onPictureProfileRemoved = 3;
        static final int TRANSACTION_onPictureProfileUpdated = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IPictureProfileCallback.DESCRIPTOR);
        }

        public static IPictureProfileCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPictureProfileCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPictureProfileCallback)) {
                return (IPictureProfileCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPictureProfileCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPictureProfileCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                PictureProfile pictureProfile = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onPictureProfileAdded(readString, pictureProfile);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                PictureProfile pictureProfile2 = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onPictureProfileUpdated(readString2, pictureProfile2);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                PictureProfile pictureProfile3 = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onPictureProfileRemoved(readString3, pictureProfile3);
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

        private static class Proxy implements IPictureProfileCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPictureProfileCallback.DESCRIPTOR;
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileAdded(String str, PictureProfile pictureProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pictureProfile, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileUpdated(String str, PictureProfile pictureProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pictureProfile, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileRemoved(String str, PictureProfile pictureProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(pictureProfile, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onError(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
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
