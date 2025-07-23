package android.media.tv.extension.signal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAudioSignalInfoListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.signal.IAudioSignalInfoListener";

    public static class Default implements IAudioSignalInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.signal.IAudioSignalInfoListener
        public void onAudioSignalInfoChanged(String str, Bundle bundle) throws RemoteException {
        }
    }

    void onAudioSignalInfoChanged(String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioSignalInfoListener {
        static final int TRANSACTION_onAudioSignalInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.signal.IAudioSignalInfoListener");
        }

        public static IAudioSignalInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.signal.IAudioSignalInfoListener");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAudioSignalInfoListener)) {
                return (IAudioSignalInfoListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAudioSignalInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.signal.IAudioSignalInfoListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.signal.IAudioSignalInfoListener");
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onAudioSignalInfoChanged(readString, bundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAudioSignalInfoListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.signal.IAudioSignalInfoListener";
            }

            @Override // android.media.tv.extension.signal.IAudioSignalInfoListener
            public void onAudioSignalInfoChanged(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.signal.IAudioSignalInfoListener");
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
