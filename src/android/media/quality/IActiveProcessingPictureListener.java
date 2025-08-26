package android.media.quality;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IActiveProcessingPictureListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.quality.IActiveProcessingPictureListener";

    public static class Default implements IActiveProcessingPictureListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.quality.IActiveProcessingPictureListener
        public void onActiveProcessingPicturesChanged(List<ActiveProcessingPicture> list) throws RemoteException {
        }
    }

    void onActiveProcessingPicturesChanged(List<ActiveProcessingPicture> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IActiveProcessingPictureListener {
        static final int TRANSACTION_onActiveProcessingPicturesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IActiveProcessingPictureListener.DESCRIPTOR);
        }

        public static IActiveProcessingPictureListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IActiveProcessingPictureListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IActiveProcessingPictureListener)) {
                return (IActiveProcessingPictureListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IActiveProcessingPictureListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IActiveProcessingPictureListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ActiveProcessingPicture.CREATOR);
                parcel.enforceNoDataAvail();
                onActiveProcessingPicturesChanged(arrayListCreateTypedArrayList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IActiveProcessingPictureListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IActiveProcessingPictureListener.DESCRIPTOR;
            }

            @Override // android.media.quality.IActiveProcessingPictureListener
            public void onActiveProcessingPicturesChanged(List<ActiveProcessingPicture> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IActiveProcessingPictureListener.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
