package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IDetectorSessionStorageService extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IDetectorSessionStorageService";

    public static class Default implements IDetectorSessionStorageService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IDetectorSessionStorageService
        public void openFile(String str, AndroidFuture androidFuture) throws RemoteException {
        }
    }

    void openFile(String str, AndroidFuture androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IDetectorSessionStorageService {
        static final int TRANSACTION_openFile = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDetectorSessionStorageService.DESCRIPTOR);
        }

        public static IDetectorSessionStorageService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDetectorSessionStorageService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDetectorSessionStorageService)) {
                return (IDetectorSessionStorageService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "openFile";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDetectorSessionStorageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDetectorSessionStorageService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                openFile(string, androidFuture);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDetectorSessionStorageService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDetectorSessionStorageService.DESCRIPTOR;
            }

            @Override // android.service.voice.IDetectorSessionStorageService
            public void openFile(String str, AndroidFuture androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IDetectorSessionStorageService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
