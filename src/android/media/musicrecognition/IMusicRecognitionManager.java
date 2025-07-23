package android.media.musicrecognition;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMusicRecognitionManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.musicrecognition.IMusicRecognitionManager";

    public static class Default implements IMusicRecognitionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.musicrecognition.IMusicRecognitionManager
        public void beginRecognition(RecognitionRequest recognitionRequest, IBinder iBinder) throws RemoteException {
        }
    }

    void beginRecognition(RecognitionRequest recognitionRequest, IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IMusicRecognitionManager {
        static final int TRANSACTION_beginRecognition = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IMusicRecognitionManager.DESCRIPTOR);
        }

        public static IMusicRecognitionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMusicRecognitionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMusicRecognitionManager)) {
                return (IMusicRecognitionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "beginRecognition";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMusicRecognitionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMusicRecognitionManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RecognitionRequest recognitionRequest = (RecognitionRequest) parcel.readTypedObject(RecognitionRequest.CREATOR);
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                beginRecognition(recognitionRequest, readStrongBinder);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMusicRecognitionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMusicRecognitionManager.DESCRIPTOR;
            }

            @Override // android.media.musicrecognition.IMusicRecognitionManager
            public void beginRecognition(RecognitionRequest recognitionRequest, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMusicRecognitionManager.DESCRIPTOR);
                    obtain.writeTypedObject(recognitionRequest, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
