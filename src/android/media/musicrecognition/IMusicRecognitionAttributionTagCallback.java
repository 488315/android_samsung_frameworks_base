package android.media.musicrecognition;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMusicRecognitionAttributionTagCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionAttributionTagCallback";

    public static class Default implements IMusicRecognitionAttributionTagCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionAttributionTagCallback
        public void onAttributionTag(String str) throws RemoteException {
        }
    }

    void onAttributionTag(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMusicRecognitionAttributionTagCallback {
        static final int TRANSACTION_onAttributionTag = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
        }

        public static IMusicRecognitionAttributionTagCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMusicRecognitionAttributionTagCallback)) {
                return (IMusicRecognitionAttributionTagCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAttributionTag";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onAttributionTag(readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMusicRecognitionAttributionTagCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMusicRecognitionAttributionTagCallback.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionAttributionTagCallback
            public void onAttributionTag(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
