package android.media.tv.extension.tune;

import android.media.tv.extension.tune.IMuxTuneSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMuxTune extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.tune.IMuxTune";

    public static class Default implements IMuxTune {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.tune.IMuxTune
        public IMuxTuneSession createSession(int i, String str) throws RemoteException {
            return null;
        }
    }

    IMuxTuneSession createSession(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMuxTune {
        static final int TRANSACTION_createSession = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.tune.IMuxTune");
        }

        public static IMuxTune asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.tune.IMuxTune");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMuxTune)) {
                return (IMuxTune) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "createSession";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.tune.IMuxTune");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.tune.IMuxTune");
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                IMuxTuneSession iMuxTuneSessionCreateSession = createSession(i3, string);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iMuxTuneSessionCreateSession);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMuxTune {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.tune.IMuxTune";
            }

            @Override // android.media.tv.extension.tune.IMuxTune
            public IMuxTuneSession createSession(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.tune.IMuxTune");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IMuxTuneSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
