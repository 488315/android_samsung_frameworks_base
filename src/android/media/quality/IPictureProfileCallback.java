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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPictureProfileCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPictureProfileCallback)) {
                return (IPictureProfileCallback) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                PictureProfile pictureProfile = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onPictureProfileAdded(string, pictureProfile);
            } else if (i == 2) {
                String string2 = parcel.readString();
                PictureProfile pictureProfile2 = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onPictureProfileUpdated(string2, pictureProfile2);
            } else if (i == 3) {
                String string3 = parcel.readString();
                PictureProfile pictureProfile3 = (PictureProfile) parcel.readTypedObject(PictureProfile.CREATOR);
                parcel.enforceNoDataAvail();
                onPictureProfileRemoved(string3, pictureProfile3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pictureProfile, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileUpdated(String str, PictureProfile pictureProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pictureProfile, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onPictureProfileRemoved(String str, PictureProfile pictureProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(pictureProfile, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onParameterCapabilitiesChanged(String str, List<ParameterCapability> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.quality.IPictureProfileCallback
            public void onError(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPictureProfileCallback.DESCRIPTOR);
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
