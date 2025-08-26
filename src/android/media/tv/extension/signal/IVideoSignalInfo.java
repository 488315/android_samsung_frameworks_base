package android.media.tv.extension.signal;

import android.media.tv.extension.signal.IVideoSignalInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IVideoSignalInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.IVideoSignalInfo";

    public static class Default implements IVideoSignalInfo {
        @Override // android.media.tv.extension.signal.IVideoSignalInfo
        public void addVideoSignalInfoListener(String str, IVideoSignalInfoListener iVideoSignalInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.IVideoSignalInfo
        public Bundle getVideoSignalInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.signal.IVideoSignalInfo
        public void removeVideoSignalInfoListener(IVideoSignalInfoListener iVideoSignalInfoListener) throws RemoteException {
        }
    }

    void addVideoSignalInfoListener(String str, IVideoSignalInfoListener iVideoSignalInfoListener) throws RemoteException;

    Bundle getVideoSignalInfo(String str) throws RemoteException;

    void removeVideoSignalInfoListener(IVideoSignalInfoListener iVideoSignalInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoSignalInfo {
        static final int TRANSACTION_addVideoSignalInfoListener = 1;
        static final int TRANSACTION_getVideoSignalInfo = 3;
        static final int TRANSACTION_removeVideoSignalInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.IVideoSignalInfo");
        }

        public static IVideoSignalInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IVideoSignalInfo");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVideoSignalInfo)) {
                return (IVideoSignalInfo) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addVideoSignalInfoListener";
            }
            if (i == 2) {
                return "removeVideoSignalInfoListener";
            }
            if (i != 3) {
                return null;
            }
            return "getVideoSignalInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.IVideoSignalInfo");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.IVideoSignalInfo");
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                IVideoSignalInfoListener iVideoSignalInfoListenerAsInterface = IVideoSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addVideoSignalInfoListener(string, iVideoSignalInfoListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IVideoSignalInfoListener iVideoSignalInfoListenerAsInterface2 = IVideoSignalInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeVideoSignalInfoListener(iVideoSignalInfoListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle videoSignalInfo = getVideoSignalInfo(string2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(videoSignalInfo, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVideoSignalInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.IVideoSignalInfo";
            }

            @Override // android.media.tv.extension.signal.IVideoSignalInfo
            public void addVideoSignalInfoListener(String str, IVideoSignalInfoListener iVideoSignalInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IVideoSignalInfo");
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iVideoSignalInfoListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IVideoSignalInfo
            public void removeVideoSignalInfoListener(IVideoSignalInfoListener iVideoSignalInfoListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IVideoSignalInfo");
                    parcelObtain.writeStrongInterface(iVideoSignalInfoListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.signal.IVideoSignalInfo
            public Bundle getVideoSignalInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.signal.IVideoSignalInfo");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
