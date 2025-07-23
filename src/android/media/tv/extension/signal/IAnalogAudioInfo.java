package android.media.tv.extension.signal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAnalogAudioInfo extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.IAnalogAudioInfo";

    public static class Default implements IAnalogAudioInfo {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.IAnalogAudioInfo
        public Bundle getAnalogAudioInfo(String str) throws RemoteException {
            return null;
        }
    }

    Bundle getAnalogAudioInfo(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAnalogAudioInfo {
        static final int TRANSACTION_getAnalogAudioInfo = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.IAnalogAudioInfo");
        }

        public static IAnalogAudioInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IAnalogAudioInfo");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAnalogAudioInfo)) {
                return (IAnalogAudioInfo) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getAnalogAudioInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.IAnalogAudioInfo");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.IAnalogAudioInfo");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                Bundle analogAudioInfo = getAnalogAudioInfo(readString);
                parcel2.writeNoException();
                parcel2.writeTypedObject(analogAudioInfo, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAnalogAudioInfo {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.IAnalogAudioInfo";
            }

            @Override // android.media.tv.extension.signal.IAnalogAudioInfo
            public Bundle getAnalogAudioInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IAnalogAudioInfo");
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
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
