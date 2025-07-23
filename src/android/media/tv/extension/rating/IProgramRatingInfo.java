package android.media.tv.extension.rating;

import android.media.tv.extension.rating.IProgramRatingInfoListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProgramRatingInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.rating.IProgramRatingInfo";

    public static class Default implements IProgramRatingInfo {
        @Override // android.media.tv.extension.rating.IProgramRatingInfo
        public void addProgramRatingInfoListener(String str, IProgramRatingInfoListener iProgramRatingInfoListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.rating.IProgramRatingInfo
        public Bundle getProgramRatingInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.rating.IProgramRatingInfo
        public void removeProgramRatingInfoListener(IProgramRatingInfoListener iProgramRatingInfoListener) throws RemoteException {
        }
    }

    void addProgramRatingInfoListener(String str, IProgramRatingInfoListener iProgramRatingInfoListener) throws RemoteException;

    Bundle getProgramRatingInfo(String str) throws RemoteException;

    void removeProgramRatingInfoListener(IProgramRatingInfoListener iProgramRatingInfoListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IProgramRatingInfo {
        static final int TRANSACTION_addProgramRatingInfoListener = 1;
        static final int TRANSACTION_getProgramRatingInfo = 3;
        static final int TRANSACTION_removeProgramRatingInfoListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IProgramRatingInfo.DESCRIPTOR);
        }

        public static IProgramRatingInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProgramRatingInfo.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProgramRatingInfo)) {
                return (IProgramRatingInfo) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addProgramRatingInfoListener";
            }
            if (i == 2) {
                return "removeProgramRatingInfoListener";
            }
            if (i != 3) {
                return null;
            }
            return "getProgramRatingInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProgramRatingInfo.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProgramRatingInfo.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                IProgramRatingInfoListener asInterface = IProgramRatingInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addProgramRatingInfoListener(readString, asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IProgramRatingInfoListener asInterface2 = IProgramRatingInfoListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeProgramRatingInfoListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle programRatingInfo = getProgramRatingInfo(readString2);
                parcel2.writeNoException();
                parcel2.writeTypedObject(programRatingInfo, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProgramRatingInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProgramRatingInfo.DESCRIPTOR;
            }

            @Override // android.media.tv.extension.rating.IProgramRatingInfo
            public void addProgramRatingInfoListener(String str, IProgramRatingInfoListener iProgramRatingInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProgramRatingInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iProgramRatingInfoListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IProgramRatingInfo
            public void removeProgramRatingInfoListener(IProgramRatingInfoListener iProgramRatingInfoListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProgramRatingInfo.DESCRIPTOR);
                    obtain.writeStrongInterface(iProgramRatingInfoListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.rating.IProgramRatingInfo
            public Bundle getProgramRatingInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProgramRatingInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
