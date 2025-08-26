package android.media.tv.extension.cam;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMmiSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.cam.IMmiSession";

    public static class Default implements IMmiSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.cam.IMmiSession
        public void close() throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.IMmiSession
        public void closeMmi() throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.IMmiSession
        public void setEnquiryAnswer(int i, String str) throws RemoteException {
        }

        @Override // android.media.tv.extension.cam.IMmiSession
        public void setMenuListAnswer(int i) throws RemoteException {
        }
    }

    void close() throws RemoteException;

    void closeMmi() throws RemoteException;

    void setEnquiryAnswer(int i, String str) throws RemoteException;

    void setMenuListAnswer(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMmiSession {
        static final int TRANSACTION_close = 4;
        static final int TRANSACTION_closeMmi = 3;
        static final int TRANSACTION_setEnquiryAnswer = 2;
        static final int TRANSACTION_setMenuListAnswer = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.cam.IMmiSession");
        }

        public static IMmiSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.cam.IMmiSession");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMmiSession)) {
                return (IMmiSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setMenuListAnswer";
            }
            if (i == 2) {
                return "setEnquiryAnswer";
            }
            if (i == 3) {
                return "closeMmi";
            }
            if (i != 4) {
                return null;
            }
            return "close";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.cam.IMmiSession");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.cam.IMmiSession");
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                setMenuListAnswer(i3);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i4 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                setEnquiryAnswer(i4, string);
                parcel2.writeNoException();
            } else if (i == 3) {
                closeMmi();
                parcel2.writeNoException();
            } else if (i == 4) {
                close();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMmiSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.cam.IMmiSession";
            }

            @Override // android.media.tv.extension.cam.IMmiSession
            public void setMenuListAnswer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiSession");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IMmiSession
            public void setEnquiryAnswer(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiSession");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IMmiSession
            public void closeMmi() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiSession");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.extension.cam.IMmiSession
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.cam.IMmiSession");
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
